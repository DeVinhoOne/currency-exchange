package org.example.currencyexchange.account;

import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.model.dto.AccountCreateResponse;
import org.example.currencyexchange.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AccountTests {

    @Autowired
    private AccountService accountService;

    @Test
    void shouldCreateNewAccount() {
        //When
        var response = createTestAccount();

        //Then
        assertEquals(1, response.id());
    }

    @Test
    void shouldGetAccountDetailsById() {
        //Given
        createTestAccount();

        //When
        var response = accountService.getAccountDetails(1L);

        //Then
        assertNotNull(response);
        assertEquals("Franciszek", response.firstName());
        assertEquals("Kowalski", response.lastName());
        assertEquals(1, response.balances().size());
        assertEquals(new BigDecimal("900.00"), response.balances().getFirst().amount());
        assertEquals(Currency.getInstance("PLN"), response.balances().getFirst().currency());
    }

    private AccountCreateResponse createTestAccount() {
        var request = new AccountCreateRequest("Franciszek", "Kowalski", BigDecimal.valueOf(900));
        return accountService.createAccount(request);
    }

}
