package org.example.currencyexchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.exception.model.NBPApiException;
import org.example.currencyexchange.mapper.CurrencyRatesMapper;
import org.example.currencyexchange.model.CurrencyRateResponse;
import org.example.currencyexchange.model.nbp.CurrencyApiResponse;
import org.example.currencyexchange.service.CurrencyRatesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CurrencyRatesServiceImpl implements CurrencyRatesService {

    private final RestClient restClient;
    private final CurrencyRatesMapper mapper;
    @Value("${nbp.api.usd-rates}")
    private String usdRatesURI;

    @Override
    @Retryable(maxAttempts = 4, backoff = @Backoff(delay = 2000, multiplier = 1.7))
    public CurrencyRateResponse fetchRate() {
        var body = restClient.get()
                .uri(usdRatesURI)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new NBPApiException(response.getStatusCode(), "Invalid request to NBP API");
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new NBPApiException(response.getStatusCode(), "NBP API Server Error");
                })
                .body(CurrencyApiResponse.class);
        return mapper.map(body);
    }

}
