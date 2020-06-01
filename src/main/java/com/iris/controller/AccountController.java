package com.iris.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iris.domain.User;
import com.iris.domain.account.CheckingAccount;
import com.iris.domain.account.MoneyMarketAccount;
import com.iris.domain.account.SavingsAccount;
import com.iris.domain.transactions.CheckingTransaction;
import com.iris.domain.transactions.MoneyMarketTransaction;
import com.iris.domain.transactions.SavingsTransaction;
import com.iris.request.DepositAndWithdrawRequest;
import com.iris.service.AccountService;
import com.iris.service.TransactionService;
import com.iris.service.UserService;

@RestController
@RequestMapping("accounts")
public class AccountController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/savingsAccount")
	public ResponseEntity<?> savingsAccount(Principal principal){
		User user = userService.findByUsername(principal.getName());
		SavingsAccount savingsAccount = user.getSavingsAccount();
		return new ResponseEntity<SavingsAccount>(savingsAccount,HttpStatus.OK);
	}
	
	@GetMapping("/checkingAccount")
	public ResponseEntity<?> checkingAccount(Principal principal){
		User user = userService.findByUsername(principal.getName());
		CheckingAccount checkingAccount = user.getCheckingAccount();
		return new ResponseEntity<CheckingAccount>(checkingAccount,HttpStatus.OK);
	}
	
	@GetMapping("/moneyMarketAccount")
	public ResponseEntity<?> moneyMarketAccount(Principal principal){
		User user = userService.findByUsername(principal.getName());
		MoneyMarketAccount moneyMarketAccount = user.getMoneyMarketAccount();
		return new ResponseEntity<MoneyMarketAccount>(moneyMarketAccount,HttpStatus.OK);
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<?> deposit(@RequestBody DepositAndWithdrawRequest depositRequest, Principal principal){
		String request = accountService.deposit(depositRequest.getAccountType(),depositRequest.getAmount(),principal.getName());
		return new ResponseEntity<String>(request,HttpStatus.OK);
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<?> withdraw(@RequestBody DepositAndWithdrawRequest depositRequest, Principal principal){
		String request = accountService.withdraw(depositRequest.getAccountType(),depositRequest.getAmount(),principal.getName());
		return new ResponseEntity<String>(request,HttpStatus.OK);
	}
	
	@GetMapping("/savingAccountTransactionList")
	public ResponseEntity<?> getSavingAccountTransactionList(Principal principal){
		List<SavingsTransaction> savingsTransactionList = transactionService.findSavingsTransactionList(principal.getName());
		return new ResponseEntity<List<SavingsTransaction>>(savingsTransactionList,HttpStatus.OK);
	}
	
	@GetMapping("/checkingAccountTransactionList")
	public ResponseEntity<?> getCheckingAccountTransactionList(Principal principal){
		List<CheckingTransaction> checkingTransactionList = transactionService.findCheckingTransactionList(principal.getName());
		return new ResponseEntity<List<CheckingTransaction>>(checkingTransactionList,HttpStatus.OK);
	}
	
	@GetMapping("/moneyMarketAccountTransactionList")
	public ResponseEntity<?> getMoneyMarketAccountTransactionList(Principal principal){
		List<MoneyMarketTransaction> moneyMarketTransactionList = transactionService.findMoneyMarketTransactionList(principal.getName());
		return new ResponseEntity<List<MoneyMarketTransaction>>(moneyMarketTransactionList,HttpStatus.OK);
	}

}
