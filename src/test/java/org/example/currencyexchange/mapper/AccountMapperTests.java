package org.example.currencyexchange.mapper;

import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.model.entity.Account;
import org.example.currencyexchange.model.entity.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountMapperTests {

    private AccountMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AccountMapper.class);
    }

    @Test
    void shouldMapToAccount() {
        // Given
        var request = new AccountCreateRequest(
                "John",
                "Doe",
                new BigDecimal("1000.00")
        );

        // When
        var account = mapper.mapToAccount(request);

        // Then
        assertThat(account.getFirstName()).isEqualTo("John");
        assertThat(account.getLastName()).isEqualTo("Doe");
        assertThat(account.getBalances()).hasSize(1);
    }

    @Test
    void shouldMapToAccountCreateResponse() {
        //Given
        var account = new Account();
        account.setId(8L);

        //When
        var accountCreateResponse = mapper.mapToAccountCreateResponse(account);

        //Then
        assertEquals(8L, accountCreateResponse.id());
    }

    @Test
    void shouldMapToAccountDetailsResponse() {
        //Given
        var balance1 = new Balance();
        balance1.setId(1L);
        balance1.setAmount(new BigDecimal("1100.00"));
        balance1.setCurrency(Currency.getInstance("USD"));
        var balance2 = new Balance();
        balance2.setId(2L);
        balance2.setAmount(new BigDecimal("2200.00"));
        balance2.setCurrency(Currency.getInstance("PLN"));
        var account = new Account();
        account.setId(10L);
        account.setFirstName("Tomek");
        account.setLastName("Kowalski");
        account.addBalance(balance1);
        account.addBalance(balance2);

        //When
        var accountDetailsResponse = mapper.mapToAccountDetailsResponse(account);

        //Then
        assertEquals("Tomek", accountDetailsResponse.firstName());
        assertEquals("Kowalski", accountDetailsResponse.lastName());
        assertEquals(2, accountDetailsResponse.balances().size());

        var responseBalance1 = accountDetailsResponse.balances().get(0);
        var responseBalance2 = accountDetailsResponse.balances().get(1);

        assertEquals(balance1.getAmount(), responseBalance1.amount());
        assertEquals(balance1.getCurrency(), responseBalance1.currency());

        assertEquals(balance2.getAmount(), responseBalance2.amount());
        assertEquals(balance2.getCurrency(), responseBalance2.currency());
    }

}
