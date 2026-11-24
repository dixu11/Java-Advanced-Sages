package pl.sages.jext.lambdastreams.step3.streams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sages.jext.lambdastreams.ConsoleCapture;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Lambdy i strumienie, krok 3: ArrayList z 10 liczbami")
class NumbersTasksTest {

    private final List<Integer> numbers = NumbersTasks.numbers(); // 56, 1, 22, 88, 44, 33, 77, 88, 1, 99

    @Test
    @DisplayName("1) forEach wyświetla wszystkie liczby")
    void printAll() {
        assertEquals(List.of("56", "1", "22", "88", "44", "33", "77", "88", "1", "99"),
                ConsoleCapture.lines(() -> NumbersTasks.printAll(numbers)), "każda liczba w osobnej linii, w kolejności z listy");
    }

    @Test
    @DisplayName("2) stream wyświetla posortowane")
    void printSorted() {
        assertEquals(List.of("1", "1", "22", "33", "44", "56", "77", "88", "88", "99"),
                ConsoleCapture.lines(() -> NumbersTasks.printSorted(numbers)));
        assertEquals(List.of(56, 1, 22, 88, 44, 33, 77, 88, 1, 99), numbers, "stream nie zmienia listy źródłowej");
    }

    @Test
    @DisplayName("3) ile zostaje po odfiltrowaniu nieparzystych")
    void countEven() {
        assertEquals(5, NumbersTasks.countEven(numbers), "zostają parzyste: 56, 22, 88, 44, 88");
    }

    @Test
    @DisplayName("4) liczby nie większe niż 50 w nowej liście")
    void notAboveFifty() {
        assertEquals(List.of(1, 22, 44, 33, 1), NumbersTasks.notAboveFifty(numbers));
    }

    @Test
    @DisplayName("5) malejąco i findFirst")
    void findFirstDescending() {
        assertEquals(99, NumbersTasks.findFirstDescending(numbers));
    }
}
