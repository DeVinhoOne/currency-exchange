package org.example.currencyexchange.model.nbp;

import java.util.List;

public record CurrencyApiResponse(
    String table,
    String currency,
    String code,
    List<Rate> rates
) {}