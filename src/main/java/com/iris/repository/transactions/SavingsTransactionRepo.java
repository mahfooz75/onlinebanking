package com.iris.repository.transactions;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iris.domain.transactions.SavingsTransaction;

@Repository
public interface SavingsTransactionRepo extends CrudRepository<SavingsTransaction, Long> {
	
	List<SavingsTransaction> findAll();
	
}
