package org.example.currencyexchange.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Getter
@Setter
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Currency currency;
    private BigDecimal amount = BigDecimal.ZERO;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)  // Foreign key column in the Balance table
    private Account account;

    public void add(final BigDecimal toAdd) {
        amount.add(toAdd);
    }
}
