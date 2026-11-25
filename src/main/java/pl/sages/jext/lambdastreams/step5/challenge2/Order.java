package pl.sages.jext.lambdastreams.step5.challenge2;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Rozgrzewka, krok 5. Sprawdzenie: test {@code RozgrzewkaShopTest}.
 *
 * Paczka StreamChallange P2: zamówienie z listą produktów i czasem złożenia.
 */
public class Order {
    private final List<Product> products;
    private final LocalDateTime orderTime;

    public Order(List<Product> products) {
        this(products, LocalDateTime.now());
    }

    /** nowe: czas zamówienia podany z zewnątrz, żeby „nie starsze niż tydzień” dało się sprawdzić testem */
    public Order(List<Product> products, LocalDateTime orderTime) {
        this.products = products;
        this.orderTime = orderTime;
    }

    // nowe: gettery (w oryginale P2 ich nie było)
    public List<Product> getProducts() {
        return products;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "products=" + products +
                ", orderTime=" + orderTime +
                '}';
    }
}
