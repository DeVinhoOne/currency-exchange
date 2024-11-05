package org.example.currencyexchange.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record CurrencyExchangeResponse(
        BigDecimal rate,
        Currency from,
        Currency to,
        BigDecimal amount,
        LocalDateTime timestamp
) {
}
