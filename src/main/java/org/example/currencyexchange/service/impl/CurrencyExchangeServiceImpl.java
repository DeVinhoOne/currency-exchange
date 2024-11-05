package org.example.currencyexchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.model.CurrencyRateResponse;
import org.example.currencyexchange.model.dto.CurrencyExchangeRequest;
import org.example.currencyexchange.model.dto.CurrencyExchangeResponse;
import org.example.currencyexchange.model.entity.Balance;
import org.example.currencyexchange.repository.AccountRepository;
import org.example.currencyexchange.service.CurrencyExchangeService;
import org.example.currencyexchange.service.CurrencyRatesService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final AccountRepository accountRepository;
    private final CurrencyRatesService currencyRatesService;

    @Override
    public CurrencyExchangeResponse exchange(final CurrencyExchangeRequest request) {
        var account = accountRepository
                .findById(request.accountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        var sourceBalance = account.getBalance(request.from().getCurrency())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not have a opened balance in " + request.from()));
        if (sourceBalance.getAmount().compareTo(request.amount()) < 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient funds");
        }

        var targetBalanceOpt = account.getBalance(request.to().getCurrency());

        var currencyRate = currencyRatesService.fetchRate();
        var targetAmount = calculateNewAmount(request, currencyRate);
        if (targetBalanceOpt.isEmpty()) {
            var targetBalance = new Balance();
            targetBalance.setCurrency(request.to().getCurrency());
            targetBalance.setAmount(targetAmount);
            account.addBalance(targetBalance);
            sourceBalance.setAmount(sourceBalance.getAmount().subtract(request.amount()));
        } else {
            var targetBalance = targetBalanceOpt.get();
            targetBalance.setAmount(targetAmount);
            sourceBalance.setAmount(sourceBalance.getAmount().subtract(request.amount()));
        }
        accountRepository.save(account);
        return new CurrencyExchangeResponse(currencyRate.getBuy(), request.from().getCurrency(), request.to().getCurrency(), targetAmount, LocalDateTime.now());
    }

    private BigDecimal calculateNewAmount(final CurrencyExchangeRequest request, final CurrencyRateResponse currencyRate) {
        return request.amount().divide(currencyRate.getBuy(), 2, RoundingMode.HALF_DOWN);
    }

}
