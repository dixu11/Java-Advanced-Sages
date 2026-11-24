package pl.sages.jext.ch4.streams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Ćw. 8: stream gatherers")
class WindowsTest {

    @Test
    @DisplayName("a) paczki windowFixed")
    void batches() {
        assertEquals(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7)),
                Windows.inBatches(List.of(1, 2, 3, 4, 5, 6, 7), 3));
    }

    @Test
    @DisplayName("b) średnia krocząca windowSliding")
    void movingAverage() {
        assertEquals(List.of(2.0, 3.0, 4.0), Windows.movingAverage(List.of(1, 2, 3, 4, 5), 3));
    }

    @Test
    @DisplayName("c) sumy narastające scan")
    void runningTotals() {
        assertEquals(List.of(1, 3, 6, 10), Windows.runningTotals(List.of(1, 2, 3, 4)));
    }

    @Test
    @DisplayName("d) dla chętnych: własny Gatherer")
    void customGatherer() {
        assertEquals(List.of(1, 2, 1, 3),
                Stream.of(1, 1, 2, 2, 2, 1, 3, 3).gather(Windows.<Integer>withoutAdjacentDuplicates()).toList());
    }
}
