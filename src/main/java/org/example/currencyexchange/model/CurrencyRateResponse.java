package org.example.currencyexchange.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CurrencyRateResponse {

    private BigDecimal sell;
    private BigDecimal buy;
    private String currencyCode;
    private LocalDate date;

}
