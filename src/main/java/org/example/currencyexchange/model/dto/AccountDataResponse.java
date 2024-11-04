package org.example.currencyexchange.model.dto;

import java.util.List;

public record AccountDataResponse(
        String firstName,
        String lastName,
        List<BalanceData> balances
) {
}
