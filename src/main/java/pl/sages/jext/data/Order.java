package pl.sages.jext.data;

import java.time.LocalDate;

/**
 * Zamówienie z pliku src/main/resources/orders.csv (wspólny zestaw danych do ćwiczeń 5, 7 i 8).
 */
public record Order(long id, String customer, String city, String category,
                    double amount, LocalDate date, Status status) {

    public enum Status { NEW, PAID, SHIPPED, CANCELLED }

    /** Zamienia jedną linię CSV (separator ";") na rekord. */
    public static Order fromCsv(String line) {
        String[] p = line.split(";");
        return new Order(
                Long.parseLong(p[0]), p[1], p[2], p[3],
                Double.parseDouble(p[4]), LocalDate.parse(p[5]), Status.valueOf(p[6]));
    }
}
