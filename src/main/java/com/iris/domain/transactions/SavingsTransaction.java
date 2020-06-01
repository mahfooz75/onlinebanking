package com.iris.domain.transactions;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.iris.domain.account.SavingsAccount;

@Entity
public class SavingsTransaction extends Transaction {

	@ManyToOne
	@JoinColumn(name = "saving_account_id")
	private SavingsAccount savingsAccount;
	
	

	public SavingsTransaction() {
		super();
	}

	public SavingsTransaction(Date date, String description, String type, String status, double amount,
			BigDecimal availableBalance,SavingsAccount savingsAccount) {
		super(date, description, type, status, amount, availableBalance);
		this.savingsAccount = savingsAccount;
	}

	public SavingsAccount getSavingsAccount() {
		return savingsAccount;
	}

	public void setSavingsAccount(SavingsAccount savingsAccount) {
		this.savingsAccount = savingsAccount;
	}

}
