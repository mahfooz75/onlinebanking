package com.iris.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iris.domain.AccountNumberGenerator;

@Repository
public interface AccountNumberGeneratorRepo extends CrudRepository<AccountNumberGenerator, Long> {
	@Query("SELECT max(accountNumber) FROM AccountNumberGenerator")
	public Integer getMaxAccountNumber();
}
