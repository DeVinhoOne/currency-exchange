package org.example.currencyexchange.model.dto;

import org.example.currencyexchange.model.CurrencyRequest;

import java.math.BigDecimal;

public record CurrencyExchangeRequest(
        Long accountId,
        CurrencyRequest from,
        CurrencyRequest to,
        BigDecimal amount
) {
}
