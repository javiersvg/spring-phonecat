package com.examplecorp.phonecat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import com.examplecorp.phonecat.model.Phone;

public interface PhoneRepository extends MongoRepository<Phone, String> {

	@SuppressWarnings("unchecked")
	@PreAuthorize("#phone.ownerId == principal?.username")
	Phone save(@Param("phone") Phone phone);

}
