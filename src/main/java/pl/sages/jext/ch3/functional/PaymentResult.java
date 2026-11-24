package pl.sages.jext.ch3.functional;

import java.math.BigDecimal;
import java.time.YearMonth;

/**
 * Ćwiczenie 6a (slajdy 98-107): wynik płatności w sklepie jako typ zapieczętowany z rekordami.
 * <p>
 * Płatność kończy się jednym z trzech wyników, a każdy niesie inne dane: sukces ma numer transakcji
 * i kwotę, odmowa ma powód, błąd techniczny ma wyjątek. Nie ma tu pól, które przy połowie wyników są null.
 * <p>
 * TODO: zapieczętuj oba interfejsy w tym pliku (slajdy 103-105):
 * - PaymentResult ma pozwalać tylko na Success, Declined i Failure,
 * - DeclineReason tylko na InsufficientFunds, CardExpired i FraudSuspected.
 * Rekordy są niejawnie final, więc w nich nic nie trzeba zmieniać.
 * Dopiero po tym kroku switch bez default w klasie Payments się skompiluje.
 */
public interface PaymentResult {
}

/** Kwota z walutą, np. 120.00 PLN. Rekord (slajd 98): pola final, equals, hashCode i toString za darmo. */
record Money(BigDecimal amount, String currency) {
}

/** Płatność przeszła: numer transakcji i zapłacona kwota. */
record Success(String transactionId, Money paid) implements PaymentResult {
}

/** Bank odmówił: powód odmowy to osobny typ zapieczętowany (rekord w rekordzie, slajd 113). */
record Declined(DeclineReason reason) implements PaymentResult {
}

/** Błąd techniczny po drodze (brak odpowiedzi bramki, zerwane połączenie): przyczyną jest wyjątek. */
record Failure(Exception cause) implements PaymentResult {
}

/** Powód odmowy płatności. TODO (6a): zapieczętuj (sealed ... permits ...). */
interface DeclineReason {
}

/** Za mało pieniędzy na koncie: ile brakuje. */
record InsufficientFunds(Money missing) implements DeclineReason {
}

/** Karta straciła ważność: miesiąc, w którym wygasła. */
record CardExpired(YearMonth expiredOn) implements DeclineReason {
}

/** System antyfraudowy wstrzymał płatność: ocena ryzyka od 0 do 100. */
record FraudSuspected(int riskScore) implements DeclineReason {
}
