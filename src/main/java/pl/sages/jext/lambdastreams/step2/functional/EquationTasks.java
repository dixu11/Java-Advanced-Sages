package pl.sages.jext.lambdastreams.step2.functional;

/**
 * Arkusz „Zadania zaawansowane”, sekcja „Lambda, optional”, zad. 1 (rozwiązanie jak w SDA-zaawansowane EquationDemo).
 * Test: {@code EquationTasksTest}.
 * <p>
 * Stwórz interfejs Equation z metodą execute przyjmującą dwie wartości double i zwracającą double.
 * Za pomocą lambdy zdefiniuj dwie funkcje implementujące tę metodę. Jedna ma wykonywać mnożenie a druga
 * dzielenie. Funkcja dzieląca ma rzucać wyjątek w przypadku dzielenia przez 0.
 * <p>
 * nowe: wyjątek to {@link IllegalArgumentException} (test go sprawdza). To wstęp do „wyjątki w lambdach”
 * z rozdziału 4 (slajdy 128-131).
 */
public class EquationTasks {

    @FunctionalInterface
    public interface Equation {
        double execute(double number1, double number2);
    }

    /** mnożenie, lambdą */
    public static Equation multiplication() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** dzielenie, lambdą; dla dzielnika 0 rzuca IllegalArgumentException */
    public static Equation division() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public static void main(String[] args) {
        System.out.println(multiplication().execute(2, 3));
        try {
            System.out.println(division().execute(5, 0));
        } catch (IllegalArgumentException e) {
            System.out.println("Obsługa dzielenia przez 0 ");
        }
    }
}
