package org.example.currencyexchange.mapper;

import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.model.dto.AccountCreateResponse;
import org.example.currencyexchange.model.dto.AccountDetailsResponse;
import org.example.currencyexchange.model.dto.BalanceData;
import org.example.currencyexchange.model.entity.Account;
import org.example.currencyexchange.model.entity.Balance;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Currency;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "balances", ignore = true)
    Account mapToAccount(final AccountCreateRequest request);

    AccountCreateResponse mapToAccountCreateResponse(final Account account);

    AccountDetailsResponse mapToAccountDetailsResponse(final Account account);

    List<BalanceData> mapBalances(final List<Balance> balances);

    @AfterMapping
    default void setBalances(final AccountCreateRequest request, @MappingTarget final Account account) {
        final Balance balance = new Balance();
        balance.setCurrency(Currency.getInstance("PLN"));
        balance.setAmount(request.initBalancePLN());
        account.addBalance(balance);
    }

}
