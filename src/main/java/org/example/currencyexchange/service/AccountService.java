package org.example.currencyexchange.service;

import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.model.dto.AccountCreateResponse;

public interface AccountService {

    AccountCreateResponse createAccount(AccountCreateRequest request);

}
