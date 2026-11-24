package pl.sages.jext.ch3.functional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Ćwiczenie 6b-6e (slajdy 105-117): switch z wzorcami rekordów, zagnieżdżenia, guardy (when) i zmienne nienazwane (_).
 * <p>
 * Jeden wynik płatności, a każdy odbiorca robi z nim co innego: klient dostaje komunikat (6b),
 * magazyn i dział płatności decyzję, co dalej (6c), a analityk raport odmów (6e).
 * Każda z tych operacji to osobny switch, a rekordy z PaymentResult.java zostają nietknięte.
 * Na tym polega problem ekspresji ze slajdu 109: przy stałych przypadkach i przybywających operacjach
 * sealed + switch rozszerza się łatwiej niż metoda w każdej klasie.
 * <p>
 * Uwaga: dopóki interfejsy w PaymentResult.java nie są sealed (6a), switch bez default się nie skompiluje
 * (kompilator nie wie, że innych wyników nie ma). Zacznij od 6a.
 */
public class Payments {

    /** Powyżej tej kwoty (bez względu na walutę) płatność idzie do ręcznego sprawdzenia. Dokładnie 10000 to jeszcze zwykła płatność. */
    static final BigDecimal MANUAL_REVIEW_LIMIT = new BigDecimal("10000");

    /** Co sklep robi dalej z zamówieniem (6c). */
    public enum Action { SHIP, ASK_FOR_ANOTHER_CARD, RETRY_LATER, MANUAL_REVIEW }

    /**
     * 6b) Komunikat dla klienta. Jedno wyrażenie switch, BEZ default, z wzorcami rekordów (slajdy 114-115)
     * i guardem when (slajd 110). Kwoty wypisuj przez {@code amount.toPlainString()} (np. 120.00),
     * datę przez zwykłe sklejenie z tekstem (YearMonth wypisuje się jako 2026-10).
     * <ul>
     * <li>Success z kwotą większą niż 10000 → "Płatność przyjęta, czeka na potwierdzenie: 12000.00 PLN"
     *     (guard: {@code case Success(_, Money(var amount, var currency)) when amount.compareTo(MANUAL_REVIEW_LIMIT) > 0};
     *     BigDecimal porównuj przez compareTo, nie przez &gt; ani equals),</li>
     * <li>pozostałe Success → "Zapłacono 120.00 PLN",</li>
     * <li>Declined z InsufficientFunds → "Brakuje 35.50 PLN na koncie"
     *     (trzy poziomy naraz: {@code case Declined(InsufficientFunds(Money(var amount, var currency)))}),</li>
     * <li>Declined z CardExpired → "Karta wygasła 2026-10",</li>
     * <li>Declined z FraudSuspected → "Płatność odrzucona, skontaktuj się z bankiem"
     *     (ocena ryzyka niepotrzebna: {@code FraudSuspected _}),</li>
     * <li>Failure → "Błąd techniczny, spróbuj ponownie później" (wyjątek niepotrzebny: {@code Failure _}).</li>
     * </ul>
     * Kolejność ma znaczenie: Success z guardem musi stać przed zwykłym Success.
     */
    public static String customerMessage(PaymentResult r) {
        // TODO
        return switch (r) {
            case Success(_, Money(var amount, var currency)) when amount.compareTo(MANUAL_REVIEW_LIMIT) > 0 ->
                    "Płatność przyjęta, czeka na potwierdzenie: " + amount.toPlainString() + " " + currency;
            case Success(_, Money(var amount, var currency)) -> "Zapłacono " + amount.toPlainString() + " " + currency;
            case Declined(InsufficientFunds(Money(var amount, var currency))) ->
                    "Brakuje " + amount.toPlainString() + " " + currency + " na koncie";
            case Declined(CardExpired(var date)) -> "Karta wygasła " + date;
            case Declined(FraudSuspected _) -> "Płatność odrzucona, skontaktuj się z bankiem";
            case Failure _ -> "Błąd techniczny, spróbuj ponownie później";
        };
    }

    /**
     * 6c) Decyzja sklepu: co dalej z zamówieniem. Znowu jeden switch bez default.
     * <ul>
     * <li>Success → SHIP, ale Success z kwotą większą niż 10000 → MANUAL_REVIEW,</li>
     * <li>Declined z InsufficientFunds albo CardExpired → ASK_FOR_ANOTHER_CARD; oba przypadki zmieszczą się
     *     w jednym case: {@code case Declined(InsufficientFunds _), Declined(CardExpired _) -> ...}
     *     (kilka wzorców po przecinku działa, gdy żaden nie nazywa zmiennej),</li>
     * <li>Declined z FraudSuspected i oceną ryzyka co najmniej 80 → MANUAL_REVIEW,
     *     pozostałe FraudSuspected → ASK_FOR_ANOTHER_CARD,</li>
     * <li>Failure, którego przyczyną jest {@link java.util.concurrent.TimeoutException} → RETRY_LATER
     *     (we wzorcu rekordu można podać podtyp składowej: {@code case Failure(TimeoutException _)}),
     *     każde inne Failure → MANUAL_REVIEW.</li>
     * </ul>
     * Kolejność ma znaczenie (slajd 110): szczególny przypadek przed ogólnym. Gdy {@code case Failure _}
     * stanie przed {@code case Failure(TimeoutException _)}, kompilator zgłosi, że drugi case jest zdominowany
     * (jak String po CharSequence: ogólniejszy wzorzec połyka wszystko, co przyszłoby po nim).
     */
    public static Action nextAction(PaymentResult r) {
        // TODO
        return switch (r) {
            case Success(_, Money(var amount, _)) when amount.compareTo(MANUAL_REVIEW_LIMIT) > 0 -> Action.MANUAL_REVIEW;
            case Success _ -> Action.SHIP;
            case Declined(InsufficientFunds _), Declined(CardExpired _) -> Action.ASK_FOR_ANOTHER_CARD;
            case Declined(FraudSuspected(var score)) when score >= 80 -> Action.MANUAL_REVIEW;
            case Declined(FraudSuspected _) -> Action.ASK_FOR_ANOTHER_CARD;
            case Failure(TimeoutException _) -> Action.RETRY_LATER;
            case Failure _ -> Action.MANUAL_REVIEW;
        };
    }

    // 6d) Na koniec, bez testu: nowy wariant wyniku.
    // Bank czasem nie odpowiada od razu, tylko daje adres, pod którym sprawdzisz status później.
    // 1. W PaymentResult.java dopisz  record Pending(String checkUrl) implements PaymentResult {}
    //    i dodaj Pending do permits.
    // 2. Skompiluj (Build albo mvn test-compile). Kompilator wskaże oba switche powyżej:
    //    "the switch expression does not cover all possible input values".
    //    To jest siła sealed: każde miejsce do poprawienia widzisz przy kompilacji, a nie na produkcji w gałęzi default.
    // 3. Obsłuż Pending w obu switchach: klient dostaje "Czekamy na potwierdzenie banku", a akcja to RETRY_LATER.
    // Rozwiązanie 6d (w tym projekcie Pending nie jest dodany, żeby testy pasowały do szkieletu):
    //     case Pending _ -> "Czekamy na potwierdzenie banku";   // w customerMessage
    //     case Pending _ -> Action.RETRY_LATER;                 // w nextAction
    // Przy declineReport nic nie trzeba zmieniać: tam switch idzie po DeclineReason, a nie po PaymentResult.

    /**
     * 6e) Dla chętnych (slajdy 132-136): raport odmów. Dla wyników typu Declined policz, ile było odmów
     * z jakiego powodu. Klucz mapy daje switch po DeclineReason:
     * "brak środków" (InsufficientFunds), "karta wygasła" (CardExpired), "podejrzenie oszustwa" (FraudSuspected).
     * Powody, których nie było, nie pojawiają się w mapie; Success i Failure są pomijane.
     * <p>
     * Podpowiedź: wzorzec rekordu działa też w instanceof, np. {@code r instanceof Declined(var reason)},
     * więc odmowy wyłuskasz w mapMulti (albo filter + map), a policzysz przez
     * {@code Collectors.groupingBy(reason -> switch (reason) { ... }, Collectors.counting())}.
     */
    public static Map<String, Long> declineReport(List<PaymentResult> results) {
        // TODO
        return results.stream()
                .<DeclineReason>mapMulti((r, out) -> {
                    if (r instanceof Declined(var reason)) {
                        out.accept(reason);
                    }
                })
                .collect(Collectors.groupingBy(reason -> switch (reason) {
                    case InsufficientFunds _ -> "brak środków";
                    case CardExpired _ -> "karta wygasła";
                    case FraudSuspected _ -> "podejrzenie oszustwa";
                }, Collectors.counting()));
        // To samo przez flatMap:
        //     .flatMap(r -> r instanceof Declined(var reason) ? Stream.of(reason) : Stream.empty())
    }
}
