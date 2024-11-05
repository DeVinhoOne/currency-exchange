package org.example.currencyexchange.rest.controller;

import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.model.dto.AccountCreateRequest;
import org.example.currencyexchange.model.dto.AccountCreateResponse;
import org.example.currencyexchange.model.dto.AccountDetailsResponse;
import org.example.currencyexchange.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<AccountCreateResponse> createAccount(@RequestBody final AccountCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(request));
    }

    @GetMapping
    public ResponseEntity<AccountDetailsResponse> getAccountData(@RequestParam final Long accountId) {
        return ResponseEntity.ok(service.getAccountDetails(accountId));
    }

}
