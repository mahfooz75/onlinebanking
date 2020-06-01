package com.iris.domain.account;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iris.domain.transactions.MoneyMarketTransaction;

@Entity
public class MoneyMarketAccount extends AccountType {
	
	@OneToMany(mappedBy = "moneyMarketAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<MoneyMarketTransaction> moneyMarketTransactionList;

	public List<MoneyMarketTransaction> getMoneyMarketTransactionList() {
		return moneyMarketTransactionList;
	}

	public void setMoneyMarketTransactionList(List<MoneyMarketTransaction> moneyMarketTransactionList) {
		this.moneyMarketTransactionList = moneyMarketTransactionList;
	}

}
