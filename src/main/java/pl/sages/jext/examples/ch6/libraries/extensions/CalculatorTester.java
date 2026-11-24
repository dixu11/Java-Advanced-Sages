package pl.sages.jext.examples.ch6.libraries.extensions;

import lombok.experimental.ExtensionMethod;

/**
 * Slajd 161: rozszerzenie korzysta z obiektu sprzed kropki.
 * Lombok przepisuje wywołanie przy kompilacji, nie tworzy dekoratora.
 * W zespołach zwykle wygrywa jawne wywołanie statyczne:
 * {@code CalculatorExtensions.twice(plus, 5, 3)}.
 * <p>
 * Adnotacja pozostaje w pakiecie {@code lombok.experimental}.
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch6.libraries.extensions.CalculatorTester}
 */
@ExtensionMethod(CalculatorExtensions.class)
public class CalculatorTester {
    public static void main(String[] args) {
        Calculator plus = (a, b) -> a + b;
        Calculator times = (a, b) -> a * b;
        System.out.println(plus.twice(5, 3));  // 11 = (5 + 3) + 3
        System.out.println(times.twice(5, 3)); // 45 = (5 * 3) * 3
        // Lombok przepisuje obiekt.metoda(x, y) na Klasa.metoda(obiekt, x, y)
    }
}
