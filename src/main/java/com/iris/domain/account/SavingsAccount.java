package com.iris.domain.account;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iris.domain.transactions.SavingsTransaction;

@Entity
public class SavingsAccount extends AccountType{
	
    @OneToMany(mappedBy = "savingsAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
    private List<SavingsTransaction> savingTransactionList;

	public List<SavingsTransaction> getSavingTransactionList() {
		return savingTransactionList;
	}

	public void setSavingTransactionList(List<SavingsTransaction> savingTransactionList) {
		this.savingTransactionList = savingTransactionList;
	}
}
