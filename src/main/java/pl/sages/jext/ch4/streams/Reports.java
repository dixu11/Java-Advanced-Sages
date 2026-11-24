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
 * Ćwiczenie 7 (slajdy 130-140): NIO i kolektory na pliku src/main/resources/orders.csv.
 * Każda metoda to 1-3 linijki strumienia.
 */
public class Reports {

    /** Wynik ćwiczenia 7f. */
    public record MinMax(double min, double max) {
    }

    /**
     * a) Wczytaj zamówienia z pliku CSV (slajd 133). Pierwsza linia to nagłówek: pomiń ją.
     * Do zamiany linii na rekord jest gotowe Order.fromCsv. Pamiętaj o zamknięciu strumienia
     * (try-with-resources), bo Files.lines trzyma otwarty plik.
     */
    public static List<Order> load(Path file) throws IOException {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** b) Liczba zamówień w każdym mieście (groupingBy + counting, slajd 136). */
    public static Map<String, Long> countByCity(List<Order> orders) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** c) Suma kwot w każdej kategorii (groupingBy + summingDouble). */
    public static Map<String, Double> totalByCategory(List<Order> orders) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * d) Podziel zamówienia na dwie grupy: true = kwota większa niż threshold, false = reszta
     * (partitioningBy, slajd 136).
     */
    public static Map<Boolean, List<Order>> partitionByAmount(List<Order> orders, double threshold) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** e) Średnia kwota wszystkich zamówień, 0 dla pustej listy (mapToDouble + average, slajd 130). */
    public static double averageAmount(List<Order> orders) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * f) Najmniejsza i największa kwota w JEDNYM przejściu strumienia: Collectors.teeing (slajd 140)
     * z dwoma kolektorami (summarizingDouble też by zadziałał, ale tu ćwiczymy teeing). Podpowiedź:
     * teeing(mapping(Order::amount, minBy(...)), mapping(Order::amount, maxBy(...)), (min, max) -> ...).
     * Możesz założyć, że lista nie jest pusta.
     */
    public static MinMax minAndMax(List<Order> orders) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /**
     * g) Dla chętnych: nazwiska 3 klientów z największą sumą zamówień (bez anulowanych),
     * od największej sumy. Podpowiedź: groupingBy + summingDouble, potem strumień po entrySet().
     */
    public static List<String> top3Customers(List<Order> orders) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
