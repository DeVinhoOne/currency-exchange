package org.example.currencyexchange.exchange;

import org.example.currencyexchange.model.CurrencyRequest;
import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.model.dto.CurrencyExchangeRequest;
import org.example.currencyexchange.service.AccountService;
import org.example.currencyexchange.service.CurrencyExchangeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CurrencyExchangeTests {

    @Autowired
    private CurrencyExchangeService service;
    @Autowired
    private AccountService accountService;

    @Test
    void shouldConvertPLNtoUSD() {
        //Given
        createTestAccount();
        var request = new CurrencyExchangeRequest(
                1L,
                new CurrencyRequest("PLN"),
                new CurrencyRequest("USD"),
                new BigDecimal("650.99")
        );

        //When
        var response = service.exchange(request);

        //Then
        assertEquals(Currency.getInstance("PLN"), response.from());
        assertEquals(Currency.getInstance("USD"), response.to());
        assertNotNull(response.rate());
        assertNotNull(response.timestamp());
    }

    @Test
    void shouldConvertUSDtoPLN() {
        //Given
        createTestAccount();
        var requestToUSD = new CurrencyExchangeRequest(
                1L,
                new CurrencyRequest("PLN"),
                new CurrencyRequest("USD"),
                new BigDecimal("650.99")
        );
        service.exchange(requestToUSD);

        var requestToPLN = new CurrencyExchangeRequest(
                1L,
                new CurrencyRequest("USD"),
                new CurrencyRequest("PLN"),
                new BigDecimal("101.51")
        );

        //When
        var response = service.exchange(requestToPLN);

        //Then
        assertEquals(Currency.getInstance("USD"), response.from());
        assertEquals(Currency.getInstance("PLN"), response.to());
        assertNotNull(response.rate());
        assertNotNull(response.timestamp());
    }

    private void createTestAccount() {
        var request = new AccountCreateRequest("Tomasz", "Nowak", new BigDecimal("2000.00"));
        accountService.createAccount(request);
    }

}
