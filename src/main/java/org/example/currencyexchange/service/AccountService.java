package org.example.currencyexchange.service;

import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.model.dto.AccountCreateResponse;
import org.example.currencyexchange.model.dto.AccountDetailsResponse;

public interface AccountService {

    AccountCreateResponse createAccount(final AccountCreateRequest request);

    AccountDetailsResponse getAccountDetails(final Long accountId);

}
