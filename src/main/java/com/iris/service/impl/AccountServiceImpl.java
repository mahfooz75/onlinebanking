package com.iris.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iris.domain.AccountNumberGenerator;
import com.iris.domain.ResponseMessage;
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
import com.iris.service.AccountNumberGeneratorService;
import com.iris.service.AccountService;
import com.iris.service.TransactionService;
import com.iris.service.UserService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private UserService userService;

	@Autowired
	private SavingsAccountRepo savingsAccountRepo;

	@Autowired
	private CheckingAccountRepo checkingAccountRepo;

	@Autowired
	private MoneyMarketAccountRepo moneyMarketAccountRepo;
	
	@Autowired
	private AccountNumberGeneratorService accountNumberGeneratorService;
	
	@Autowired
	private TransactionService transactionService;

	@Override
	public SavingsAccount createSavingsAccount() {
		SavingsAccount savingsAccount = new SavingsAccount();
		savingsAccount.setAccountBalance(new BigDecimal(0.0));
		savingsAccount.setAccountNumber(getGeneratedAccountNumber());

		return savingsAccountRepo.save(savingsAccount);

		// return
		// savingsAccountRepo.findByAccountNumber(savingsAccount.getAccountNumber());
	}

	@Override
	public CheckingAccount createCheckingAccount() {
		CheckingAccount checkingAccount = new CheckingAccount();
		checkingAccount.setAccountBalance(new BigDecimal(0.0));
		checkingAccount.setAccountNumber(getGeneratedAccountNumber());

		return checkingAccountRepo.save(checkingAccount);

		// return
		// checkingAccountRepo.findByAccountNumber(checkingAccount.getAccountNumber());
	}

	@Override
	public MoneyMarketAccount createMoneyMarketAccount() {
		MoneyMarketAccount moneyMarketAccount = new MoneyMarketAccount();
		moneyMarketAccount.setAccountBalance(new BigDecimal(0.0));
		moneyMarketAccount.setAccountNumber(getGeneratedAccountNumber());

		return moneyMarketAccountRepo.save(moneyMarketAccount);

		// return
		// moneyMarketAccountRepo.findByAccountNumber(moneyMarketAccount.getAccountNumber());
	}

	@Override
	public ResponseMessage deposit(final String accountTypeInput, final double amount, final String userName) {
		String accountType=accountTypeInput.replaceAll("\\s+", "");
		User user = userService.findByUsername(userName);
		ResponseMessage response = new ResponseMessage();
		if (accountType.equalsIgnoreCase("Savings")) {
			SavingsAccount savingsAccount = user.getSavingsAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccountRepo.save(savingsAccount);

			Date date = new Date();
			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account",
					"Saving Account", "Finished", amount, savingsAccount.getAccountBalance(),savingsAccount);
			transactionService.saveSavingsDepositTransaction(savingsTransaction);
			response.setMessage("Deposit Success");
		}
		else if (accountType.equalsIgnoreCase("Checking")) {
			CheckingAccount checkingAccount = user.getCheckingAccount();
			checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().add(new BigDecimal(amount)));
			checkingAccountRepo.save(checkingAccount);

			Date date = new Date();
			CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Deposit to Checking Account",
					"Checking Account", "Finished", amount, checkingAccount.getAccountBalance(),checkingAccount);
			transactionService.saveCheckingDepositTransaction(checkingTransaction);
			response.setMessage("Deposit Success");
		}
		else if(accountType.equalsIgnoreCase("MoneyMarket")) {
			MoneyMarketAccount moneyMarketAccount=user.getMoneyMarketAccount();
			moneyMarketAccount.setAccountBalance(moneyMarketAccount.getAccountBalance().add(new BigDecimal(amount)));
			moneyMarketAccountRepo.save(moneyMarketAccount);
			
			Date date = new Date();
			
			MoneyMarketTransaction moneyMarketTransaction = new MoneyMarketTransaction(date, "Deposit to Money Market Account", "Money Market Account", "Finished", amount, moneyMarketAccount.getAccountBalance(), moneyMarketAccount);
			transactionService.saveMoneyMarketDepositTransaction(moneyMarketTransaction);
			response.setMessage("Deposit Success");
		}else {
			response.setMessage("Deposit fails");
		}
		return response;
	}

	@Override
	public ResponseMessage withdraw(String accountTypeInput, double amount, String userName) {
		String accountType=accountTypeInput.replaceAll("\\s+", "");
		User user = userService.findByUsername(userName);
		ResponseMessage response=new ResponseMessage();
		if (accountType.equalsIgnoreCase("Savings")) {
			SavingsAccount savingsAccount = user.getSavingsAccount();
			BigDecimal balanceAmount = savingsAccount.getAccountBalance();
			if(balanceAmount.compareTo(new BigDecimal(amount))<=0) {
				response.setMessage("Insufficient Balance");
				return response;
			}
			savingsAccount.setAccountBalance(balanceAmount.subtract(new BigDecimal(amount)));
			savingsAccountRepo.save(savingsAccount);

			Date date = new Date();
			SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from savings Account",
					"Saving Account", "Finished", amount, savingsAccount.getAccountBalance(),savingsAccount);
			transactionService.saveSavingsDepositTransaction(savingsTransaction);
			response.setMessage("Withdraw Success");
		}
		else if (accountType.equalsIgnoreCase("Checking")) {
			CheckingAccount checkingAccount = user.getCheckingAccount();
			BigDecimal balanceAmount = checkingAccount.getAccountBalance();
			if(balanceAmount.compareTo(new BigDecimal(amount))<=0) {
				response.setMessage("Insufficient Balance");
				return response;
			}
			checkingAccount.setAccountBalance(balanceAmount.subtract(new BigDecimal(amount)));
			checkingAccountRepo.save(checkingAccount);

			Date date = new Date();
			CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Withdraw from Checking Account",
					"Checking Account", "Finished", amount, checkingAccount.getAccountBalance(),checkingAccount);
			transactionService.saveCheckingDepositTransaction(checkingTransaction);
			response.setMessage("Withdraw Success");
		}
		else if(accountType.equalsIgnoreCase("MoneyMarket")) {
			MoneyMarketAccount moneyMarketAccount=user.getMoneyMarketAccount();
			BigDecimal balanceAmount = moneyMarketAccount.getAccountBalance();
			if(balanceAmount.compareTo(new BigDecimal(amount))<0) {
				response.setMessage("Insufficient Balance");
				return response;
			}
			moneyMarketAccount.setAccountBalance(balanceAmount.subtract(new BigDecimal(amount)));
			moneyMarketAccountRepo.save(moneyMarketAccount);
			
			Date date = new Date();
			
			MoneyMarketTransaction moneyMarketTransaction = new MoneyMarketTransaction(date, "Withdraw from Money Market Account", "Money Market Account", "Finished", amount, moneyMarketAccount.getAccountBalance(), moneyMarketAccount);
			transactionService.saveMoneyMarketDepositTransaction(moneyMarketTransaction);
			response.setMessage("Withdraw Success");
		}else {
			response.setMessage("Withdraw Fail");
		}
		return response;
	}
	
	
	private Integer getGeneratedAccountNumber(){
		Integer maxAccountNumber = accountNumberGeneratorService.getMaxAccountNumber();
		if(maxAccountNumber==null) {
			AccountNumberGenerator accountNumberGenerator=new AccountNumberGenerator();
			maxAccountNumber = saveAccountGenerator(accountNumberGenerator);
		}else {
			AccountNumberGenerator accountNumberGenerator=new AccountNumberGenerator();
			accountNumberGenerator.setAccountNumber(maxAccountNumber+1);
			maxAccountNumber = saveAccountGenerator(accountNumberGenerator);
		}
		return maxAccountNumber; 
	}

	private Integer saveAccountGenerator(AccountNumberGenerator accountNumberGenerator) {
		AccountNumberGenerator saveObj = accountNumberGeneratorService.save(accountNumberGenerator);
		return saveObj.getAccountNumber();
	}

}
