package pl.sages.jext.examples.ch4.streams;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Demo Stream API na losowych liczbach: na start COUNT wartości z przedziału [FROM, TO] (obie granice włącznie),
 * potem po kolei najczęstsze operacje strumieni. Do prowadzenia na żywo: zmień stałe albo dopisz własny krok.
 * <p>
 * SEED ustawiony na liczbę daje za każdym razem te same wartości (łatwiej omawiać wynik z grupą),
 * {@code null} losuje za każdym uruchomieniem od nowa.
 * <p>
 * Uruchomienie: zielona strzałka przy {@code main} albo
 * {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch4.streams.RandomNumbersDemo}
 */
public class RandomNumbersDemo {

    static final int COUNT = 20;
    static final int FROM = 1;
    static final int TO = 100;
    static final Long SEED = 42L;

    public static void main(String[] args) {
        Random random = SEED == null ? new Random() : new Random(SEED);

        // 0. Generowanie: trzy sposoby na to samo. Górna granica w ints() i nextInt() jest wyłączna, stąd TO + 1.
        List<Integer> numbers = random.ints(COUNT, FROM, TO + 1)   // IntStream z gotowym licznikiem i przedziałem
                .boxed()                                           // int -> Integer, żeby zebrać do List<Integer>
                .toList();
        List<Integer> viaGenerate = Stream.generate(() -> random.nextInt(FROM, TO + 1))
                .limit(COUNT)                                      // generate() jest nieskończony, limit go ucina
                .toList();
        List<Integer> viaRange = IntStream.range(0, COUNT)
                .map(i -> random.nextInt(FROM, TO + 1))
                .boxed()
                .toList();
        section("0. Losowe liczby", numbers);
        System.out.println("   (to samo przez Stream.generate: " + viaGenerate.size()
                + " liczb, przez IntStream.range: " + viaRange.size() + " liczb)");

        // 1. filter i map
        section("1. filter: parzyste", numbers.stream().filter(n -> n % 2 == 0).toList());
        section("1. map: kwadraty", numbers.stream().map(n -> n * n).toList());

        // 2. sorted, distinct, reversed
        section("2. sorted", numbers.stream().sorted().toList());
        section("2. distinct + sorted malejąco", numbers.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .toList());

        // 3. limit i skip: trzy największe, wszystko poza pięcioma pierwszymi
        section("3. top 3", numbers.stream().sorted(Comparator.reverseOrder()).limit(3).toList());
        section("3. skip(5)", numbers.stream().skip(5).toList());

        // 4. Redukcja: reduce, sum na IntStream, statystyki w jednym przejściu
        int sumByReduce = numbers.stream().reduce(0, Integer::sum);
        int sumByIntStream = numbers.stream().mapToInt(Integer::intValue).sum();
        Optional<Integer> max = numbers.stream().max(Integer::compare);
        IntSummaryStatistics stats = numbers.stream().mapToInt(Integer::intValue).summaryStatistics();
        section("4. suma reduce / sum", sumByReduce + " / " + sumByIntStream);
        section("4. max (Optional)", max.orElseThrow());
        section("4. summaryStatistics", stats);

        // 5. Pytania tak/nie: kończą się, gdy znają odpowiedź (short-circuit)
        section("5. anyMatch > 90", numbers.stream().anyMatch(n -> n > 90));
        section("5. allMatch >= FROM", numbers.stream().allMatch(n -> n >= FROM));
        section("5. noneMatch == 0", numbers.stream().noneMatch(n -> n == 0));

        // 6. findFirst zwraca Optional, bo takiej liczby może nie być
        Optional<Integer> firstAbove90 = numbers.stream().filter(n -> n > 90).findFirst();
        section("6. pierwsza > 90", firstAbove90.map(String::valueOf).orElse("brak"));

        // 7. Kolektory: grupowanie po dziesiątkach, podział na parzyste i nieparzyste, sklejanie tekstu
        Map<Integer, Long> byTens = numbers.stream()
                .collect(Collectors.groupingBy(n -> n / 10 * 10, java.util.TreeMap::new, Collectors.counting()));
        Map<Boolean, List<Integer>> evenOdd = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        String joined = numbers.stream().map(String::valueOf).collect(Collectors.joining(", ", "[", "]"));
        section("7. groupingBy dziesiątki -> ile", byTens);
        section("7. partitioningBy parzyste", evenOdd);
        section("7. joining", joined);

        // 8. Duplikaty: policz wystąpienia i zostaw te, które są więcej niż raz
        Map<Integer, Long> duplicates = numbers.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        section("8. duplikaty (liczba -> ile razy)", duplicates.isEmpty() ? "brak" : duplicates);

        // 9. Leniwość: peek pokazuje, że elementy idą po jednym przez cały łańcuch,
        //    a limit(2) kończy pracę, zanim strumień dojdzie do końca listy
        System.out.println("\n9. Leniwość (peek):");
        List<Integer> lazy = numbers.stream()
                .peek(n -> System.out.println("   wchodzi " + n))
                .filter(n -> n > 50)
                .peek(n -> System.out.println("   przeszło filter " + n))
                .map(n -> n * 10)
                .limit(2)
                .toList();
        System.out.println("   wynik: " + lazy);

        // 10. Strumień jest jednorazowy
        Stream<Integer> once = numbers.stream();
        once.count();
        try {
            once.count();
        } catch (IllegalStateException e) {
            section("10. drugie użycie strumienia", e.getMessage());
        }
    }

    static void section(String title, Object value) {
        System.out.println("\n" + title + ":\n   " + value);
    }
}
