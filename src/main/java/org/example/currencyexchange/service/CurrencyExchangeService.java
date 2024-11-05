package org.example.currencyexchange.service;

import org.example.currencyexchange.model.dto.CurrencyExchangeRequest;
import org.example.currencyexchange.model.dto.CurrencyExchangeResponse;

public interface CurrencyExchangeService {

    CurrencyExchangeResponse exchange(final CurrencyExchangeRequest request);

}
