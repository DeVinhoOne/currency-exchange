package org.example.currencyexchange.model.nbp;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Rate(
    String no,
    LocalDate effectiveDate,
    BigDecimal bid,
    BigDecimal ask
) {}