package com.iris.domain.transactions;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.iris.domain.account.MoneyMarketAccount;

@Entity
public class MoneyMarketTransaction extends Transaction {

	@ManyToOne
	@JoinColumn(name = "money_market_account_id")
	private MoneyMarketAccount moneyMarketAccount;
	
	
	
	public MoneyMarketTransaction() {
		super();
	}

	public MoneyMarketTransaction(Date date, String description, String type, String status, double amount,
			BigDecimal availableBalance,MoneyMarketAccount moneyMarketAccount) {
		super(date, description, type, status, amount, availableBalance);
		this.moneyMarketAccount = moneyMarketAccount;
	}

	public MoneyMarketAccount getMoneyMarketAccount() {
		return moneyMarketAccount;
	}

	public void setMoneyMarketAccount(MoneyMarketAccount moneyMarketAccount) {
		this.moneyMarketAccount = moneyMarketAccount;
	}

}
