package org.example.currencyexchange.rest.controller;

import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.model.dto.CurrencyExchangeRequest;
import org.example.currencyexchange.model.dto.CurrencyExchangeResponse;
import org.example.currencyexchange.service.CurrencyExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    @PostMapping
    public ResponseEntity<CurrencyExchangeResponse> exchange(@RequestBody final CurrencyExchangeRequest request) {
        return ResponseEntity.ok(currencyExchangeService.exchange(request));
    }

}
