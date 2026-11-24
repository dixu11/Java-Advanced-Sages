package pl.sages.jext.ch3.functional;

import pl.sages.jext.data.Order;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Ćwiczenie 5 (slajdy 86-95): łączenie Predicate i Function, funkcje wyższego rzędu, memoizacja.
 * Dane: rekord Order (pakiet pl.sages.jext.data), lista z Orders.all().
 */
public class Functions {

    /** a) Predykat: kwota zamówienia jest WIĘKSZA niż threshold. */
    public static Predicate<Order> moreExpensiveThan(double threshold) {
        // TODO

        List.of("abc").stream()
                .forEach(System.out::println);
             //   .forEach(text -> System.out.println(text));



        throw new UnsupportedOperationException("TODO");
    }

    /** b) Predykat: zamówienie z podanego miasta. */
    public static Predicate<Order> fromCity(String city) {

        try(Scanner sc = new Scanner(new File("file.txt"))){
            sc.nextLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


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
     * I z podanego miasta I NIE anulowane. Użyj and(...) oraz Predicate.not(...) (slajd 89).
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
     * Użyj andThen (slajd 90).
     */
    public static Function<Order, String> customerInUpperCase() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * g) Dla chętnych (slajd 95): zwróć funkcję, która zapamiętuje wyniki. Dla argumentu, który już był,
     * NIE wywołuje ponownie funkcji oryginalnej. Podpowiedź: HashMap i computeIfAbsent.
     */
    public static <T, R> Function<T, R> memoize(Function<T, R> function) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
