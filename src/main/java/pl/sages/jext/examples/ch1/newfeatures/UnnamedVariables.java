package pl.sages.jext.examples.ch1.newfeatures;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiFunction;

/**
 * Slajd 18 (oraz slajdy 15, 17, 19): zmienne nienazwane {@code _}.
 * <p>
 * Przykłady 3 i 4 ze slajdu 18 są w wersji poprawionej: zasób tworzony
 * w nawiasie try (slajd 19) i pętla z JEP 456 zamiast pętli nieskończonej.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch1.newfeatures.UnnamedVariables}
 */
public class UnnamedVariables {

    record Point(int x, int y) {}

    sealed interface Shape permits Circle, Square, Triangle {}
    record Circle(double r) implements Shape {}
    record Square(double a) implements Shape {}
    record Triangle(double side) implements Shape {}

    /** Slajd 19: zasób, który liczy się tylko przez otwarcie i zamknięcie. */
    static class Timer implements AutoCloseable {
        private final String name;
        private final long start = System.nanoTime();

        Timer(String name) { this.name = name; }

        @Override
        public void close() {
            System.out.println(name + ": " + (System.nanoTime() - start) / 1_000_000 + " ms");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String s = "Java";

        // 1. Zmienne w definicji pętli
        int len = 0;
        for (char _ : s.toCharArray()) { len++; } // pominięcie nazwy w pętlach for oraz for-each
        System.out.println("Length of " + s + ": " + len);

        // 2. Definicja wyjątków w ramach try-catch
        try {
            int i = Integer.parseInt("abc");
            System.out.println(i);
        } catch (NumberFormatException _) { // pominięcie nazwy dla łapanego wyjątku
            System.out.println("\"abc\" to nie liczba");
        }

        // 3. try-with-resources: zasób tworzony w nawiasie (poprawka, slajd 19)
        try (var _ = new Timer("import")) {
            Thread.sleep(50); // Timera nie dotykasz, liczy się tylko close() na końcu
        }

        // 4. Zmienne lokalne: przykład z JEP 456 (poprawka; na slajdzie pętla nieskończona)
        Queue<Integer> q = new ArrayDeque<>(List.of(1, 2, 3, 4, 5, 6, 7));
        while (q.size() >= 3) {
            var x = q.remove();
            var y = q.remove();
            var _ = q.remove(); // zmienna nienazwana, ale prawa strona się wykona
            System.out.println("para (" + x + ", " + y + "), trzeci element pominięty");
        }
        System.out.println("zostało w kolejce: " + q);

        // 5. Parametry lambd
        List<String> list = List.of("a", "b");
        list.forEach(_ -> System.out.println("stała akcja")); // nieużywane parametry lambd
        Map.of("klucz", "wartość").forEach((_, value) -> System.out.println(value)); // slajd 15

        // 6. Pattern matching w ramach switch oraz instanceof
        for (Object obj : List.<Object>of(new Point(1, 2), "tekst", 42)) {
            switch (obj) {
                case Point(_, int y) -> System.out.println("y=" + y); // nieużywane elementy wzorca
                case String _        -> System.out.println("to string"); // całkowicie nieużywany obiekt
                default              -> {}
            }
        }

        // slajd 17: _ wiele razy w jednym zakresie i kilka wzorców w jednym case
        BiFunction<String, Integer, String> f = (_, _) -> "stała"; // OK
        // BiFunction<String, Integer, String> g = (ignored, ignored) -> "x"; // błąd: dwa razy ignored
        System.out.println(f.apply("x", 1));
        for (Shape shape : List.of(new Circle(1), new Square(2), new Triangle(3))) {
            String description = switch (shape) {
                case Circle _, Square _ -> "okrągłe albo kwadratowe"; // case Circle c, Square s: błąd
                case Triangle t         -> "trójkąt o boku " + t.side();
            };
            System.out.println(description);
        }
    }
}
