package org.example.currencyexchange.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Currency;

@Getter
public class CurrencyRequest {

    private final String currencyCode;
    @JsonIgnore
    private final Currency currency;

    @JsonCreator
    public CurrencyRequest(String currencyCode) {
        this.currencyCode = currencyCode.toUpperCase();
        this.currency = Currency.getInstance(this.currencyCode);
    }

}
