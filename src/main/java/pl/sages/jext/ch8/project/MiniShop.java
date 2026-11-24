package pl.sages.jext.ch8.project;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;

// ROZWIĄZANIE (odkomentuj i usuń throw):
// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import java.util.Comparator;
// import java.util.concurrent.Executors;
// import java.util.stream.Collectors;
// import java.util.stream.Gatherers;
// KONIEC ROZWIĄZANIA

/**
 * Ćwiczenie 13: Mini-sklep: od JSON-a do raportu. Etapy a-d: 60 minut,
 * partie d+: kolejne 10, DTO e: kolejne 20. Modele i bramka są gotowe.
 * Rozwiązuj po kolei, sprawdzając metody MiniShopTest wskazane w README.
 */
public final class MiniShop {
    public static final BigDecimal TRANSFER_LIMIT = new BigDecimal("1000.00");

    /** a, 10 min. Wczytaj /mini-shop-orders.json z classpath, zamknij strumień. */
    public static List<Order> loadOrders() throws IOException {
        // TODO: Jackson + TypeReference<List<Order>>.
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // try (var input = MiniShop.class.getResourceAsStream("/mini-shop-orders.json")) {
        //     if (input == null) throw new IOException("Brak zasobu mini-shop-orders.json");
        //     return new ObjectMapper().readValue(input, new TypeReference<List<Order>>() {});
        // }
        // KONIEC ROZWIĄZANIA
    }

    /**
     * b, 15 min. Najpierw odrzuć kwotę null, zero i ujemną. Następnie switch:
     * Blik: dokładnie 6 cyfr ASCII; Card: numer niepusty; Transfer: limit 1000 włącznie.
     * Powody muszą być dokładnie takie jak w testach. Użyj wzorców rekordów i when.
     */
    public static OrderResult validate(Order order) {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // if (order.amount() == null || order.amount().signum() <= 0)
        //     return new OrderResult.Rejected(order, "Niepoprawna kwota");
        // return switch (order.payment()) {
        //     case null -> new OrderResult.Rejected(order, "Brak płatności");
        //     case Payment.Blik(var code) when code == null || !code.matches("[0-9]{6}") ->
        //         new OrderResult.Rejected(order, "Niepoprawny BLIK");
        //     case Payment.Card(var number) when number == null || number.isBlank() ->
        //         new OrderResult.Rejected(order, "Pusty numer karty");
        //     case Payment.Transfer _ when order.amount().compareTo(TRANSFER_LIMIT) > 0 ->
        //         new OrderResult.Rejected(order, "Przekroczony limit przelewu");
        //     case Payment.Blik _, Payment.Card _, Payment.Transfer _ -> new OrderResult.Accepted(order);
        // };
        // KONIEC ROZWIĄZANIA
    }

    /**
     * c, 20 min. Jeden Callable na zamówienie. virtual=true: wątki wirtualne,
     * false: pula 10 wątków. Zbierz Future w kolejności wejścia, poczekaj na wyniki.
     * Bramka tylko dla Accepted; acquire przed pay, release w finally po pay.
     * Nie połykaj przerwań ani błędów Future. Zamknij executor try-with-resources.
     */
    public static List<OrderResult> process(List<Order> orders, boolean virtual, PaymentGateway gateway)
            throws InterruptedException, ExecutionException {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // try (var executor = virtual ? Executors.newVirtualThreadPerTaskExecutor() : Executors.newFixedThreadPool(10)) {
        //     var futures = orders.stream().map(order -> executor.submit(() -> {
        //         var result = validate(order);
        //         if (result instanceof OrderResult.Accepted) {
        //             gateway.permits().acquire();
        //             try {
        //                 gateway.pay(order);
        //             } finally {
        //                 gateway.permits().release();
        //             }
        //         }
        //         return result;
        //     })).toList();
        //     var results = new java.util.ArrayList<OrderResult>(orders.size());
        //     for (var future : futures) results.add(future.get());
        //     return List.copyOf(results);
        // }
        // KONIEC ROZWIĄZANIA
    }

    /** d, 15 min. Suma zaakceptowanych kwot na klienta, odmowy według powodu, top 3. */
    public static Report report(List<OrderResult> results) {
        // TODO: przy remisie w top 3 sortuj klientów alfabetycznie.
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // var totals = results.stream()
        //     .filter(r -> r instanceof OrderResult.Accepted)
        //     .map(r -> ((OrderResult.Accepted) r).order())
        //     .collect(Collectors.groupingBy(Order::customer,
        //         Collectors.reducing(BigDecimal.ZERO, Order::amount, BigDecimal::add)));
        // var rejections = results.stream()
        //     .filter(r -> r instanceof OrderResult.Rejected)
        //     .map(r -> (OrderResult.Rejected) r)
        //     .collect(Collectors.groupingBy(OrderResult.Rejected::reason, Collectors.counting()));
        // var top = totals.entrySet().stream()
        //     .map(e -> new Report.CustomerTotal(e.getKey(), e.getValue()))
        //     .sorted(Comparator.comparing(Report.CustomerTotal::total).reversed()
        //         .thenComparing(Report.CustomerTotal::customer))
        //     .limit(3).toList();
        // return new Report(totals, rejections, top);
        // KONIEC ROZWIĄZANIA
    }

    /** d+, 10 min, dla chętnych. Partie po 100, bez pustej ostatniej partii. */
    public static List<List<Order>> batches(List<Order> orders) {
        // TODO: Gatherers.windowFixed.
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return orders.stream().gather(Gatherers.windowFixed(100)).toList();
        // KONIEC ROZWIĄZANIA
    }

    /** e, 20 min wraz z ReportMapper, dla chętnych. MapStruct -> DTO -> JSON do pliku. */
    public static void exportReport(Report report, Path destination) throws IOException {
        // TODO: użyj ReportMapper.INSTANCE i zapisz czytelny JSON.
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // var dto = ReportMapper.INSTANCE.toDto(report);
        // new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(destination.toFile(), dto);
        // KONIEC ROZWIĄZANIA
    }
}
