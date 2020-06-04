package com.iris.service;

import com.iris.domain.ResponseMessage;
import com.iris.domain.account.CheckingAccount;
import com.iris.domain.account.MoneyMarketAccount;
import com.iris.domain.account.SavingsAccount;

public interface AccountService {

	SavingsAccount createSavingsAccount();
	CheckingAccount createCheckingAccount();
	MoneyMarketAccount createMoneyMarketAccount();
	ResponseMessage deposit(String accountType, double amount, String name);
	ResponseMessage withdraw(String accountType, double amount, String name);
	
}
