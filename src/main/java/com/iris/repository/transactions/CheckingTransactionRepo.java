package com.iris.repository.transactions;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iris.domain.transactions.CheckingTransaction;

@Repository
public interface CheckingTransactionRepo extends CrudRepository<CheckingTransaction, Long> {

	List<CheckingTransaction> findAll();
	
}
