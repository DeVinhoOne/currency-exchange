package org.example.currencyexchange.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.currencyexchange.mapper.AccountMapper;
import org.example.currencyexchange.model.CurrencyRateResponse;
import org.example.currencyexchange.model.dto.CurrencyExchangeRequest;
import org.example.currencyexchange.model.dto.CurrencyExchangeResponse;
import org.example.currencyexchange.model.entity.Balance;
import org.example.currencyexchange.repository.AccountRepository;
import org.example.currencyexchange.service.CurrencyExchangeService;
import org.example.currencyexchange.service.CurrencyRatesService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final AccountRepository accountRepository;
    private final CurrencyRatesService currencyRatesService;
    private final AccountMapper accountMapper;

    @Transactional
    @Override
    public CurrencyExchangeResponse exchange(final CurrencyExchangeRequest request) {
        var account = accountRepository
                .findById(request.accountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        var sourceBalance = account.getBalance(request.from().getCurrency())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account does not have a opened balance in " + request.from().getCurrencyCode()));
        if (sourceBalance.getAmount().compareTo(request.amount()) < 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Insufficient funds");
        }

        var targetBalanceOpt = account.getBalance(request.to().getCurrency());

        var currencyRate = currencyRatesService.fetchRate();
        var currencyExchangeResponse = calculateNewAmount(request, currencyRate);
        Balance targetBalance;
        if (targetBalanceOpt.isEmpty()) {
            targetBalance = new Balance();
            targetBalance.setCurrency(request.to().getCurrency());
            targetBalance.setAmount(targetBalance.getAmount().add(currencyExchangeResponse.getAmount()));
            account.addBalance(targetBalance);
            sourceBalance.setAmount(sourceBalance.getAmount().subtract(request.amount()));
        } else {
            targetBalance = targetBalanceOpt.get();
            targetBalance.setAmount(targetBalance.getAmount().add(currencyExchangeResponse.getAmount()));
            sourceBalance.setAmount(sourceBalance.getAmount().subtract(request.amount()));
        }
        accountRepository.save(account);
        mapCurrencyExchangeResponse(currencyExchangeResponse, sourceBalance, targetBalance);
        return currencyExchangeResponse;
    }

    private void mapCurrencyExchangeResponse(CurrencyExchangeResponse currencyExchangeResponse, final Balance sourceBalance, final Balance targetBalance) {
        currencyExchangeResponse.setUpdatedBalances(accountMapper.mapBalances(List.of(sourceBalance, targetBalance)));
        currencyExchangeResponse.setTimestamp(LocalDateTime.now());
        currencyExchangeResponse.setFrom(sourceBalance.getCurrency());
        currencyExchangeResponse.setTo(targetBalance.getCurrency());
    }

    private CurrencyExchangeResponse calculateNewAmount(final CurrencyExchangeRequest request, final CurrencyRateResponse currencyRate) {
        var currencyExchangeResponse = new CurrencyExchangeResponse();
        if (request.from().getCurrencyCode().equals("PLN")) {
            currencyExchangeResponse.setAmount(sellPLN(request.amount(), currencyRate.getBuy()));
            currencyExchangeResponse.setRate(currencyRate.getBuy());
        } else {
            currencyExchangeResponse.setAmount(buyPLN(request.amount(), currencyRate.getSell()));
            currencyExchangeResponse.setRate(currencyRate.getSell());
        }
        return currencyExchangeResponse;
    }

    private BigDecimal sellPLN(final BigDecimal amount, final BigDecimal rate) {
        return amount.divide(rate, 2, RoundingMode.HALF_DOWN);
    }

    private BigDecimal buyPLN(final BigDecimal amount, final BigDecimal rate) {
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

}
