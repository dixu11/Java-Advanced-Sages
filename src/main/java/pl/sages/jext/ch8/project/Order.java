package pl.sages.jext.ch8.project;

import java.math.BigDecimal;

/**
 * Ćwiczenie 13. Sprawdzenie: test {@code Zad13MiniShopTest}.
 *
 * Gotowy rekord zamówienia. Kwota w PLN; wszystkie dane w pliku są fikcyjne.
 */
public record Order(String id, String customer, BigDecimal amount, Payment payment) {}
