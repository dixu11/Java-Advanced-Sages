package pl.sages.jext.ch8.project;

/** Wynik etapu b. Odrzucenie zachowuje zamówienie i czytelny powód. */
public sealed interface OrderResult permits OrderResult.Accepted, OrderResult.Rejected {
    record Accepted(Order order) implements OrderResult {}
    record Rejected(Order order, String reason) implements OrderResult {}
}
