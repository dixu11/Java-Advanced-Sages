package pl.sages.jext.lambdastreams.step2.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Rozgrzewka, krok 2 (slajdy 85-139): lambdy i strumienie.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.lambdastreams.step2.functional.EquationTasks}
 * (src/main/java/pl/sages/jext/lambdastreams/step2/functional/EquationTasks.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.lambdastreams.step2.functional.EquationTasks} (src/main/java/pl/sages/jext/lambdastreams/step2/functional/EquationTasks.java)</li>
 * </ol>
 */
@DisplayName("Lambdy i strumienie, krok 2: Equation z mnożeniem i dzieleniem")
class RozgrzewkaEquationTasksTest {

    @Test
    @DisplayName("mnożenie")
    void multiplication() {
        assertEquals(6.0, EquationTasks.multiplication().execute(2, 3), 1e-9);
    }

    @Test
    @DisplayName("dzielenie i wyjątek przy dzieleniu przez 0")
    void division() {
        assertEquals(2.5, EquationTasks.division().execute(5, 2), 1e-9);
        assertThrows(IllegalArgumentException.class, () -> EquationTasks.division().execute(5, 0),
                "przy dzielniku 0 rzuć IllegalArgumentException (double samo z siebie da Infinity)");
    }
}
