package org.example.currencyexchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.mapper.AccountMapper;
import org.example.currencyexchange.model.entity.Account;
import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.model.dto.AccountCreateResponse;
import org.example.currencyexchange.repository.AccountRepository;
import org.example.currencyexchange.service.AccountService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    @Override
    public AccountCreateResponse createAccount(final AccountCreateRequest request) {
        final Account account = mapper.map(request);
        return mapper.map(accountRepository.save(account));
    }

}
