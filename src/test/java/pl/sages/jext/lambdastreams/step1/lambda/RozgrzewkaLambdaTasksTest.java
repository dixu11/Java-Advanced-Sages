package pl.sages.jext.lambdastreams.step1.lambda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sages.jext.lambdastreams.ConsoleCapture;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Rozgrzewka, krok 1 (slajdy 85-139): lambdy i strumienie.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.lambdastreams.step1.lambda.LambdaTasks}
 * (src/main/java/pl/sages/jext/lambdastreams/step1/lambda/LambdaTasks.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.lambdastreams.step1.lambda.LambdaTasks} (src/main/java/pl/sages/jext/lambdastreams/step1/lambda/LambdaTasks.java)</li>
 * </ol>
 */
@DisplayName("Lambdy i strumienie, krok 1: Operation i Printer z arkusza")
class RozgrzewkaLambdaTasksTest {

    @Test
    @DisplayName("1a) dodawanie klasą anonimową")
    void addition() {
        LambdaTasks.Operation addition = LambdaTasks.addition();
        assertEquals(30, addition.calculate(10, 20));
        assertTrue(addition.getClass().isAnonymousClass(),
                "dodawanie ma być klasą anonimową: return new Operation() { ... }");
    }

    @Test
    @DisplayName("1b) odejmowanie lambdą")
    void subtraction() {
        LambdaTasks.Operation subtraction = LambdaTasks.subtraction();
        assertEquals(-10, subtraction.calculate(10, 20));
        assertTrue(subtraction.getClass().isHidden(),
                "odejmowanie ma być lambdą: (number1, number2) -> ...");
    }

    @Test
    @DisplayName("2a) funkcja krzycząca: wielkie litery i wykrzyknik")
    void shouter() {
        assertEquals(List.of("HELLO!"), ConsoleCapture.lines(() -> LambdaTasks.shouter().print("Hello")));
    }

    @Test
    @DisplayName("2b) trudne: pierwsze litery słów")
    void firstLetters() {
        assertEquals(List.of("Amk"), ConsoleCapture.lines(() -> LambdaTasks.firstLetters().print("Ala ma kota")));
        assertEquals(List.of("Jik"), ConsoleCapture.lines(() -> LambdaTasks.firstLetters().print("Java  i kawa")),
                "kilka spacji między słowami też ma działać");
    }
}
