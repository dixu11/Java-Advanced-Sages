package pl.sages.jext.lambdastreams.step2.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sages.jext.lambdastreams.ConsoleCapture;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Rozgrzewka, krok 2 (slajdy 85-139): lambdy i strumienie.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.lambdastreams.step2.functional.FiveFunctions}
 * (src/main/java/pl/sages/jext/lambdastreams/step2/functional/FiveFunctions.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.lambdastreams.step2.functional.FiveFunctions} (src/main/java/pl/sages/jext/lambdastreams/step2/functional/FiveFunctions.java)</li>
 * </ol>
 */
@DisplayName("Lambdy i strumienie, krok 2: 5 funkcji gotowymi interfejsami")
class RozgrzewkaFiveFunctionsTest {

    @Test
    @DisplayName("1) Function: długość tekstu")
    void textLength() {
        assertNotNull(FiveFunctions.TEXT_LENGTH, "wpisz lambdę w miejsce null");
        assertEquals(4, FiveFunctions.TEXT_LENGTH.apply("abcd"));
        assertEquals(0, FiveFunctions.TEXT_LENGTH.apply(""));
    }

    @Test
    @DisplayName("2) Predicate: minimum 5 znaków")
    void atLeastFive() {
        assertNotNull(FiveFunctions.HAS_AT_LEAST_FIVE_CHARS, "wpisz lambdę w miejsce null");
        assertFalse(FiveFunctions.HAS_AT_LEAST_FIVE_CHARS.test("abcd"));
        assertTrue(FiveFunctions.HAS_AT_LEAST_FIVE_CHARS.test("abcde"), "5 znaków to już minimum");
    }

    @Test
    @DisplayName("3) Supplier: losowa liczba 1-10")
    void randomOneToTen() {
        assertNotNull(FiveFunctions.RANDOM_ONE_TO_TEN, "wpisz lambdę w miejsce null");
        List<Integer> drawn = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            drawn.add(FiveFunctions.RANDOM_ONE_TO_TEN.get());
        }
        assertTrue(drawn.stream().allMatch(n -> n >= 1 && n <= 10), "liczby mają być z przedziału 1-10");
        assertTrue(drawn.contains(1) && drawn.contains(10), "w 1000 losowaniach powinny wypaść i 1, i 10");
    }

    @Test
    @DisplayName("4) Consumer: drukuje wielkimi literami")
    void printUpperCase() {
        assertNotNull(FiveFunctions.PRINT_UPPER_CASE, "wpisz lambdę w miejsce null");
        assertEquals(List.of("HELLO"), ConsoleCapture.lines(() -> FiveFunctions.PRINT_UPPER_CASE.accept("hello")));
    }

    @Test
    @DisplayName("5) Comparator: po ilości liter")
    void byLength() {
        assertNotNull(FiveFunctions.BY_LENGTH, "wpisz lambdę w miejsce null");
        assertTrue(FiveFunctions.BY_LENGTH.compare("b", "aaaa") < 0, "krótszy tekst ma być pierwszy");
        assertEquals(0, FiveFunctions.BY_LENGTH.compare("ab", "cd"));
        List<String> words = new ArrayList<>(List.of("Zbyszek", "Iza", "Kasia"));
        words.sort(FiveFunctions.BY_LENGTH);
        assertEquals(List.of("Iza", "Kasia", "Zbyszek"), words);
    }
}
