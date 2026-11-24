package pl.sages.jext.ch8.project;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/** Wynik etapu d: same agregaty, bez danych płatności. */
public record Report(Map<String, BigDecimal> acceptedByCustomer,
                     Map<String, Long> rejectedByReason, List<CustomerTotal> topCustomers) {
    public record CustomerTotal(String customer, BigDecimal total) {}
}
