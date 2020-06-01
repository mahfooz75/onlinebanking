package com.iris.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iris.domain.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long>{
	User findByUsername(String username);
	User findByEmail(String email);
}
