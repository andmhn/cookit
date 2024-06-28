package com.github.andmhn.cookit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.andmhn.cookit.entity.User;
import com.github.andmhn.cookit.repository.UserRepository;

@RestController
public class MainController {
	@Autowired
	UserRepository userRepository;
	
	@PostMapping(path = "/register")
	public ResponseEntity<User> registerNew(@RequestBody User user) {
		User saved = userRepository.save(user);
		return ResponseEntity.ok(saved);
	}
	
	@GetMapping(path = "/findAll")
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
}
