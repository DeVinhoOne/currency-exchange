package org.example.currencyexchange.currencyrate;

import org.example.currencyexchange.service.CurrencyRatesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CurrencyRateTests {

    @Autowired
    private CurrencyRatesService service;

    @Test
    void shouldFetchUSDRate() {
        //When
        var response = service.fetchRate();

        //Then
        Assertions.assertNotNull(response.getBuy());
        Assertions.assertNotNull(response.getSell());
        Assertions.assertNotNull(response.getCurrencyCode());
        Assertions.assertNotNull(response.getDate());
    }

}
