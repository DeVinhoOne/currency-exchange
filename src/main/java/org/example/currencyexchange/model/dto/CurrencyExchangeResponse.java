package org.example.currencyexchange.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@Getter
@Setter
public class CurrencyExchangeResponse {
    private BigDecimal rate;
    private Currency from;
    private Currency to;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private List<BalanceData> updatedBalances;
}
