package org.example.currencyexchange.model.dto;

import java.math.BigDecimal;
import java.util.Currency;

public record BalanceData(
        Currency currency,
        BigDecimal amount
) {
}
