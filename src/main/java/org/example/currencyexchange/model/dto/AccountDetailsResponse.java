package org.example.currencyexchange.model.dto;

import java.util.List;

public record AccountDetailsResponse(
        String firstName,
        String lastName,
        List<BalanceData> balances
) {
}
