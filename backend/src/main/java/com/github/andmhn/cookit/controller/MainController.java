package com.github.andmhn.cookit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.andmhn.cookit.model.User;
import com.github.andmhn.cookit.model.UserRepository;

@RestController
public class MainController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(path = "/register")
	public String registerNew(@RequestBody User user) {
		// forbid duplicate user registration
		if(userRepository.findByEmail(user.getEmail()).isPresent()) {
			return "Error: User already present!!!";
		} else {
			// encode password before saving
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			userRepository.save(user);
			return "User saved!";
		}
	}
	
	@GetMapping(path = "/findAll")
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
}
