package pl.sages.jext.lambdastreams.step5.challenge2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Rozgrzewka, krok 5. Sprawdzenie: test {@code RozgrzewkaShopTest}.
 *
 * Paczka StreamChallange P2: dane sklepu (oryginalne produkty, zamówienia i klienci z Main).
 * nowe: zamówienia mają różne daty (2, 10, 3, 20 i 1 dzień temu), a klienci różne miesiące urodzenia
 * (wiek bez zmian), żeby zadania z tygodniem i z mapą miesięcy miały sens.
 * <p>
 * Uruchomienie: {@code mvn compile exec:exec -Dmain=pl.sages.jext.lambdastreams.step5.challenge2.Main}
 */
public class Main {

    public static Shop sampleShop() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();

        Product product1 = new Product("fasola", 10, 1, Country.POLAND);
        Product product2 = new Product("Coca-Cola", 7, 6, Country.USA);
        Product product3 = new Product("Banan", 20, 4, Country.CHINA);
        Product product4 = new Product("Krem do rąk", 3, 20, Country.JAPAN);
        Product product5 = new Product("Długopis", 10, 2.5, Country.POLAND);

        Order order = new Order(new ArrayList<>(List.of(product1, product2, product4, product3)), now.minusDays(2));
        Order order2 = new Order(new ArrayList<>(List.of(product2, product4, product3)), now.minusDays(10));
        Order order3 = new Order(new ArrayList<>(List.of(product1, product4, product3, product5)), now.minusDays(3));
        Order order4 = new Order(new ArrayList<>(List.of(product1, product4, product3, product3)), now.minusDays(20));
        Order order5 = new Order(new ArrayList<>(List.of(product1, product2, product3, product4, product3)), now.minusDays(1));

        Customer customer = new Customer(new ArrayList<>(List.of(order)), "Jacek", today.minusYears(20).minusMonths(1));
        Customer customer2 = new Customer(new ArrayList<>(List.of(order2, order5, order3)), "Wojtek", today.minusYears(25).minusMonths(3));
        Customer customer3 = new Customer(new ArrayList<>(List.of(order2, order4)), "Krzysiek", today.minusYears(15).minusMonths(1));
        Customer customer4 = new Customer(new ArrayList<>(List.of()), "Kamil", today.minusYears(19).minusMonths(6));
        Customer customer5 = new Customer(new ArrayList<>(List.of(order5)), "Adrian", today.minusYears(40).minusMonths(3));

        return new Shop(new ArrayList<>(List.of(customer, customer2, customer3, customer4, customer5)));
    }

    public static void main(String[] args) {
        Shop shop = sampleShop();
        System.out.println(shop.getCustomerNamesSorted());
        System.out.println(shop.getAverageAge());
        System.out.println(shop.findTheMostSpendingCustomer());
        System.out.println(shop.countCustomersBornInMonth());
        System.out.println(shop.countProductsByCountry());
    }
}
