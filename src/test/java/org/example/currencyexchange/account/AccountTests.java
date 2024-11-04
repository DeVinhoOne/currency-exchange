package org.example.currencyexchange.account;

import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class AccountTests {

    @Autowired
    private AccountService accountService;

    @Test
    void shouldCreateNewAccount() {
        var request = new AccountCreateRequest("Franciszek", "Kowalski", BigDecimal.valueOf(900));
        var response = accountService.createAccount(request);
        Assertions.assertEquals(1, response.id());
    }

}
