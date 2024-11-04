package org.example.currencyexchange.currencyrate;

import org.example.currencyexchange.service.CurrencyRatesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CurrencyRateTests {

    @Autowired
    private CurrencyRatesService service;

    @Test
    void shouldFetchUSDRate() {
        //When
        var response = service.fetchRate("USD");

        //Then
        Assertions.assertNotNull(response.buy());
        Assertions.assertNotNull(response.sell());
        Assertions.assertNotNull(response.currencyCode());
        Assertions.assertNotNull(response.date());
    }
}
