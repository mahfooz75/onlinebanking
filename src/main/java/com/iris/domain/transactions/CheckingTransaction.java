package com.iris.domain.transactions;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.iris.domain.account.CheckingAccount;

@Entity
public class CheckingTransaction extends Transaction {
	@ManyToOne
	@JoinColumn(name = "checking_account_id")
	private CheckingAccount checkingAccount;
	
	
	public CheckingTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CheckingTransaction(
			Date date, String description, String type, String status, double amount,
			BigDecimal availableBalance,CheckingAccount checkingAccount) {
		super(date, description, type, status, amount, availableBalance);
		this.checkingAccount = checkingAccount;
	}


	public CheckingAccount getCheckingAccount() {
		return checkingAccount;
	}

	public void setCheckingAccount(CheckingAccount checkingAccount) {
		this.checkingAccount = checkingAccount;
	}

}
