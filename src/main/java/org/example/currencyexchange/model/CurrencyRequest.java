package org.example.currencyexchange.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Currency;

@Getter
public class CurrencyRequest {

    private final String currencyCode;
    @JsonIgnore
    private final Currency currency;

    @JsonCreator
    public CurrencyRequest(String currencyCode) {
        if (!"PLN".equals(currencyCode) && !"USD".equals(currencyCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only PLN or USD currencies are supported.");
        }
        this.currencyCode = currencyCode.toUpperCase();
        this.currency = Currency.getInstance(this.currencyCode);
    }

}
