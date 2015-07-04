package com.examplecorp.phonecat.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.access.prepost.PostFilter;

import com.examplecorp.phonecat.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	/**
	 * User retrieval has to be limited to logged users and
	 * just should return the information related to the logged user
	 */
	@PostFilter("hasRole('USER') && filterObject.email == principal?.username")
	List<User> findAll();

	User findByEmail(String email);
}
