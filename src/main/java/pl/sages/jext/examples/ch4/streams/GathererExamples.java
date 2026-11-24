package pl.sages.jext.examples.ch4.streams;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

/**
 * Slajdy 142-146: wbudowane gatherery ({@code windowFixed}, {@code windowSliding}, {@code fold}, {@code scan},
 * {@code mapConcurrent}), własny Gatherer {@code BiggestInt} i {@code mapWithMaxErrors}.
 * <p>
 * Poprawka: slajd 146 woła {@code throwingFunction.map(element)}, a {@code TryFunction}
 * ze slajdu 127 ma metodę {@code apply}. Slajd 145 pokazuje tylko importy {@code AtomicInteger} i {@code Gatherer};
 * potrzebne są też {@code Supplier} i {@code BinaryOperator}.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch4.streams.GathererExamples}
 */
public class GathererExamples {

    // --- slajd 145 ---
    record BiggestInt(int limit) implements Gatherer<Integer, AtomicInteger, Integer> {
        @Override
        public Supplier<AtomicInteger> initializer() {
            // obiekt pamięci, tu wykorzystany został AtomicInteger do przechowania znalezionej liczby
            return () -> new AtomicInteger(Integer.MIN_VALUE);
        }

        @Override
        public Integrator<AtomicInteger, Integer, Integer> integrator() {
            return Integrator.of((max, element, downstream) -> {
                if (max.accumulateAndGet(element, Math::max) >= limit) {
                    downstream.push(element);
                    return false; // zakończenie dalszego procesowania, bo został osiągnięty wejściowy limit
                }
                return true; // kontynuujemy, bo limit nie został jeszcze osiągnięty
            });
        }

        @Override
        public BinaryOperator<AtomicInteger> combiner() {
            // najprostsza implementacja w przypadku, gdy nie zamierzamy używać w strumieniach z paralelizmem
            return (leftMax, rightMax) -> leftMax;
        }
    }

    // --- slajd 146 (TryFunction ze slajdu 127) ---
    @FunctionalInterface
    public interface TryFunction<T, R> {
        R apply(T t) throws Exception;
    }

    // Odpowiednik map, ale z pominięciem błędów.
    // Przerywa procesowanie strumienia bez rzucenia wyjątku, jeśli liczba błędów osiąga maxErrors.
    static <T, R> Gatherer<T, AtomicInteger, R> mapWithMaxErrors(TryFunction<T, R> throwingFunction,
                                                                 int maxErrors) {
        // sequential oznacza narzucenie sekwencyjności nawet wtedy, jeśli strumień parallel
        return Gatherer.ofSequential(
                AtomicInteger::new,   // można pominąć, jeśli Gatherer nie potrzebuje (wtedy zakładany jest Void)
                (state, element, downstream) -> {
                    try {  // wykonanie przekazanej lambdy TryFunction z możliwością pojawienia się wyjątku
                        downstream.push(throwingFunction.apply(element)); // na slajdzie: map(element)
                    } catch (Throwable e) {
                        if (state.incrementAndGet() >= maxErrors) {
                            return false; // przerywamy dalsze procesowanie strumienia
                        }
                    }
                    return true; // kontynuacja procesowania
                });
    }

    public static void main(String[] args) {
        // --- slajd 142 ---
        List<Integer> summedPairs = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .gather(Gatherers.windowFixed(2)) // wbudowany typ Gatherer
                .map(numList -> numList.getFirst() + numList.getLast())
                .toList(); // [3, 7, 11, 15, 19]
        System.out.println("100: " + summedPairs);

        // --- slajd 143 ---
        var semicolonSeparated = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .gather(Gatherers.fold(() -> "",
                        (result, element) -> {
                            if (result.equals("")) return element.toString();
                            return result + ";" + element;
                        })).findFirst()
                .get(); // wynikowy string: 1;2;3;4;5;6;7;8;9
        var scanned = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).gather(Gatherers.scan(
                        () -> 100,
                        (current, next) -> current + next))
                .toList();
        List<List<Integer>> windows = Stream.of(1, 2, 3, 4, 5, 6, 7, 8)
                .gather(Gatherers.windowFixed(3))
                .toList(); // [[1, 2, 3], [4, 5, 6], [7, 8]]
        List<List<Integer>> moreWindows
                = Stream.of(1, 2, 3, 4, 5, 6, 7)
                .gather(Gatherers.windowSliding(3))
                .toList();
        var concurrent = Stream.of("a", "b", "c", "d")
                .gather(Gatherers.mapConcurrent(4, s -> s.toUpperCase() + "@" + (Thread.currentThread().isVirtual() ? "wirtualny" : "platformowy")))
                .toList();
        System.out.println("101: fold = " + semicolonSeparated);
        System.out.println("101: scan = " + scanned);
        System.out.println("101: windowFixed(3) = " + windows);
        System.out.println("101: windowSliding(3) = " + moreWindows);
        System.out.println("101: mapConcurrent = " + concurrent + " (kolejność zachowana)");

        // --- slajd 145 ---
        System.out.println("103: BiggestInt(5) = " + Stream.of(1, 4, 2, 7, 3, 9).gather(new BiggestInt(5)).toList()
                + " (pierwszy element >= 5, reszta niepobrana)");

        // --- slajd 146 ---
        System.out.print("104: ");
        Stream.of("1", "2137", "lol")
                .gather(mapWithMaxErrors(Long::valueOf, 1))
                .forEach(x -> System.out.print(x + " ")); // 1 2137
        System.out.println();
    }
}
