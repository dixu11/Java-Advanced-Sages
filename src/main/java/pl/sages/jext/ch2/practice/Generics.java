package pl.sages.jext.ch2.practice;

import java.util.List;

/**
 * Ćwiczenie 3 (slajdy 68-74): wildcardy {@code ? extends} i {@code ? super}.
 * Reguła PECS: Producer Extends, Consumer Super.
 */
public class Generics {

    /**
     * a) Zsumuj liczby z listy. Lista jest PRODUCENTEM (tylko z niej czytamy), dlatego
     * przyjmuje List<Integer>, List<Double> i List<Number>. Podpowiedź: Number.doubleValue().
     */
    public static double sum(List<? extends Number> numbers) {
        // TODO
        double sum = 0;
        for (Number n : numbers) {
            sum += n.doubleValue();
        }
        return sum;
    }

    /**
     * b) Dopisz do listy liczby 1, 2, ..., count. Lista jest KONSUMENTEM (tylko do niej piszemy),
     * dlatego przyjmuje List<Integer>, List<Number> i List<Object>.
     */
    public static void appendSequence(List<? super Integer> target, int count) {
        // TODO
        for (int i = 1; i <= count; i++) {
            target.add(i);
        }
    }

    /**
     * c) Skopiuj wszystkie elementy ze źródła do celu. Uzupełnij tylko ciało metody,
     * ale zastanów się, dlaczego sygnatura wygląda właśnie tak.
     */
    public static <T> void copy(List<? extends T> source, List<? super T> target) {
        // TODO
        target.addAll(source);
    }

    /**
     * d) Zwróć największy element. Ograniczenie {@code T extends Comparable<? super T>} gwarantuje,
     * że elementy da się porównać (slajd 74). Dla pustej listy rzuć IllegalArgumentException.
     */
    public static <T extends Comparable<? super T>> T largest(List<? extends T> list) {
        // TODO
        if (list.isEmpty()) {
            throw new IllegalArgumentException("pusta lista");
        }
        T max = list.getFirst();
        for (T element : list) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }
        return max;
    }
}
