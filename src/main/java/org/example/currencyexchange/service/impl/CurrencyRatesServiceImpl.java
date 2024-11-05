package org.example.currencyexchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.mapper.CurrencyRatesMapper;
import org.example.currencyexchange.model.CurrencyRateResponse;
import org.example.currencyexchange.model.nbp.CurrencyApiResponse;
import org.example.currencyexchange.service.CurrencyRatesService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CurrencyRatesServiceImpl implements CurrencyRatesService {

    private final RestClient restClient;
    private final CurrencyRatesMapper mapper;

    @Override
    public CurrencyRateResponse fetchRate() {
        var body = restClient.get()
                .uri("/api/exchangerates/rates/c/usd/?format=json")
                .retrieve()
                .body(CurrencyApiResponse.class);
        return mapper.map(body);
    }

}
