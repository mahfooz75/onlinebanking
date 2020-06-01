package com.iris.repository.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iris.domain.account.CheckingAccount;

@Repository
public interface CheckingAccountRepo extends CrudRepository<CheckingAccount, Long>{
	CheckingAccount findByAccountNumber(int accountNumber);
}
