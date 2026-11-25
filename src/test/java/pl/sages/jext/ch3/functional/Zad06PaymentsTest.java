package pl.sages.jext.ch3.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sages.jext.ch3.functional.Payments.Action;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ćwiczenie 6 (slajdy 100-117): sealed, rekordy i switch z wzorcami.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.ch3.functional.PaymentResult}
 * (src/main/java/pl/sages/jext/ch3/functional/PaymentResult.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.ch3.functional.PaymentResult} (src/main/java/pl/sages/jext/ch3/functional/PaymentResult.java)</li>
 * <li>{@code pl.sages.jext.ch3.functional.Payments} (src/main/java/pl/sages/jext/ch3/functional/Payments.java)</li>
 * </ol>
 */
@DisplayName("Ćw. 6: wynik płatności (sealed, rekordy, switch z wzorcami)")
class Zad06PaymentsTest {

    private static Money pln(String amount) {
        return new Money(new BigDecimal(amount), "PLN");
    }

    private static Success paid(String amount) {
        return new Success("TX-1", pln(amount));
    }

    private static Declined declined(DeclineReason reason) {
        return new Declined(reason);
    }

    private static Set<String> permitted(Class<?> type) {
        return Arrays.stream(type.getPermittedSubclasses())
                .map(Class::getSimpleName)
                .collect(Collectors.toSet());
    }

    @Test
    @DisplayName("a) PaymentResult i DeclineReason są sealed")
    void sealed() {
        assertTrue(PaymentResult.class.isSealed(),
                "dodaj sealed ... permits Success, Declined, Failure do interfejsu PaymentResult");
        assertTrue(permitted(PaymentResult.class).containsAll(Set.of("Success", "Declined", "Failure")),
                "PaymentResult ma pozwalać na Success, Declined i Failure, a pozwala na: " + permitted(PaymentResult.class));
        assertTrue(DeclineReason.class.isSealed(),
                "dodaj sealed ... permits InsufficientFunds, CardExpired, FraudSuspected do interfejsu DeclineReason");
        assertEquals(Set.of("InsufficientFunds", "CardExpired", "FraudSuspected"), permitted(DeclineReason.class),
                "DeclineReason ma pozwalać dokładnie na trzy powody odmowy");
    }

    @Test
    @DisplayName("b) komunikat przy udanej płatności, z guardem dla kwoty powyżej 10000")
    void customerMessageSuccess() {
        assertEquals("Zapłacono 120.00 PLN", Payments.customerMessage(paid("120.00")),
                "zwykła płatność: kwota przez toPlainString() i waluta");
        assertEquals("Zapłacono 10000 PLN", Payments.customerMessage(paid("10000")),
                "dokładnie 10000 to jeszcze zwykła płatność (warunek: większa niż 10000)");
        assertEquals("Zapłacono 10000.00 EUR", Payments.customerMessage(new Success("TX-2", new Money(new BigDecimal("10000.00"), "EUR"))),
                "10000.00 to ta sama kwota co 10000, więc też zwykła płatność (BigDecimal porównuj przez compareTo)");
        assertEquals("Płatność przyjęta, czeka na potwierdzenie: 10000.01 PLN", Payments.customerMessage(paid("10000.01")),
                "kwota większa niż 10000: guard when w case Success, ustawiony przed zwykłym Success");
        assertEquals("Płatność przyjęta, czeka na potwierdzenie: 25000.00 PLN", Payments.customerMessage(paid("25000.00")),
                "duża płatność czeka na potwierdzenie");
    }

    @Test
    @DisplayName("b) komunikat przy odmowie: wzorce zagnieżdżone w Declined")
    void customerMessageDeclined() {
        assertEquals("Brakuje 35.50 PLN na koncie", Payments.customerMessage(declined(new InsufficientFunds(pln("35.50")))),
                "brak środków: rozbierz Declined(InsufficientFunds(Money(var amount, var currency)))");
        assertEquals("Brakuje 0.99 EUR na koncie",
                Payments.customerMessage(declined(new InsufficientFunds(new Money(new BigDecimal("0.99"), "EUR")))),
                "waluta ma pochodzić z brakującej kwoty, nie może być wpisana na sztywno");
        assertEquals("Karta wygasła 2026-10", Payments.customerMessage(declined(new CardExpired(YearMonth.of(2026, 10)))),
                "karta wygasła: data jako YearMonth sklejona z tekstem");
        assertEquals("Karta wygasła 2025-01", Payments.customerMessage(declined(new CardExpired(YearMonth.of(2025, 1)))),
                "karta wygasła: miesiąc z zerem z przodu, tak jak wypisuje YearMonth");
        assertEquals("Płatność odrzucona, skontaktuj się z bankiem", Payments.customerMessage(declined(new FraudSuspected(95))),
                "podejrzenie oszustwa: klient nie widzi oceny ryzyka");
        assertEquals("Płatność odrzucona, skontaktuj się z bankiem", Payments.customerMessage(declined(new FraudSuspected(10))),
                "podejrzenie oszustwa: ten sam komunikat przy każdej ocenie ryzyka");
    }

    @Test
    @DisplayName("b) komunikat przy błędzie technicznym")
    void customerMessageFailure() {
        assertEquals("Błąd techniczny, spróbuj ponownie później", Payments.customerMessage(new Failure(new TimeoutException("bramka"))),
                "błąd techniczny: komunikat nie zależy od wyjątku");
        assertEquals("Błąd techniczny, spróbuj ponownie później", Payments.customerMessage(new Failure(new IOException("sieć"))),
                "błąd techniczny: komunikat nie zależy od wyjątku");
    }

    @Test
    @DisplayName("c) decyzja przy udanej płatności: wysyłka albo ręczne sprawdzenie")
    void nextActionSuccess() {
        assertEquals(Action.SHIP, Payments.nextAction(paid("120.00")), "zwykła płatność: wysyłamy");
        assertEquals(Action.SHIP, Payments.nextAction(paid("10000")), "dokładnie 10000 to jeszcze zwykła płatność");
        assertEquals(Action.MANUAL_REVIEW, Payments.nextAction(paid("10000.01")),
                "powyżej 10000: ręczne sprawdzenie (guard przed zwykłym Success)");
        assertEquals(Action.MANUAL_REVIEW, Payments.nextAction(paid("50000")), "powyżej 10000: ręczne sprawdzenie");
    }

    @Test
    @DisplayName("c) decyzja przy odmowie: inna karta albo ręczne sprawdzenie (próg ryzyka 80)")
    void nextActionDeclined() {
        assertEquals(Action.ASK_FOR_ANOTHER_CARD, Payments.nextAction(declined(new InsufficientFunds(pln("35.50")))),
                "brak środków: prosimy o inną kartę");
        assertEquals(Action.ASK_FOR_ANOTHER_CARD, Payments.nextAction(declined(new CardExpired(YearMonth.of(2026, 10)))),
                "karta wygasła: prosimy o inną kartę");
        assertEquals(Action.ASK_FOR_ANOTHER_CARD, Payments.nextAction(declined(new FraudSuspected(79))),
                "ryzyko 79 (poniżej 80): prosimy o inną kartę");
        assertEquals(Action.MANUAL_REVIEW, Payments.nextAction(declined(new FraudSuspected(80))),
                "ryzyko 80: już ręczne sprawdzenie (warunek: score >= 80)");
        assertEquals(Action.MANUAL_REVIEW, Payments.nextAction(declined(new FraudSuspected(100))),
                "ryzyko 100: ręczne sprawdzenie");
        assertEquals(Action.ASK_FOR_ANOTHER_CARD, Payments.nextAction(declined(new FraudSuspected(0))),
                "ryzyko 0: prosimy o inną kartę");
    }

    @Test
    @DisplayName("c) decyzja przy błędzie: timeout ponawiamy, resztę sprawdza człowiek")
    void nextActionFailure() {
        assertEquals(Action.RETRY_LATER, Payments.nextAction(new Failure(new TimeoutException("bramka nie odpowiada"))),
                "TimeoutException: ponawiamy później (case Failure(TimeoutException _) przed Failure _)");
        assertEquals(Action.MANUAL_REVIEW, Payments.nextAction(new Failure(new IOException("zerwane połączenie"))),
                "inny wyjątek niż TimeoutException: ręczne sprawdzenie");
        assertEquals(Action.MANUAL_REVIEW, Payments.nextAction(new Failure(new IllegalStateException("zły stan"))),
                "inny wyjątek niż TimeoutException: ręczne sprawdzenie");
    }

    @Test
    @DisplayName("e) dla chętnych: raport odmów według powodu")
    void declineReport() {
        List<PaymentResult> results = List.of(
                paid("120.00"),
                declined(new InsufficientFunds(pln("35.50"))),
                declined(new CardExpired(YearMonth.of(2026, 10))),
                new Failure(new TimeoutException("bramka")),
                declined(new InsufficientFunds(pln("12.00"))),
                declined(new FraudSuspected(90)),
                paid("25000.00"),
                declined(new InsufficientFunds(pln("1.00"))),
                declined(new CardExpired(YearMonth.of(2025, 1))));
        assertEquals(Map.of("brak środków", 3L, "karta wygasła", 2L, "podejrzenie oszustwa", 1L), Payments.declineReport(results),
                "liczymy tylko Declined, kluczem jest opis powodu; Success i Failure pomijamy");
    }

    @Test
    @DisplayName("e) dla chętnych: raport bez odmów jest pusty, brakujących powodów nie ma w mapie")
    void declineReportEdgeCases() {
        assertEquals(Map.of(), Payments.declineReport(List.of(paid("10.00"), new Failure(new IOException("sieć")))),
                "bez odmów raport jest pusty");
        assertEquals(Map.of("podejrzenie oszustwa", 2L),
                Payments.declineReport(List.of(declined(new FraudSuspected(10)), declined(new FraudSuspected(99)))),
                "w mapie są tylko powody, które wystąpiły");
    }
}
