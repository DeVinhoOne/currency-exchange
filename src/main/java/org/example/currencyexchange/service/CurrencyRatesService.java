package org.example.currencyexchange.service;

import org.example.currencyexchange.model.CurrencyRateResponse;

public interface CurrencyRatesService {

    CurrencyRateResponse fetchRate(String currencyCode);

}
