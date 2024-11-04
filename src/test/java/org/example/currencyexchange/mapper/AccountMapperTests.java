package org.example.currencyexchange.mapper;

import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.model.dto.AccountCreateResponse;
import org.example.currencyexchange.model.entity.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

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
        var account = mapper.map(request);

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
        var accountCreateResponse = mapper.map(account);

        //Then
        assertEquals(8L, accountCreateResponse.id());
    }

}
