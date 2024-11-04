package org.example.currencyexchange.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CurrencyRateResponse(
    BigDecimal sell,
    BigDecimal buy,
    String currencyCode,
    LocalDate date
) {
}
