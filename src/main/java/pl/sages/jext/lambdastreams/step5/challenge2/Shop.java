package pl.sages.jext.lambdastreams.step5.challenge2;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Paczka StreamChallange P2: sklep i polecenia. Dane: {@link Main#sampleShop()}. Test: {@code ShopTest}.
 * <p>
 * Polecenia z oryginału są nad metodami. nowe: każda metoda ma już nazwę i typ zwracany (żeby test się
 * kompilował); w nawiasach doprecyzowanie, jak liczy rozwiązanie (tak jak w grupie 120).
 * Ilość produktu to pole amount, cena pozycji to getFullPrice() = amount * price.
 * Zamówienia liczymy u każdego klienta osobno (order2 i order5 mają po dwóch klientów).
 */
public class Shop {
    private final List<Customer> customers;

    public Shop(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    //zwróć imiona customerów posortowane alfabetycznie
    public List<String> getCustomerNamesSorted() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //zwróć średni wiek customerów
    public double getAverageAge() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //zwróć customerów którzy mają jakieś zamówienia
    public List<Customer> findCustomersWithOrders() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //zwróć tylko pełnoletnich customerów posortowanych po ich wieku
    public List<Customer> findAdultCustomersSortedByAge() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //zwróć zamówienia nie starsze niż tydzień
    public List<Order> findOrdersNotOlderThanWeek() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //zwróć średnią ilość produktów w zamówieniach (średnia z amount wszystkich zamówionych produktów)
    public double getAverageProductAmount() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //zwróć średnią cenę produktów ze wszystkich zamówień (średnia z getFullPrice())
    public double getAverageProductPrice() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //zwróć customera, który wydał najwięcej (suma getFullPrice() produktów z jego zamówień)
    public Customer findTheMostSpendingCustomer() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //zwróć produkt którego mamy najmniej (wg. amount)
    public Product findProductWithLeastAmount() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //zwróć wszystkie produkty zamówione później niż tydzień temu
    public List<Product> findProductsOrderedWithinLastWeek() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //trudne:
    //zwróć mapę której kluczem będzie numer miesiąca a wartością ilość customerów urodzonych w danym miesiącu
    public Map<Integer, Long> countCustomersBornInMonth() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    //zwróć mapę zawierającą kraj oraz ilość produktów pochodzących z tego kraju (suma amount)
    public Map<Country, Integer> countProductsByCountry() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    // pomocnicza metoda z grupy 120: wszystkie produkty ze wszystkich zamówień wszystkich klientów
    private Stream<Product> getAllOrderedProducts() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
