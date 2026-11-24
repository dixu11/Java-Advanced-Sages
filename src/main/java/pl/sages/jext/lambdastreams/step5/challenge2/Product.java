package pl.sages.jext.lambdastreams.step5.challenge2;

/** Paczka StreamChallange P2: produkt. amount to ilość sztuk, price to cena za sztukę. */
public class Product {
    private final String name;
    private final int amount;
    private final double price;
    private final Country country;

    public Product(String name, int amount, double price, Country country) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.country = country;
    }

    // nowe: gettery (w oryginale P2 ich nie było, kursant dopisywał je sam)
    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public Country getCountry() {
        return country;
    }

    // z grupy 120: cena pozycji = ilość * cena
    public double getFullPrice() {
        return amount * price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", country=" + country +
                '}';
    }
}
