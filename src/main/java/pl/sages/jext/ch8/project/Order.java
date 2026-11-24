package pl.sages.jext.ch8.project;

import java.math.BigDecimal;

/** Gotowy rekord zamówienia. Kwota w PLN; wszystkie dane w pliku są fikcyjne. */
public record Order(String id, String customer, BigDecimal amount, Payment payment) {}
