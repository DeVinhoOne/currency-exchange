package org.example.currencyexchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.model.CurrencyRateResponse;
import org.example.currencyexchange.service.CurrencyRatesService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CurrencyRatesServiceImpl implements CurrencyRatesService {

    private final RestClient restClient;

    @Override
    public CurrencyRateResponse fetchRate(String currencyCode) {
        String body = restClient.get()
                .uri("/api/exchangerates/rates/c/{currencyCode}/?format=json", currencyCode)
                .retrieve()
                .body(String.class);
        return null;
    }

}
