package pl.sages.jext.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/** Gotowy wczytywacz danych z classpath (nie jest ćwiczeniem). */
public final class Orders {

    private Orders() {
    }

    public static List<Order> all() {
        try (var in = Orders.class.getResourceAsStream("/orders.csv");
             var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return reader.lines().skip(1).map(Order::fromCsv).toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
