package com.iris.service;

import java.util.List;

import com.iris.domain.User;
import com.iris.domain.transactions.CheckingTransaction;
import com.iris.domain.transactions.MoneyMarketTransaction;
import com.iris.domain.transactions.SavingsTransaction;
import com.iris.request.TransferWithinAccountRequest;

public interface TransactionService {

	List<SavingsTransaction> findSavingsTransactionList(String username);

	List<CheckingTransaction> findCheckingTransactionList(String username);

	List<MoneyMarketTransaction> findMoneyMarketTransactionList(String username);

	void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction);

	void saveCheckingDepositTransaction(CheckingTransaction checkingTransaction);

	void saveMoneyMarketDepositTransaction(MoneyMarketTransaction moneyMarketTransaction);

	void betweenAccountsTransfer(TransferWithinAccountRequest transferWithinAccountRequest, User user);

}
