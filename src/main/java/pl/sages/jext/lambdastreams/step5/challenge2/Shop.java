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
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return customers.stream()
        //         .map(customer -> customer.getName())
        //         .sorted()
        //         .toList();
        // KONIEC ROZWIĄZANIA
    }

    //zwróć średni wiek customerów
    public double getAverageAge() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return customers.stream()
        //         .mapToInt(customer -> customer.getAge())
        //         .average()
        //         .orElse(-1);
        // KONIEC ROZWIĄZANIA
    }

    //zwróć customerów którzy mają jakieś zamówienia
    public List<Customer> findCustomersWithOrders() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return customers.stream()
        //         .filter(customer -> !customer.getOrders().isEmpty())
        //         .toList();
        // KONIEC ROZWIĄZANIA
    }

    //zwróć tylko pełnoletnich customerów posortowanych po ich wieku
    public List<Customer> findAdultCustomersSortedByAge() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return customers.stream()
        //         .filter(customer -> customer.getAge() >= 18)
        //         .sorted(Comparator.comparingInt(Customer::getAge))
        //         .toList();
        // KONIEC ROZWIĄZANIA
    }

    //zwróć zamówienia nie starsze niż tydzień
    public List<Order> findOrdersNotOlderThanWeek() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);
        // return customers.stream()
        //         .flatMap(customer -> customer.getOrders().stream())
        //         .filter(order -> order.getOrderTime().isAfter(weekAgo))
        //         .toList();
        // KONIEC ROZWIĄZANIA
    }

    //zwróć średnią ilość produktów w zamówieniach (średnia z amount wszystkich zamówionych produktów)
    public double getAverageProductAmount() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return getAllOrderedProducts()
        //         .mapToInt(product -> product.getAmount())
        //         .average()
        //         .orElse(0);
        // KONIEC ROZWIĄZANIA
    }

    //zwróć średnią cenę produktów ze wszystkich zamówień (średnia z getFullPrice())
    public double getAverageProductPrice() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return getAllOrderedProducts()
        //         .mapToDouble(product -> product.getFullPrice())
        //         .average()
        //         .orElse(0);
        // KONIEC ROZWIĄZANIA
    }

    //zwróć customera, który wydał najwięcej (suma getFullPrice() produktów z jego zamówień)
    public Customer findTheMostSpendingCustomer() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return customers.stream()
        //         .max(Comparator.comparingDouble(customer -> customer.getOrders().stream()
        //                 .flatMap(order -> order.getProducts().stream())
        //                 .mapToDouble(Product::getFullPrice)
        //                 .sum()))
        //         .orElseThrow();
        // KONIEC ROZWIĄZANIA
    }

    //zwróć produkt którego mamy najmniej (wg. amount)
    public Product findProductWithLeastAmount() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return getAllOrderedProducts()
        //         .min(Comparator.comparingInt(Product::getAmount))
        //         .orElseThrow();
        // KONIEC ROZWIĄZANIA
    }

    //zwróć wszystkie produkty zamówione później niż tydzień temu
    public List<Product> findProductsOrderedWithinLastWeek() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return findOrdersNotOlderThanWeek().stream()
        //         .flatMap(order -> order.getProducts().stream())
        //         .toList();
        // KONIEC ROZWIĄZANIA
    }

    //trudne:
    //zwróć mapę której kluczem będzie numer miesiąca a wartością ilość customerów urodzonych w danym miesiącu
    public Map<Integer, Long> countCustomersBornInMonth() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return customers.stream()
        //         .collect(Collectors.groupingBy(customer -> customer.getBirthday().getMonthValue(),
        //                 Collectors.counting()));
        // // grupa 120 robiła to przez IntStream.range(1, 13).boxed().collect(toMap(...)): działa, ale przechodzi
        // // klientów 12 razy; groupingBy robi to w jednym przejściu
        // KONIEC ROZWIĄZANIA
    }

    //zwróć mapę zawierającą kraj oraz ilość produktów pochodzących z tego kraju (suma amount)
    public Map<Country, Integer> countProductsByCountry() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return getAllOrderedProducts()
        //         .collect(Collectors.groupingBy(Product::getCountry, Collectors.summingInt(Product::getAmount)));
        // KONIEC ROZWIĄZANIA
    }

    // pomocnicza metoda z grupy 120: wszystkie produkty ze wszystkich zamówień wszystkich klientów
    private Stream<Product> getAllOrderedProducts() {
        // TODO
        throw new UnsupportedOperationException("TODO");
        // ROZWIĄZANIE (odkomentuj i usuń throw):
        // return customers.stream()
        //         .flatMap(customer -> customer.getOrders().stream())
        //         .flatMap(order -> order.getProducts().stream());
        // KONIEC ROZWIĄZANIA
    }
}
