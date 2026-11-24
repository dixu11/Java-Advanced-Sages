package pl.sages.jext.ch4.streams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sages.jext.data.Order;
import pl.sages.jext.data.Orders;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Ćw. 7: NIO i kolektory")
class ReportsTest {

    private final List<Order> orders = Orders.all();

    @Test
    @DisplayName("a) wczytanie CSV przez Files.lines")
    void load() throws Exception {
        Path file = Path.of(getClass().getResource("/orders.csv").toURI());
        List<Order> loaded = Reports.load(file);
        assertEquals(24, loaded.size(), "czy pominąłeś nagłówek?");
        assertEquals(orders, loaded);
    }

    @Test
    @DisplayName("b) liczba zamówień w miastach")
    void cities() {
        assertEquals(Map.of("Warszawa", 9L, "Kraków", 6L, "Gdańsk", 3L, "Poznań", 3L, "Wrocław", 3L),
                Reports.countByCity(orders));
    }

    @Test
    @DisplayName("c) suma kwot w kategoriach")
    void categories() {
        Map<String, Double> totals = Reports.totalByCategory(orders);
        assertEquals(4, totals.size());
        assertEquals(17145.00, totals.get("Elektronika"), 0.001);
        assertEquals(458.89, totals.get("Książki"), 0.001);
        assertEquals(3334.90, totals.get("Dom"), 0.001);
        assertEquals(3270.00, totals.get("Sport"), 0.001);
    }

    @Test
    @DisplayName("d) podział na duże i małe")
    void partition() {
        Map<Boolean, List<Order>> result = Reports.partitionByAmount(orders, 1000);
        assertEquals(List.of(2L, 8L, 9L, 11L, 15L, 16L, 21L, 24L),
                result.get(true).stream().map(Order::id).toList());
        assertEquals(16, result.get(false).size());
    }

    @Test
    @DisplayName("e) średnia kwota")
    void average() {
        assertEquals(1008.6996, Reports.averageAmount(orders), 0.001);
        assertEquals(0.0, Reports.averageAmount(List.of()), 0.001);
    }

    @Test
    @DisplayName("f) min i max przez teeing")
    void minMax() {
        assertEquals(new Reports.MinMax(39.99, 5400.00), Reports.minAndMax(orders));
    }

    @Test
    @DisplayName("g) dla chętnych: top 3 klienci")
    void top3() {
        assertEquals(List.of("Anna Kowalska", "Ola Kamińska", "Jan Nowak"), Reports.top3Customers(orders));
    }
}
