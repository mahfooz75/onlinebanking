package com.iris.repository.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iris.domain.account.MoneyMarketAccount;

@Repository
public interface MoneyMarketAccountRepo extends CrudRepository<MoneyMarketAccount, Long> {
	MoneyMarketAccount findByAccountNumber(int accountNumber);
}
