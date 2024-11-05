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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CurrencyExchangeTests {

    @Autowired
    private CurrencyExchangeService exchangeService;
    @Autowired
    private AccountService accountService;

    @Test
    void shouldConvertFromPLNtoUSD() {
        //Given
        var accountId = createTestAccount();
        var request = new CurrencyExchangeRequest(
                accountId,
                new CurrencyRequest("PLN"),
                new CurrencyRequest("USD"),
                new BigDecimal("650.99")
        );

        //When
        var response = exchangeService.exchange(request);

        //Then
        assertEquals(Currency.getInstance("PLN"), response.getFrom());
        assertEquals(Currency.getInstance("USD"), response.getTo());
        assertNotNull(response.getRate());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void shouldConvertFromUSDtoPLN() {
        //Given
        var accountId = createTestAccount();
        var requestToUSD = new CurrencyExchangeRequest(
                accountId,
                new CurrencyRequest("PLN"),
                new CurrencyRequest("USD"),
                new BigDecimal("650.99")
        );
        exchangeService.exchange(requestToUSD);

        var requestToPLN = new CurrencyExchangeRequest(
                1L,
                new CurrencyRequest("USD"),
                new CurrencyRequest("PLN"),
                new BigDecimal("101.51")
        );

        //When
        var response = exchangeService.exchange(requestToPLN);

        //Then
        assertEquals(Currency.getInstance("USD"), response.getFrom());
        assertEquals(Currency.getInstance("PLN"), response.getTo());
        assertNotNull(response.getRate());
        assertNotNull(response.getTimestamp());
    }

    @Test
    void shouldSubtractCorrectAmountAfterConversion() {
        //Given
        var accountId = createTestAccount();
        var request = new CurrencyExchangeRequest(
                accountId,
                new CurrencyRequest("PLN"),
                new CurrencyRequest("USD"),
                new BigDecimal("650.11")
        );

        //When
        var response = exchangeService.exchange(request);

        var sourceBalanceData = response.getUpdatedBalances().stream().filter(b -> b.currency().equals(Currency.getInstance("PLN"))).findFirst();

        //Then
        assertEquals(new BigDecimal("1349.89"), sourceBalanceData.get().amount());
    }

    private Long createTestAccount() {
        var request = new AccountCreateRequest("Tomasz", "Nowak", new BigDecimal("2000.00"));
        return accountService.createAccount(request).id();
    }

    @Test
    void shouldOpenNewUSDBalanceAfterFirstExchange() {
        var accountId = createTestAccount();
        var request = new CurrencyExchangeRequest(
                accountId,
                new CurrencyRequest("PLN"),
                new CurrencyRequest("USD"),
                new BigDecimal("100.55")
        );

        //When
        exchangeService.exchange(request);
        var balanceInUSD = accountService.getAccountDetails(accountId).balances().stream().filter(b -> b.currency().equals(Currency.getInstance("USD"))).findFirst();

        //Then
        assertTrue(balanceInUSD.isPresent());
    }

}
