package org.example.currencyexchange.mapper;

import org.example.currencyexchange.model.CurrencyRateResponse;
import org.example.currencyexchange.model.nbp.CurrencyApiResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CurrencyRatesMapper {

    @Mapping(target = "currencyCode", source = "code")
    CurrencyRateResponse map(final CurrencyApiResponse response);

    @AfterMapping
    default void mapRates(final CurrencyApiResponse response, @MappingTarget final CurrencyRateResponse target) {
        if (!response.rates().isEmpty()) {
            var rate = response.rates().getFirst();
            target.setBuy(rate.ask());
            target.setSell(rate.bid());
            target.setDate(rate.effectiveDate());
        }
    }
}
