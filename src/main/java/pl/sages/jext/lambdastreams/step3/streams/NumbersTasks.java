package pl.sages.jext.lambdastreams.step3.streams;

import java.util.ArrayList;
import java.util.List;

/**
 * Krok 3: arkusz „Zadania zaawansowane”, sekcja „Stream”, zad. 1 (rozwiązanie jak w grupie 120, Stream2).
 * Test: {@code NumbersTasksTest}.
 * <p>
 * Przygotuj ArrayList z 10 liczbami z przedziału 0-100.
 * <ol>
 *     <li>za pomocą metody forEach wyświetl wszystkie liczby</li>
 *     <li>za pomocą streama posortuj liczby i wyświetl posortowane</li>
 *     <li>za pomocą streama odfiltruj liczby nieparzyste i policz ile pozostało elementów funkcją kończącą count()</li>
 *     <li>za pomocą streama odfiltruj wszystkie liczby powyżej 50, pozostałe liczby zbierz do nowej listy</li>
 *     <li>za pomocą streama posortuj liczby malejąco a następnie pobierz pierwszą z nich metodą findFirst</li>
 * </ol>
 * nowe: każdy punkt jest metodą, która dostaje listę, żeby test mógł ją sprawdzić. Lista z main to liczby z grupy 120.
 */
public class NumbersTasks {

    public static List<Integer> numbers() {
        return new ArrayList<>(List.of(56, 1, 22, 88, 44, 33, 77, 88, 1, 99));
    }

    /** 1) forEach: wyświetl wszystkie liczby, każdą w osobnej linii */
    public static void printAll(List<Integer> numbers) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** 2) stream: posortuj rosnąco i wyświetl, każdą w osobnej linii */
    public static void printSorted(List<Integer> numbers) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** 3) odfiltruj liczby nieparzyste (zostają parzyste) i policz, ile zostało: count() */
    public static long countEven(List<Integer> numbers) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** 4) odfiltruj liczby powyżej 50, pozostałe zbierz do nowej listy */
    public static List<Integer> notAboveFifty(List<Integer> numbers) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** 5) posortuj malejąco i pobierz pierwszą metodą findFirst */
    public static int findFirstDescending(List<Integer> numbers) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public static void main(String[] args) {
        List<Integer> numbers = numbers();
        printAll(numbers);
        System.out.println();
        printSorted(numbers);
        System.out.println();
        System.out.println(countEven(numbers));
        System.out.println(notAboveFifty(numbers));
        System.out.println(findFirstDescending(numbers));
    }
}
