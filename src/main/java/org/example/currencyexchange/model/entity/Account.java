package org.example.currencyexchange.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Balance> balances = new ArrayList<>();

    public void addBalance(final Balance balance) {
        if (balance == null) {
            throw new IllegalArgumentException("Balance cannot be null");
        }
        balance.setAccount(this);
        balances.add(balance);
    }

}
