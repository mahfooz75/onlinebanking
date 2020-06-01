package com.iris.service;

import com.iris.domain.AccountNumberGenerator;

public interface AccountNumberGeneratorService {

	Integer getMaxAccountNumber();

	AccountNumberGenerator save(AccountNumberGenerator accountNumberGenerator);

}
