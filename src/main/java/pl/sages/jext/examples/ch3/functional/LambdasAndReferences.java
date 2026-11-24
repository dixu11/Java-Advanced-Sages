package pl.sages.jext.examples.ch3.functional;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Slajdy 87-90: czysta funkcja, lambdy i interfejsy funkcyjne, kontekst lambdy (efektywnie finalne zmienne),
 * referencje do metod.
 * <p>
 * Slajd 90: port 0 zamiast 8080 (system wybiera wolny port, przykład nie padnie na zajętym porcie).
 * {@code ServerSocket::getLocalPort} to referencja do metody instancji przez nazwę klasy (obiekt przychodzi
 * jako argument), a nie metoda statyczna; slajd podpisuje ją „statyczna referencja”.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch3.functional.LambdasAndReferences}
 */
public class LambdasAndReferences {

    // --- slajd 87 ---
    record Item(String name, int price) {}

    static int total(List<Item> items) {
        return items.stream().mapToInt(Item::price).sum();
    }

    // --- slajd 88 ---
    @FunctionalInterface
    public interface Rangeable {
        int rangeAttack(int speed);

        // Metoda zgodna z Object nie jest wliczana.
        String toString();
    }

    static int counter = 0; // slajd 89: pole statyczne, lambda może je zmieniać

    public static void main(String[] args) throws IOException {
        var items = List.of(new Item("Book", 40), new Item("Pen", 5));
        System.out.println("52: total = " + total(items) + " (wejście bez zmian: " + items + ")");

        Rangeable attack = speed -> speed * 2;
        Supplier<Boolean> alwaysTrue = () -> true;
        Predicate<String> p1 = a -> a.startsWith("java");
        Predicate<String> p2 = (String a) -> a.startsWith("java");
        BiPredicate<String, String> p3 = (a, b) -> a.startsWith("java")
                && b.startsWith("java");
        BiPredicate<String, String> bothStartsWithJava =
                (String a, String b) -> {
                    var firstMatches = a.startsWith("java");
                    var secondMatches = b.startsWith("java");
                    return firstMatches && secondMatches;
                };
        System.out.println("53: attack(5) = " + attack.rangeAttack(5) + ", " + alwaysTrue.get() + ", " + p1.test("javac")
                + ", " + p2.test("kotlin") + ", " + p3.test("java", "javadoc") + ", " + bothStartsWithJava.test("java", "c"));

        // --- slajd 89 ---
        int local = 10;            // efektywnie finalna: nikt jej nie zmienia
        Function<Integer, Integer> addLocal = x -> {
            counter++;             // pole statyczne: wolno
            return x + local;      // zmienna lokalna: tylko odczyt
        };
        // lokalna++;              // błąd kompilacji: lokalna przestałaby być efektywnie finalna
        System.out.println("54: addLocal(5) = " + addLocal.apply(5) + ", pole po wywołaniu = " + counter);

        // --- slajd 90 ---
        try (ServerSocket server = new ServerSocket(0)) {
            // referencja do metody instancji przez nazwę klasy: obiekt przychodzi jako argument
            Function<ServerSocket, Integer> getLocalPortStatic = ServerSocket::getLocalPort;
            // referencja związana z obiektem server
            Runnable getLocalPortNonStatic = server::getLocalPort; // kompiluje się, ale wynik przepada
            IntSupplier better = server::getLocalPort;              // typ, który oddaje wynik
            getLocalPortNonStatic.run();
            System.out.println("55: port przez Function = " + getLocalPortStatic.apply(server)
                    + ", przez IntSupplier = " + better.getAsInt());
        }
    }
}
