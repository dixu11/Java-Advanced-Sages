package pl.sages.jext.ch4.streams;

import pl.sages.jext.data.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Ćwiczenie 7 (dla chętnych, bez slajdu): NIO i kolektory poza kursem, na pliku src/main/resources/orders.csv.
 * Każda metoda to 1-3 linijki strumienia.
 */
public class Reports {

    /** Wynik ćwiczenia 7f. */
    public record MinMax(double min, double max) {
    }

    /**
     * a) Wczytaj zamówienia z pliku CSV. Pierwsza linia to nagłówek: pomiń ją.
     * Do zamiany linii na rekord jest gotowe Order.fromCsv. Pamiętaj o zamknięciu strumienia
     * (try-with-resources), bo Files.lines trzyma otwarty plik.
     */
    public static List<Order> load(Path file) throws IOException {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // try (Stream<String> lines = Files.lines(file)) {
        //     return lines.skip(1).map(Order::fromCsv).toList();
        // }
        // KONIEC ROZWIĄZANIA
    }

    /** b) Liczba zamówień w każdym mieście (groupingBy + counting). */
    public static Map<String, Long> countByCity(List<Order> orders) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return orders.stream().collect(Collectors.groupingBy(Order::city, Collectors.counting()));
        // KONIEC ROZWIĄZANIA
    }

    /** c) Suma kwot w każdej kategorii (groupingBy + summingDouble). */
    public static Map<String, Double> totalByCategory(List<Order> orders) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return orders.stream()
        //         .collect(Collectors.groupingBy(Order::category, Collectors.summingDouble(Order::amount)));
        // KONIEC ROZWIĄZANIA
    }

    /**
     * d) Podziel zamówienia na dwie grupy: true = kwota większa niż threshold, false = reszta
     * (partitioningBy).
     */
    public static Map<Boolean, List<Order>> partitionByAmount(List<Order> orders, double threshold) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return orders.stream().collect(Collectors.partitioningBy(o -> o.amount() > threshold));
        // KONIEC ROZWIĄZANIA
    }

    /** e) Średnia kwota wszystkich zamówień, 0 dla pustej listy (mapToDouble + average). */
    public static double averageAmount(List<Order> orders) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return orders.stream().mapToDouble(Order::amount).average().orElse(0);
        // KONIEC ROZWIĄZANIA
    }

    /**
     * f) Dla chętnych, bez slajdu: najmniejsza i największa kwota w JEDNYM przejściu strumienia: Collectors.teeing
     * z dwoma kolektorami (summarizingDouble też by zadziałał, ale tu ćwiczymy teeing). Podpowiedź:
     * teeing(mapping(Order::amount, minBy(...)), mapping(Order::amount, maxBy(...)), (min, max) -> ...).
     * Możesz założyć, że lista nie jest pusta.
     */
    public static MinMax minAndMax(List<Order> orders) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return orders.stream().collect(Collectors.teeing(
        //         Collectors.mapping(Order::amount, Collectors.minBy(Double::compare)),
        //         Collectors.mapping(Order::amount, Collectors.maxBy(Double::compare)),
        //         (min, max) -> new MinMax(min.orElseThrow(), max.orElseThrow())));
        // KONIEC ROZWIĄZANIA
    }

    /**
     * g) Dla chętnych: nazwiska 3 klientów z największą sumą zamówień (bez anulowanych),
     * od największej sumy. Podpowiedź: groupingBy + summingDouble, potem strumień po entrySet().
     */
    public static List<String> top3Customers(List<Order> orders) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return orders.stream()
        //         .filter(o -> o.status() != Order.Status.CANCELLED)
        //         .collect(Collectors.groupingBy(Order::customer, Collectors.summingDouble(Order::amount)))
        //         .entrySet().stream()
        //         .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
        //         .limit(3)
        //         .map(Map.Entry::getKey)
        //         .toList();
        // KONIEC ROZWIĄZANIA
    }
}
