package pl.sages.jext.ch2.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Ćwiczenie 3 (slajdy 68-74): generyki: extends, super i ograniczenia.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.ch2.practice.Generics}
 * (src/main/java/pl/sages/jext/ch2/practice/Generics.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.ch2.practice.Generics} (src/main/java/pl/sages/jext/ch2/practice/Generics.java)</li>
 * </ol>
 */
@DisplayName("Ćw. 3: generyki extends i super")
class Zad03GenericsTest {

    @Test
    @DisplayName("a) suma dla List<Integer> i List<Double>")
    void sum() {
        List<Integer> integers = List.of(1, 2, 3);
        List<Double> fractions = List.of(0.5, 1.5);
        assertEquals(6.0, Generics.sum(integers), 1e-9);
        assertEquals(2.0, Generics.sum(fractions), 1e-9);
    }

    @Test
    @DisplayName("b) dopisywanie do List<Number> i List<Object>")
    void append() {
        List<Number> numbers = new ArrayList<>();
        Generics.appendSequence(numbers, 3);
        assertEquals(List.of(1, 2, 3), numbers);

        List<Object> objects = new ArrayList<>(List.of("start"));
        Generics.appendSequence(objects, 2);
        assertEquals(List.of("start", 1, 2), objects);
    }

    @Test
    @DisplayName("c) kopiowanie z List<Integer> do List<Number>")
    void copy() {
        List<Integer> source = List.of(7, 8);
        List<Number> target = new ArrayList<>(List.of(1.5));
        Generics.copy(source, target);
        assertEquals(List.of(1.5, 7, 8), target);
    }

    @Test
    @DisplayName("d) największy element")
    void largest() {
        assertEquals(9, Generics.largest(List.of(3, 9, 1)));
        assertEquals("zebra", Generics.largest(List.of("ala", "zebra", "kot")));
        assertThrows(IllegalArgumentException.class, () -> Generics.largest(List.<Integer>of()));
    }
}
