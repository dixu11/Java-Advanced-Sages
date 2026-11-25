package pl.sages.jext.lambdastreams.step5.challenge2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Rozgrzewka, krok 5 (slajdy 85-139): lambdy i strumienie.
 * Zadanie NIE jest tutaj: zacznij od klasy {@link pl.sages.jext.lambdastreams.step5.challenge2.Shop}
 * (src/main/java/pl/sages/jext/lambdastreams/step5/challenge2/Shop.java).
 * Ten plik tylko sprawdza Twój wynik: uruchom go zieloną strzałką po rozwiązaniu.
 * Kolejność pracy:
 * <ol>
 * <li>{@code pl.sages.jext.lambdastreams.step5.challenge2.Shop} (src/main/java/pl/sages/jext/lambdastreams/step5/challenge2/Shop.java)</li>
 * <li>{@code pl.sages.jext.lambdastreams.step5.challenge2.Main} (src/main/java/pl/sages/jext/lambdastreams/step5/challenge2/Main.java)</li>
 * <li>{@code pl.sages.jext.lambdastreams.step5.challenge2.Customer} (src/main/java/pl/sages/jext/lambdastreams/step5/challenge2/Customer.java)</li>
 * <li>{@code pl.sages.jext.lambdastreams.step5.challenge2.Order} (src/main/java/pl/sages/jext/lambdastreams/step5/challenge2/Order.java)</li>
 * <li>{@code pl.sages.jext.lambdastreams.step5.challenge2.Product} (src/main/java/pl/sages/jext/lambdastreams/step5/challenge2/Product.java)</li>
 * <li>{@code pl.sages.jext.lambdastreams.step5.challenge2.Country} (src/main/java/pl/sages/jext/lambdastreams/step5/challenge2/Country.java)</li>
 * </ol>
 */
@DisplayName("Lambdy i strumienie, krok 5: StreamChallange P2 (sklep)")
class RozgrzewkaShopTest {

    private final Shop shop = Main.sampleShop();

    private static List<String> names(List<Customer> customers) {
        return customers.stream().map(Customer::getName).toList();
    }

    @Test
    @DisplayName("imiona customerów alfabetycznie")
    void names() {
        assertEquals(List.of("Adrian", "Jacek", "Kamil", "Krzysiek", "Wojtek"), shop.getCustomerNamesSorted());
    }

    @Test
    @DisplayName("średni wiek customerów")
    void averageAge() {
        assertEquals(23.8, shop.getAverageAge(), 1e-9, "(20 + 25 + 15 + 19 + 40) / 5");
    }

    @Test
    @DisplayName("customerzy z zamówieniami")
    void withOrders() {
        assertEquals(List.of("Jacek", "Wojtek", "Krzysiek", "Adrian"), names(shop.findCustomersWithOrders()),
                "Kamil nie ma zamówień");
    }

    @Test
    @DisplayName("pełnoletni customerzy posortowani po wieku")
    void adults() {
        assertEquals(List.of("Kamil", "Jacek", "Wojtek", "Adrian"), names(shop.findAdultCustomersSortedByAge()),
                "Krzysiek ma 15 lat; 18 lat to już pełnoletność");
    }

    @Test
    @DisplayName("zamówienia nie starsze niż tydzień")
    void recentOrders() {
        List<Order> orders = shop.findOrdersNotOlderThanWeek();
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);
        assertTrue(!orders.isEmpty() && orders.stream().allMatch(o -> o.getOrderTime().isAfter(weekAgo)),
                "mają zostać tylko zamówienia z ostatnich 7 dni");
        assertEquals(Set.of(1, 2, 3), orders.stream().map(o -> (int) o.getOrderTime().toLocalDate()
                        .until(LocalDate.now()).getDays()).collect(Collectors.toSet()),
                "zamówienia sprzed 1, 2 i 3 dni (te sprzed 10 i 20 dni odpadają)");
    }

    @Test
    @DisplayName("średnia ilość produktów (amount) w zamówieniach")
    void averageAmount() {
        assertEquals(316.0 / 28, shop.getAverageProductAmount(), 1e-9,
                "28 produktów ze wszystkich zamówień wszystkich klientów, średnia z amount");
    }

    @Test
    @DisplayName("średnia cena produktów (getFullPrice) ze wszystkich zamówień")
    void averagePrice() {
        assertEquals(53.75, shop.getAverageProductPrice(), 1e-9, "średnia z getFullPrice() 28 produktów");
    }

    @Test
    @DisplayName("customer, który wydał najwięcej")
    void mostSpending() {
        assertEquals("Wojtek", shop.findTheMostSpendingCustomer().getName(), "Wojtek ma 3 zamówienia, razem 629 zł");
    }

    @Test
    @DisplayName("produkt, którego mamy najmniej (wg amount)")
    void leastAmount() {
        assertEquals("Krem do rąk", shop.findProductWithLeastAmount().getName());
    }

    @Test
    @DisplayName("produkty zamówione w ostatnim tygodniu")
    void recentProducts() {
        List<Product> products = shop.findProductsOrderedWithinLastWeek();
        assertEquals(18, products.size(),
                "zamówienia 1, 3 i 5 dni wstecz u każdego klienta: 4 + 5 + 4 + 5 (order5 mają Wojtek i Adrian)");
        assertEquals(Set.of("fasola", "Coca-Cola", "Banan", "Krem do rąk", "Długopis"),
                products.stream().map(Product::getName).collect(Collectors.toSet()));
    }

    @Test
    @DisplayName("trudne: mapa miesiąc urodzenia → liczba customerów")
    void bornInMonth() {
        LocalDate today = LocalDate.now();
        Map<Integer, Long> expected = Map.of(
                today.minusMonths(1).getMonthValue(), 2L,
                today.minusMonths(3).getMonthValue(), 2L,
                today.minusMonths(6).getMonthValue(), 1L);
        Map<Integer, Long> actual = shop.countCustomersBornInMonth();
        IntStream.rangeClosed(1, 12).forEach(month -> assertEquals(
                expected.getOrDefault(month, 0L), actual.getOrDefault(month, 0L), "miesiąc " + month));
        assertTrue(actual.keySet().stream().allMatch(m -> m >= 1 && m <= 12), "klucze to numery miesięcy 1-12");
    }

    @Test
    @DisplayName("trudne: mapa kraj → ilość produktów (suma amount)")
    void byCountry() {
        assertEquals(Map.of(Country.POLAND, 60, Country.USA, 35, Country.CHINA, 200, Country.JAPAN, 21),
                shop.countProductsByCountry(), "flatMap do produktów, potem groupingBy z summingInt(amount)");
    }
}
