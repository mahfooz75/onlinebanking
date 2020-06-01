package com.iris.service;

import com.iris.domain.User;

public interface UserService {

	User save(User user);

	User findByUsername(String username);

	User findByEmail(String email);

	boolean checkUserExists(String username, String email);

}
