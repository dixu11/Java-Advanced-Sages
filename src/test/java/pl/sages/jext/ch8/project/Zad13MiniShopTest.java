package pl.sages.jext.ch8.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ćwiczenie 13 (powtórka, bez osobnego slajdu): Mini-sklep: od JSON-a do raportu, powtórka kursu.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.ch8.project.MiniShop}
 * (src/main/java/pl/sages/jext/ch8/project/MiniShop.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.ch8.project.MiniShop} (src/main/java/pl/sages/jext/ch8/project/MiniShop.java)</li>
 * <li>{@code pl.sages.jext.ch8.project.Payment} (src/main/java/pl/sages/jext/ch8/project/Payment.java)</li>
 * <li>{@code pl.sages.jext.ch8.project.Order} (src/main/java/pl/sages/jext/ch8/project/Order.java)</li>
 * <li>{@code pl.sages.jext.ch8.project.OrderResult} (src/main/java/pl/sages/jext/ch8/project/OrderResult.java)</li>
 * <li>{@code pl.sages.jext.ch8.project.PaymentGateway} (src/main/java/pl/sages/jext/ch8/project/PaymentGateway.java)</li>
 * <li>{@code pl.sages.jext.ch8.project.GatewayBenchmark} (src/main/java/pl/sages/jext/ch8/project/GatewayBenchmark.java)</li>
 * <li>{@code pl.sages.jext.ch8.project.Report} (src/main/java/pl/sages/jext/ch8/project/Report.java)</li>
 * <li>{@code pl.sages.jext.ch8.project.ReportMapper} (src/main/java/pl/sages/jext/ch8/project/ReportMapper.java)</li>
 * <li>{@code pl.sages.jext.ch8.project.OrderDto} (src/main/java/pl/sages/jext/ch8/project/OrderDto.java)</li>
 * <li>{@code pl.sages.jext.ch8.project.ReportDto} (src/main/java/pl/sages/jext/ch8/project/ReportDto.java)</li>
 * </ol>
 */
@DisplayName("Ćw. 13: Mini-sklep: od JSON-a do raportu")
class Zad13MiniShopTest {
    @TempDir Path temp;

    private Order order(String id, String customer, String amount, Payment payment) {
        return new Order(id, customer, amount == null ? null : new BigDecimal(amount), payment);
    }

    @Test @DisplayName("a: Jackson odczytuje zamówienia i trzy warianty płatności")
    void loadOrders() throws Exception {
        var orders = MiniShop.loadOrders();
        assertEquals(8, orders.size());
        assertEquals("O-1", orders.getFirst().id());
        assertEquals("Ala", orders.getFirst().customer());
        assertEquals(new BigDecimal("120.00"), orders.getFirst().amount());
        assertEquals(new Payment.Card("4111111111111111"), orders.getFirst().payment());
        assertEquals(new Payment.Blik("123456"), orders.get(1).payment());
        assertEquals(new Payment.Transfer("PL001234"), orders.get(2).payment());
    }

    @Test @DisplayName("b: wzorce rekordów i when odrzucają niepoprawne płatności")
    void validateOrders() {
        var valid = order("1", "Ala", "10.00", new Payment.Blik("123456"));
        assertEquals(new OrderResult.Accepted(valid), MiniShop.validate(valid));
        for (String code : new String[]{"12345", "1234567", "abcdef", "１２３４５６", "123 56", null}) {
            var invalid = order("2", "Ala", "10", new Payment.Blik(code));
            assertEquals(new OrderResult.Rejected(invalid, "Niepoprawny BLIK"), MiniShop.validate(invalid));
        }
        for (String number : new String[]{"", "  ", null}) {
            var invalid = order("3", "Ala", "10", new Payment.Card(number));
            assertEquals(new OrderResult.Rejected(invalid, "Pusty numer karty"), MiniShop.validate(invalid));
        }
        var card = order("4", "Ala", "2000", new Payment.Card("4111"));
        assertEquals(new OrderResult.Accepted(card), MiniShop.validate(card));
        var missing = order("5", "Ala", "10", null);
        assertEquals(new OrderResult.Rejected(missing, "Brak płatności"), MiniShop.validate(missing));
    }

    @Test @DisplayName("b: przelew do 1000.00 włącznie jest dozwolony")
    void transferBoundary() {
        var accepted = order("1", "Ala", "1000.00", new Payment.Transfer("PL00"));
        var rejected = order("2", "Ala", "1000.01", new Payment.Transfer("PL00"));
        assertEquals(new OrderResult.Accepted(accepted), MiniShop.validate(accepted));
        assertEquals(new OrderResult.Rejected(rejected, "Przekroczony limit przelewu"), MiniShop.validate(rejected));
    }

    @Test @DisplayName("b: kwota null, zero i ujemna są odrzucane przed płatnością")
    void invalidAmounts() {
        for (String amount : new String[]{null, "0.00", "-0.01"}) {
            var invalid = order("1", "Ala", amount, new Payment.Blik("123456"));
            assertEquals(new OrderResult.Rejected(invalid, "Niepoprawna kwota"), MiniShop.validate(invalid));
        }
    }

    @Test @DisplayName("c: obie pule zachowują kolejność i płacą tylko za Accepted")
    void processOrders() throws Exception {
        var good = order("1", "Ala", "10", new Payment.Blik("123456"));
        var bad = order("2", "Ala", "10", new Payment.Blik("bad"));
        for (boolean virtual : new boolean[]{true, false}) {
            var gateway = new PaymentGateway(Duration.ofMillis(1), 20);
            assertEquals(List.of(new OrderResult.Accepted(good), new OrderResult.Rejected(bad, "Niepoprawny BLIK")),
                MiniShop.process(List.of(good, bad), virtual, gateway));
            assertEquals(1, gateway.calls());
            assertEquals(20, gateway.permits().availablePermits());
            assertEquals(List.of(), MiniShop.process(List.of(), virtual, gateway));
        }
    }

    @Test @DisplayName("c: limit bramki obowiązuje także przy wielu wątkach wirtualnych")
    void gatewayLimit() throws Exception {
        var orders = IntStream.range(0, 200).mapToObj(i -> order("" + i, "Ala", "10", new Payment.Blik("123456"))).toList();
        for (boolean virtual : new boolean[]{true, false}) {
            var gateway = new PaymentGateway(Duration.ofMillis(5), 3);
            var results = MiniShop.process(orders, virtual, gateway);
            assertEquals(200, results.size());
            assertTrue(results.stream().allMatch(r -> r instanceof OrderResult.Accepted));
            assertEquals(orders, results.stream().map(r -> ((OrderResult.Accepted) r).order()).toList());
            assertEquals(200, gateway.calls());
            assertEquals(virtual ? 200 : 0, gateway.virtualCalls(), "Sprawdź rodzaj użytych wątków");
            assertTrue(gateway.maxConcurrentCalls() >= 1 && gateway.maxConcurrentCalls() <= 3);
            assertEquals(3, gateway.permits().availablePermits(), "Nie wolno zgubić pozwolenia");
        }
    }

    @Test @DisplayName("c: wątki wirtualne obsługują komplet 10 000 zamówień")
    void tenThousandOrders() throws Exception {
        var orders = IntStream.range(0, 10_000).mapToObj(i -> order("" + i, "Ala", "1", new Payment.Blik("123456"))).toList();
        var gateway = new PaymentGateway(Duration.ZERO, 20);
        assertEquals(10_000, MiniShop.process(orders, true, gateway).size());
        assertEquals(10_000, gateway.calls());
        assertTrue(gateway.maxConcurrentCalls() <= 20);
        assertEquals(20, gateway.permits().availablePermits());
    }

    @Test @DisplayName("d: sumy BigDecimal, liczby odmów, top 3 i kolejność remisów")
    void report() {
        var card = new Payment.Card("4111");
        List<OrderResult> results = List.of(
            new OrderResult.Accepted(order("1", "Ala", "10.10", card)),
            new OrderResult.Accepted(order("2", "Ala", "20.20", card)),
            new OrderResult.Accepted(order("3", "Bartek", "50.00", card)),
            new OrderResult.Accepted(order("4", "Celina", "30.30", card)),
            new OrderResult.Accepted(order("5", "Daniel", "1.00", card)),
            new OrderResult.Rejected(order("6", "Ala", "999.00", card), "Niepoprawny BLIK"),
            new OrderResult.Rejected(order("7", "Ala", "999.00", card), "Niepoprawny BLIK"),
            new OrderResult.Rejected(order("8", "Ala", "999.00", card), "Pusty numer karty"));
        var report = MiniShop.report(results);
        assertEquals(Map.of("Ala", new BigDecimal("30.30"), "Bartek", new BigDecimal("50.00"),
            "Celina", new BigDecimal("30.30"), "Daniel", new BigDecimal("1.00")), report.acceptedByCustomer());
        assertEquals(Map.of("Niepoprawny BLIK", 2L, "Pusty numer karty", 1L), report.rejectedByReason());
        assertEquals(List.of(new Report.CustomerTotal("Bartek", new BigDecimal("50.00")),
            new Report.CustomerTotal("Ala", new BigDecimal("30.30")),
            new Report.CustomerTotal("Celina", new BigDecimal("30.30"))), report.topCustomers());
    }

    @Test @DisplayName("d: pusty raport ma puste agregaty i top")
    void emptyReport() {
        assertEquals(new Report(Map.of(), Map.of(), List.of()), MiniShop.report(List.of()));
    }

    @Test @DisplayName("d+: gatherer zachowuje kolejność, rozmiar 100 i końcówkę")
    void batches() {
        var orders = IntStream.range(0, 205).mapToObj(i -> order("" + i, "Ala", "1", new Payment.Blik("123456"))).toList();
        var batches = MiniShop.batches(orders);
        assertEquals(List.of(100, 100, 5), batches.stream().map(List::size).toList());
        assertEquals(orders, batches.stream().flatMap(List::stream).toList());
        assertEquals(List.of(), MiniShop.batches(List.of()));
        assertEquals(List.of(100), MiniShop.batches(orders.subList(0, 100)).stream().map(List::size).toList());
    }

    @Test @DisplayName("e: MapStruct tworzy DTO bez danych karty")
    void safeDto() throws Exception {
        var order = order("1", "Ala", "10.00", new Payment.Card("4111111111111111"));
        var dto = ReportMapper.INSTANCE.toSafeOrderDto(order);
        assertEquals(new OrderDto("1", "Ala", new BigDecimal("10.00")), dto);
        var json = new ObjectMapper().valueToTree(dto);
        assertEquals(3, json.size());
        assertFalse(json.has("payment"));
        assertFalse(json.toString().contains("4111111111111111"));
    }

    @Test @DisplayName("e: JSON raportu zawiera agregaty i top 3, bez Payment")
    void exportReport() throws Exception {
        var report = new Report(Map.of("Ala", new BigDecimal("12.30")), Map.of("Niepoprawny BLIK", 2L),
            List.of(new Report.CustomerTotal("Ala", new BigDecimal("12.30"))));
        var file = temp.resolve("raport.json");
        MiniShop.exportReport(report, file);
        assertTrue(Files.isRegularFile(file));
        var json = new ObjectMapper().readTree(file.toFile());
        assertEquals(0, json.get("acceptedByCustomer").get("Ala").decimalValue().compareTo(new BigDecimal("12.30")));
        assertEquals(2L, json.get("rejectedByReason").get("Niepoprawny BLIK").longValue());
        assertEquals("Ala", json.get("topCustomers").get(0).get("customer").asText());
        assertEquals(3, json.size());
        assertFalse(json.toString().contains("payment"));
        assertEquals(new ReportDto(report.acceptedByCustomer(), report.rejectedByReason(), report.topCustomers()),
            ReportMapper.INSTANCE.toDto(report));
    }
}
