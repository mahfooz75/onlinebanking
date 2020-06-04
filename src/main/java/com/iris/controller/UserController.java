package com.iris.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iris.domain.ResponseMessage;
import com.iris.domain.User;
import com.iris.request.UserLoginRequestModel;
import com.iris.request.UserLoginResponseModel;
import com.iris.security.SecurityConstants;
import com.iris.securityjwt.util.JwtUtil;
import com.iris.service.AccountService;
import com.iris.service.MapValidationErrorService;
import com.iris.service.UserService;
import com.iris.service.impl.UserSecurityService;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AuthenticationManager authenticationManager;

	
	@Autowired 
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@PostMapping
	public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
		
		ResponseEntity<?> errorMap=mapValidationErrorService.mapValidationService(result);
		if(errorMap!=null) {
			return errorMap;
		}
		
		if (userService.checkUserExists(user.getUsername(), user.getEmail())) {
			Map<String, String> uniqueErrorMap=new HashMap<>();
			ResponseMessage message = new ResponseMessage();
			String userErrormessage = "User " + user.getUsername() + " already exist";
			message.setMessage(userErrormessage);
			uniqueErrorMap.put("username", userErrormessage);
			return new ResponseEntity<Map<String, String>>(uniqueErrorMap,HttpStatus.BAD_REQUEST);
		}
		user.setCheckingAccount(accountService.createCheckingAccount());
		user.setSavingsAccount(accountService.createSavingsAccount());
		user.setMoneyMarketAccount(accountService.createMoneyMarketAccount());

		User savedUser = userService.save(user);
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> getUser(@PathVariable String username){
		User user = userService.findByUsername(username);
		if(user==null) {
			ResponseMessage message = new ResponseMessage();
			message.setMessage("User " + username + " does not exist");
			return new ResponseEntity<ResponseMessage>(message, HttpStatus.OK);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserLoginRequestModel authenticationRequest, BindingResult result, HttpServletResponse response)
			throws Exception {
		try {
			ResponseEntity<?> errorMap=mapValidationErrorService.mapValidationService(result);
			if(errorMap!=null) {
				return errorMap;
			}
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authenticate);
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password");
		}
		
		final UserDetails userDetail = userSecurityService.loadUserByUsername(authenticationRequest.getUsername());
		String jwt = jwtUtil.generateToken(userDetail);
		response.setHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX +jwt);
		/*
		 * String extractUsername = jwtUtil.extractUsername(jwt);
		 * System.out.println(extractUsername);
		 */
		return ResponseEntity.ok(new UserLoginResponseModel(true, jwt));
	}

}
