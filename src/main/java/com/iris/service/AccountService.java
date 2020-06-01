package com.iris.service;

import com.iris.domain.account.CheckingAccount;
import com.iris.domain.account.MoneyMarketAccount;
import com.iris.domain.account.SavingsAccount;

public interface AccountService {

	SavingsAccount createSavingsAccount();
	CheckingAccount createCheckingAccount();
	MoneyMarketAccount createMoneyMarketAccount();
	String deposit(String accountType, double amount, String name);
	String withdraw(String accountType, double amount, String name);
	
}
