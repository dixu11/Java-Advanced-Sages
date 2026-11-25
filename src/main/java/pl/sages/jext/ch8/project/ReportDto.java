package pl.sages.jext.ch8.project;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Ćwiczenie 13. Sprawdzenie: test {@code Zad13MiniShopTest}.
 *
 * DTO do eksportu: agregaty i top 3, bez źródłowych zamówień.
 */
public record ReportDto(Map<String, BigDecimal> acceptedByCustomer,
                        Map<String, Long> rejectedByReason, List<Report.CustomerTotal> topCustomers) {}
