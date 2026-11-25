package pl.sages.jext.ch2.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Ćwiczenie 4 (slajdy 76-80): enum jako fabryka i strategia.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.ch2.practice.Operation}
 * (src/main/java/pl/sages/jext/ch2/practice/Operation.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.ch2.practice.Operation} (src/main/java/pl/sages/jext/ch2/practice/Operation.java)</li>
 * </ol>
 */
@DisplayName("Ćw. 4: enum jako fabryka i strategia")
class Zad04OperationTest {

    @Test
    @DisplayName("lambdy w stałych enuma")
    void operations() {
        assertEquals(5.0, Operation.ADD.apply(2, 3), 1e-9);
        assertEquals(-1.0, Operation.SUBTRACT.apply(2, 3), 1e-9);
        assertEquals(6.0, Operation.MULTIPLY.apply(2, 3), 1e-9);
        assertEquals(2.5, Operation.DIVIDE.apply(5, 2), 1e-9);
    }

    @Test
    @DisplayName("fabryka fromSymbol")
    void factory() {
        assertSame(Operation.MULTIPLY, Operation.fromSymbol("*"));
        assertSame(Operation.SUBTRACT, Operation.fromSymbol("-"));
        assertThrows(IllegalArgumentException.class, () -> Operation.fromSymbol("%"));
    }

    @Test
    @DisplayName("całość: evaluate(\"12 / 4\")")
    void evaluate() {
        assertEquals(3.0, Operation.evaluate("12 / 4"), 1e-9);
    }
}
