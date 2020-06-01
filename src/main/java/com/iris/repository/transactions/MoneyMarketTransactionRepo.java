package com.iris.repository.transactions;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iris.domain.transactions.MoneyMarketTransaction;

@Repository
public interface MoneyMarketTransactionRepo extends CrudRepository<MoneyMarketTransaction, Long> {

	List<MoneyMarketTransaction> findAll();
	
}
