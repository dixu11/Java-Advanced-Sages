package pl.sages.jext.examples.ch4.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Slajdy 132-135: strumienie prymitywne, NIO ({@code walk}, {@code find}, {@code lines}), {@code mapMulti}
 * i nietypowe sposoby tworzenia strumieni.
 * <p>
 * Poprawka (dziennik błędów): slajd 132 {@code .avg()} nie istnieje, jest {@code .average()} ({@code OptionalDouble}).
 * NIO działa na folderze {@code src/main/java/pl/sages/jext/examples} (uruchamiaj z folderu projektu).
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.examples.ch4.streams.PrimitiveStreamsAndNio}
 */
public class PrimitiveStreamsAndNio {

    public static void main(String[] args) throws IOException {
        // --- slajd 132 ---
        IntSummaryStatistics stats = IntStream.of(1, 2, 3, 4, 5).summaryStatistics();
        var avg = stats.getAverage();
        var max = stats.getMax();
        var min = stats.getMin();
        var sum = stats.getSum();
        System.out.println("88: avg=" + avg + " max=" + max + " min=" + min + " sum=" + sum);
        var date1 = LocalDate.of(2020, 1, 1);
        var date2 = LocalDate.of(2022, 1, 1);
        var date3 = LocalDate.of(2024, 1, 1);
        var date4 = LocalDate.of(2026, 1, 1);
        var averageYear = Stream.of(date1, date2, date3, date4).mapToInt(LocalDate::getYear).average(); // na slajdzie avg()
        System.out.println("88: averageYear = " + averageYear);

        // --- slajd 134 ---
        Path start = Path.of("src/main/java/pl/sages/jext/examples");
        if (!Files.isDirectory(start)) {
            start = Path.of(".");
        }
        System.out.println("90: walk, katalogi:");
        try (var stream = Files.walk(start)) {
            stream.filter(Files::isDirectory)
                    .sorted()
                    .forEach(p -> System.out.println("   " + p));
        } catch (IOException e) {
            System.out.println("obsługa sytuacji wyjątkowej: " + e);
        }
        // Predykat wykorzystuje standardowe atrybuty elementów systemu plików
        BiPredicate<Path, BasicFileAttributes> isDirectoryPredicate = (path, attr) -> attr.isDirectory();
        // przeszukujemy wgłąb bez ograniczenia
        try (var stream = Files.find(start, Integer.MAX_VALUE, isDirectoryPredicate)) {
            System.out.println("90: find, liczba katalogów = " + stream.count());
        } catch (IOException e) {
            System.out.println("obsługa sytuacji wyjątkowej: " + e);
        }

        // --- slajd 135 ---
        Path location = Path.of("pom.xml");
        if (Files.exists(location)) {
            try (Stream<String> stream = Files.lines(location)) {
                System.out.println("91: linie pom.xml z <artifactId>: " + stream.map(String::strip)
                        .filter(l -> l.startsWith("<artifactId>")).count());
            }
        }

        // --- temat uzupełniający (bez slajdu) ---
        List<Integer> divisors = List.of(10, 15, 21).stream()
                .<Integer>mapMulti((number, consumer) -> { // consumer reprezentuje kolejny etap strumienia
                    for (int i = 1; i <= number; i++) {
                        if (number % i == 0) {
                            consumer.accept(i);
                        }
                    }
                }).collect(Collectors.toList());
        System.out.println("92: dzielniki 10, 15, 21: " + divisors);

        // --- temat uzupełniający (bez slajdu) ---
        System.out.println("93.1: " + LocalDate.of(2026, 11, 23).datesUntil(LocalDate.of(2026, 12, 21), Period.ofWeeks(1)).toList());
        int[] array = {5, 6, 7, 8, 9};
        System.out.println("93.2: " + Arrays.toString(Arrays.stream(array, 1, 3).toArray()));
        System.out.println("93.3: " + IntStream.rangeClosed(1, 5).sum() + ", " + LongStream.rangeClosed(1, 20).reduce(1, (a, b) -> a * b));
        System.out.println("93.4: " + Arrays.toString(new Random(42).ints(1, 7).limit(5).toArray()));
        System.out.println("93.5: " + Stream.generate(new AtomicInteger()::incrementAndGet).limit(5).toList()
                + " (strumień nieskończony: bez limit się nie skończy)");
        System.out.print("93.6: ");
        IntStream.iterate(
                        1, idx -> idx < 5, idx -> idx + 1)
                .forEachOrdered(i -> System.out.print(i + " "));
        System.out.println();
    }
}
