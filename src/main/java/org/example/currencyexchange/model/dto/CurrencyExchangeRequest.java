package org.example.currencyexchange.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.example.currencyexchange.model.CurrencyRequest;

import java.math.BigDecimal;

public record CurrencyExchangeRequest(
        @NotNull(message = "accountId is required")
        Long accountId,
        @NotNull(message = "'from' is required")
        CurrencyRequest from,
        @NotNull(message = "'to' is required")
        CurrencyRequest to,
        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        BigDecimal amount
) {
}
