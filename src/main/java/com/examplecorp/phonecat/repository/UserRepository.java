package com.examplecorp.phonecat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.examplecorp.phonecat.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
