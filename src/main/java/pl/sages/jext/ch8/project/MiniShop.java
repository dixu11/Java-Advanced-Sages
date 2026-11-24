package pl.sages.jext.ch8.project;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;


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
    }

    /**
     * b, 15 min. Najpierw odrzuć kwotę null, zero i ujemną. Następnie switch:
     * Blik: dokładnie 6 cyfr ASCII; Card: numer niepusty; Transfer: limit 1000 włącznie.
     * Powody muszą być dokładnie takie jak w testach. Użyj wzorców rekordów i when.
     */
    public static OrderResult validate(Order order) {
        // TODO
        throw new UnsupportedOperationException("TODO");
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
    }

    /** d, 15 min. Suma zaakceptowanych kwot na klienta, odmowy według powodu, top 3. */
    public static Report report(List<OrderResult> results) {
        // TODO: przy remisie w top 3 sortuj klientów alfabetycznie.
        throw new UnsupportedOperationException("TODO");
    }

    /** d+, 10 min, dla chętnych. Partie po 100, bez pustej ostatniej partii. */
    public static List<List<Order>> batches(List<Order> orders) {
        // TODO: Gatherers.windowFixed.
        throw new UnsupportedOperationException("TODO");
    }

    /** e, 20 min wraz z ReportMapper, dla chętnych. MapStruct -> DTO -> JSON do pliku. */
    public static void exportReport(Report report, Path destination) throws IOException {
        // TODO: użyj ReportMapper.INSTANCE i zapisz czytelny JSON.
        throw new UnsupportedOperationException("TODO");
    }
}
