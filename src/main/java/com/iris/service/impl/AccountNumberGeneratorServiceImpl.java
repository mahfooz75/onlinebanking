package com.iris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iris.domain.AccountNumberGenerator;
import com.iris.repository.AccountNumberGeneratorRepo;
import com.iris.service.AccountNumberGeneratorService;

@Service
public class AccountNumberGeneratorServiceImpl implements AccountNumberGeneratorService {
	
	@Autowired
	private AccountNumberGeneratorRepo repo;
	
	@Override
	public Integer getMaxAccountNumber() {
		return repo.getMaxAccountNumber();
	}
	
	@Override
	public AccountNumberGenerator save(AccountNumberGenerator accountNumberGenerator) {
		return repo.save(accountNumberGenerator);
	}
	
}
