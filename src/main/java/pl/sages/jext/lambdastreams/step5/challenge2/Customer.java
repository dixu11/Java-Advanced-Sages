package pl.sages.jext.lambdastreams.step5.challenge2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/** Paczka StreamChallange P2: klient z zamówieniami. */
public class Customer {
    private final List<Order> orders;
    private final String name;
    private final LocalDate birthday;

    public Customer(List<Order> orders, String name, LocalDate birthday) {
        this.orders = orders;
        this.name = name;
        this.birthday = birthday;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public int getAge() {
        return (int) birthday.until(LocalDate.now(), ChronoUnit.YEARS);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", orders=" + orders.size() +
                '}';
    }
}
