package com.iris.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iris.domain.User;
import com.iris.domain.account.CheckingAccount;
import com.iris.domain.account.MoneyMarketAccount;
import com.iris.domain.account.SavingsAccount;
import com.iris.domain.transactions.CheckingTransaction;
import com.iris.domain.transactions.MoneyMarketTransaction;
import com.iris.domain.transactions.SavingsTransaction;
import com.iris.repository.account.CheckingAccountRepo;
import com.iris.repository.account.MoneyMarketAccountRepo;
import com.iris.repository.account.SavingsAccountRepo;
import com.iris.repository.transactions.CheckingTransactionRepo;
import com.iris.repository.transactions.MoneyMarketTransactionRepo;
import com.iris.repository.transactions.SavingsTransactionRepo;
import com.iris.request.TransferWithinAccountRequest;
import com.iris.service.TransactionService;
import com.iris.service.UserService;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SavingsTransactionRepo savingsTransactionRepo;
	
	@Autowired
	private CheckingTransactionRepo checkingTransactionRepo;
	
	@Autowired
	private MoneyMarketTransactionRepo moneyMarketTransactionRepo;
	
	@Autowired
	private SavingsAccountRepo savingsAccountRepo;
	
	@Autowired
	private CheckingAccountRepo checkingAccountRepo;
	
	@Autowired
	private MoneyMarketAccountRepo moneyMarketAccountRepo;
	
	@Override
	public List<SavingsTransaction> findSavingsTransactionList(String username){
		User user = userService.findByUsername(username);
		return user.getSavingsAccount().getSavingTransactionList();
	}
	
	@Override
	public List<CheckingTransaction> findCheckingTransactionList(String username){
		User user = userService.findByUsername(username);
		return user.getCheckingAccount().getCheckingTransactionList();
	}
	
	@Override
	public List<MoneyMarketTransaction> findMoneyMarketTransactionList(String username){
		User user = userService.findByUsername(username);
		return user.getMoneyMarketAccount().getMoneyMarketTransactionList();
	}
	
	@Override
	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction){
		savingsTransactionRepo.save(savingsTransaction);
	}

	@Override
	public void saveCheckingDepositTransaction(CheckingTransaction checkingTransaction) {
		checkingTransactionRepo.save(checkingTransaction);
	}
	
	@Override
	public void saveMoneyMarketDepositTransaction(MoneyMarketTransaction moneyMarketTransaction) {
		moneyMarketTransactionRepo.save(moneyMarketTransaction);
	}
	

	@Override
	public void betweenAccountsTransfer(TransferWithinAccountRequest transferWithinAccountRequest, User user) {
		String fromAccount = transferWithinAccountRequest.getFromAccount();
		String toAccount = transferWithinAccountRequest.getToAccount();
		BigDecimal transferedAmount = transferWithinAccountRequest.getAmount();
		
		SavingsAccount savingsAccount = user.getSavingsAccount();
		CheckingAccount checkingAccount = user.getCheckingAccount();
		MoneyMarketAccount moneyMarketAccount = user.getMoneyMarketAccount();
		// From Savings to Checking and MoneyMarket
		if("Savings".equalsIgnoreCase(fromAccount) && "Checking".equalsIgnoreCase(toAccount)) {
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(transferedAmount));
			checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().add(transferedAmount));
			savingsAccountRepo.save(savingsAccount);
			checkingAccountRepo.save(checkingAccount);
			SavingsTransaction savingsTransaction = new SavingsTransaction(new Date(), "Between account transfer from "+fromAccount+" to "+toAccount, "Transfer", "Finished", transferedAmount.doubleValue(), savingsAccount.getAccountBalance(), savingsAccount);
			savingsTransactionRepo.save(savingsTransaction);
		}
		else if("Savings".equalsIgnoreCase(fromAccount) && "MoneyMarket".equalsIgnoreCase(toAccount)) {
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(transferedAmount));
			moneyMarketAccount.setAccountBalance(moneyMarketAccount.getAccountBalance().add(transferedAmount));
			savingsAccountRepo.save(savingsAccount);
			moneyMarketAccountRepo.save(moneyMarketAccount);
			SavingsTransaction savingsTransaction = new SavingsTransaction(new Date(), "Between account transfer from "+fromAccount+" to "+toAccount, "Transfer", "Finished", transferedAmount.doubleValue(), savingsAccount.getAccountBalance(), savingsAccount);
			savingsTransactionRepo.save(savingsTransaction);
		}
		// From Checking to Saving and Money Market
		else if("Checking".equalsIgnoreCase(fromAccount) && "Savings".equalsIgnoreCase(toAccount)) {
			checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(transferedAmount));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(transferedAmount));
			checkingAccountRepo.save(checkingAccount);
			savingsAccountRepo.save(savingsAccount);
			CheckingTransaction checkingTransaction = new CheckingTransaction(new Date(), "Between account transfer from "+fromAccount+" to "+toAccount, "Transfer", "Finished", transferedAmount.doubleValue(), checkingAccount.getAccountBalance(), checkingAccount);
			checkingTransactionRepo.save(checkingTransaction);
		}
		else if("Checking".equalsIgnoreCase(fromAccount) && "MoneyMarket".equalsIgnoreCase(toAccount)) {
			checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(transferedAmount));
			moneyMarketAccount.setAccountBalance(moneyMarketAccount.getAccountBalance().add(transferedAmount));
			checkingAccountRepo.save(checkingAccount);
			moneyMarketAccountRepo.save(moneyMarketAccount);
			CheckingTransaction checkingTransaction = new CheckingTransaction(new Date(), "Between account transfer from "+fromAccount+" to "+toAccount, "Transfer", "Finished", transferedAmount.doubleValue(), checkingAccount.getAccountBalance(), checkingAccount);
			checkingTransactionRepo.save(checkingTransaction);
		}
		// From Money Market to Saving and Checking
		else if("MoneyMarket".equalsIgnoreCase(fromAccount) && "Savings".equalsIgnoreCase(toAccount)) {
			moneyMarketAccount.setAccountBalance(moneyMarketAccount.getAccountBalance().subtract(transferedAmount));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(transferedAmount));
			moneyMarketAccountRepo.save(moneyMarketAccount);
			savingsAccountRepo.save(savingsAccount);
			MoneyMarketTransaction moneyMarketTransaction = new MoneyMarketTransaction(new Date(), "Between account transfer from "+fromAccount+" to "+toAccount, "Transfer", "Finished", transferedAmount.doubleValue(),moneyMarketAccount.getAccountBalance(), moneyMarketAccount);
			moneyMarketTransactionRepo.save(moneyMarketTransaction);
		}
		else if("MoneyMarket".equalsIgnoreCase(fromAccount) && "Checking".equalsIgnoreCase(toAccount)) {
			moneyMarketAccount.setAccountBalance(moneyMarketAccount.getAccountBalance().subtract(transferedAmount));
			checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().add(transferedAmount));
			moneyMarketAccountRepo.save(moneyMarketAccount);
			checkingAccountRepo.save(checkingAccount);
			MoneyMarketTransaction moneyMarketTransaction = new MoneyMarketTransaction(new Date(), "Between account transfer from "+fromAccount+" to "+toAccount, "Transfer", "Finished", transferedAmount.doubleValue(),moneyMarketAccount.getAccountBalance(), moneyMarketAccount);
			moneyMarketTransactionRepo.save(moneyMarketTransaction);
		}
	}
	
}
