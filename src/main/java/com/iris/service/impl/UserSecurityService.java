package com.iris.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iris.domain.User;
import com.iris.repository.UserRepo;

@Service
public class UserSecurityService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (null == user) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
		return user;
	}
	
	public User getUserByUsername(String username) {
		User user = userRepo.findByUsername(username);
		if (null == user) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
		return user;
	}
}
