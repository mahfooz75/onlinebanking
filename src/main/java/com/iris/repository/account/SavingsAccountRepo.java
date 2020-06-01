package com.iris.repository.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iris.domain.account.SavingsAccount;

@Repository
public interface SavingsAccountRepo extends CrudRepository<SavingsAccount, Long>{
	SavingsAccount findByAccountNumber(int accountNumber);
}
