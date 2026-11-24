package pl.sages.jext.ch3.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.sages.jext.data.Order;
import pl.sages.jext.data.Orders;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Ćw. 5: Predicate i Function")
class FunctionsTest {

    private final List<Order> orders = Orders.all();
    private final Order first = orders.getFirst(); // 1, Anna Kowalska, Warszawa, 120.50, SHIPPED

    @Test
    @DisplayName("a-c) proste predykaty")
    void predicates() {
        assertTrue(Functions.moreExpensiveThan(100).test(first));
        assertFalse(Functions.moreExpensiveThan(120.50).test(first));
        assertTrue(Functions.fromCity("Warszawa").test(first));
        assertFalse(Functions.fromCity("Kraków").test(first));
        assertFalse(Functions.cancelled().test(first));
        assertEquals(3, orders.stream().filter(Functions.cancelled()).count());
    }

    @Test
    @DisplayName("d) połączone predykaty")
    void good() {
        List<Long> id = orders.stream()
                .filter(Functions.goodOrders(500, "Warszawa"))
                .map(Order::id)
                .toList();
        assertEquals(List.of(16L, 23L, 24L), id, "zamówienie 4 jest anulowane, więc go nie ma");
    }

    @Test
    @DisplayName("e) funkcja wyższego rzędu report")
    void report() {
        List<String> result = Functions.report(orders, Functions.moreExpensiveThan(3000), o -> o.id() + ":" + o.customer());
        assertEquals(List.of("9:Ola Kamińska", "16:Anna Kowalska"), result);
    }

    @Test
    @DisplayName("f) złożenie funkcji andThen")
    void composition() {
        assertEquals("ANNA KOWALSKA", Functions.customerInUpperCase().apply(first));
    }

    @Test
    @DisplayName("g) dla chętnych: memoizacja")
    void memo() {
        AtomicInteger calls = new AtomicInteger();
        Function<Integer, Integer> square = Functions.memoize(x -> {
            calls.incrementAndGet();
            return x * x;
        });
        assertEquals(16, square.apply(4));
        assertEquals(16, square.apply(4));
        assertEquals(9, square.apply(3));
        assertEquals(2, calls.get(), "dla 4 funkcja miała się wykonać tylko raz");
    }
}
