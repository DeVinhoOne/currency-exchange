package org.example.currencyexchange.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Balance> balances = new HashSet<>();

    public void addBalance(final Balance balance) {
        if (balance == null) {
            throw new IllegalArgumentException("Balance cannot be null");
        }
        balance.setAccount(this);
        balances.add(balance);
    }

}
