package org.example.currencyexchange.model.dto;

import java.math.BigDecimal;

public record AccountCreateRequest(
        String firstName,
        String lastName,
        BigDecimal initBalancePLN
) {
}
