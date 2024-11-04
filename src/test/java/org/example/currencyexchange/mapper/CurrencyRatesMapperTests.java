package org.example.currencyexchange.mapper;

import org.example.currencyexchange.model.CurrencyRateResponse;
import org.example.currencyexchange.model.nbp.CurrencyApiResponse;
import org.example.currencyexchange.model.nbp.Rate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CurrencyRatesMapperTests {

    private CurrencyRatesMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CurrencyRatesMapper.class);
    }

    @Test
    void shouldMapCurrencyApiResponse() {
        // Arrange
        Rate rate = new Rate("214/C/NBP/2024", LocalDate.of(2024, 11, 4), BigDecimal.valueOf(3.9625), BigDecimal.valueOf(4.0425));
        CurrencyApiResponse apiResponse = new CurrencyApiResponse("C", "dolar amerykański", "USD", List.of(rate));

        // Act
        CurrencyRateResponse result = mapper.map(apiResponse);

        // Assert
        assertNotNull(result);
        assertEquals("USD", result.getCurrencyCode());
        assertEquals(BigDecimal.valueOf(4.0425), result.getBuy());
        assertEquals(BigDecimal.valueOf(3.9625), result.getSell());
        assertEquals(LocalDate.of(2024, 11, 4), result.getDate());
    }

}
