package pl.sages.jext.ch8.project;

import java.math.BigDecimal;

/** Bezpieczny DTO do etapu e: celowo nie ma Payment, numeru karty ani kodu BLIK. */
public record OrderDto(String id, String customer, BigDecimal amount) {}
