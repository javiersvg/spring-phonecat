package com.examplecorp.phonecat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.examplecorp.phonecat.model.User;
import com.examplecorp.phonecat.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

    @RequestMapping(method=RequestMethod.POST)
	public User saveUser(@RequestBody User user){
		return userRepository.save(user);
	}

    @RequestMapping(method=RequestMethod.GET)
	public List<User> queryUser(){
		return userRepository.findAll();
	}
}
