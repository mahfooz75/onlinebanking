package com.iris.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iris.domain.ResponseMessage;
import com.iris.domain.User;
import com.iris.request.TransferWithinAccountRequest;
import com.iris.service.TransactionService;
import com.iris.service.UserService;

@RestController
@RequestMapping("transfer")
public class TransferController {
	
	@Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;
    
    @PostMapping("/betweenAccounts")
    public ResponseEntity<?> betweenAccounts(@RequestBody TransferWithinAccountRequest transferWithinAccountRequest, Principal principal){
    	User user = userService.findByUsername(principal.getName());
    	if(user==null) {
    		ResponseMessage message = new ResponseMessage();
    		message.setMessage("User "+principal.getName()+" does not exist");
    		return new ResponseEntity<>(message, HttpStatus.OK);
    	}
    	transactionService.betweenAccountsTransfer(transferWithinAccountRequest,user);
    	return new ResponseEntity<>(HttpStatus.OK);
    }

}
