package org.example.currencyexchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.mapper.AccountMapper;
import org.example.currencyexchange.model.dto.AccountDetailsResponse;
import org.example.currencyexchange.model.entity.Account;
import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.model.dto.AccountCreateResponse;
import org.example.currencyexchange.repository.AccountRepository;
import org.example.currencyexchange.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    @Override
    public AccountCreateResponse createAccount(final AccountCreateRequest request) {
        final Account account = mapper.mapToAccount(request);
        return mapper.mapToAccountCreateResponse(accountRepository.save(account));
    }

    @Override
    public AccountDetailsResponse getAccountDetails(Long accountId) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        Account account = accountOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        return mapper.mapToAccountDetailsResponse(account);
    }

}
