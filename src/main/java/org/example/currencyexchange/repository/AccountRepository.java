package org.example.currencyexchange.repository;

import org.example.currencyexchange.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
