package com.iris.domain.account;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iris.domain.transactions.CheckingTransaction;

@Entity
public class CheckingAccount extends AccountType{
	
	@OneToMany(mappedBy = "checkingAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CheckingTransaction> checkingTransactionList;

	public List<CheckingTransaction> getCheckingTransactionList() {
		return checkingTransactionList;
	}

	public void setCheckingTransactionList(List<CheckingTransaction> checkingTransactionList) {
		this.checkingTransactionList = checkingTransactionList;
	}

}
