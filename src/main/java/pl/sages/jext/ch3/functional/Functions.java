package pl.sages.jext.ch3.functional;

import pl.sages.jext.data.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Ćwiczenie 5 (slajdy 88-97): łączenie Predicate i Function, funkcje wyższego rzędu, memoizacja.
 * Dane: rekord Order (pakiet pl.sages.jext.data), lista z Orders.all().
 */
public class Functions {

    /** a) Predykat: kwota zamówienia jest WIĘKSZA niż threshold. */
    public static Predicate<Order> moreExpensiveThan(double threshold) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** b) Predykat: zamówienie z podanego miasta. */
    public static Predicate<Order> fromCity(String city) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** c) Predykat: zamówienie anulowane (status CANCELLED). */
    public static Predicate<Order> cancelled() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * d) Połącz predykaty z punktów a-c, BEZ pisania nowej lambdy: droższe niż threshold
     * I z podanego miasta I NIE anulowane. Użyj and(...) oraz Predicate.not(...) (slajd 91).
     */
    public static Predicate<Order> goodOrders(double threshold, String city) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * e) Funkcja wyższego rzędu: odfiltruj zamówienia predykatem i zamień każde funkcją na napis.
     * Kolejność jak na wejściu. Bez strumieni też można, ale ze strumieniem to 1 linijka.
     */
    public static List<String> report(List<Order> orders, Predicate<Order> filter,
                                      Function<Order, String> format) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * f) Złóż dwie funkcje: najpierw klient zamówienia (Order::customer), potem zamiana na WIELKIE litery.
     * Użyj andThen (slajd 92).
     */
    public static Function<Order, String> customerInUpperCase() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * g) Dla chętnych (slajd 97): zwróć funkcję, która zapamiętuje wyniki. Dla argumentu, który już był,
     * NIE wywołuje ponownie funkcji oryginalnej. Podpowiedź: HashMap i computeIfAbsent.
     */
    public static <T, R> Function<T, R> memoize(Function<T, R> function) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
