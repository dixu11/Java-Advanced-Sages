package pl.sages.jext.ch4.streams;

import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

/**
 * Ćwiczenie 8 (slajdy 142-146): stream gatherers (finalne od JDK 24).
 */
public class Windows {

    /**
     * a) Podziel listę na paczki po rozmiar elementów, ostatnia może być krótsza.
     * [1..7], rozmiar 3: [[1, 2, 3], [4, 5, 6], [7]]. Gatherers.windowFixed.
     */
    public static List<List<Integer>> inBatches(List<Integer> numbers, int size) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * b) Średnia krocząca: średnia z każdego okna o podanym rozmiarze, okna zachodzą na siebie.
     * [1, 2, 3, 4, 5], okno 3: [2.0, 3.0, 4.0]. Gatherers.windowSliding, potem map na średnią.
     */
    public static List<Double> movingAverage(List<Integer> numbers, int window) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * c) Sumy narastające: [1, 2, 3, 4] daje [1, 3, 6, 10]. Gatherers.scan (slajd 143).
     */
    public static List<Integer> runningTotals(List<Integer> numbers) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * d) Dla chętnych (slajdy 144-146): własny Gatherer, który przepuszcza element tylko wtedy,
     * gdy różni się od poprzedniego: [1, 1, 2, 2, 2, 1, 3, 3] daje [1, 2, 1, 3].
     * Podpowiedź: Gatherer.ofSequential(state, (state, element, downstream) -> ...), state może
     * być jednoelementową tablicą Object[] z poprzednim elementem.
     */
    public static <T> Gatherer<T, ?, T> withoutAdjacentDuplicates() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
