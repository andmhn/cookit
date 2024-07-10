package com.github.andmhn.cookit.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.andmhn.cookit.model.User;
import com.github.andmhn.cookit.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
    public boolean hasUserWithEmail(String email) {
    	return userRepository.existsByEmail(email);
    }

    public User saveUser(User user) {
    	// encode password here
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> validEmailAndPassword(String email, String password) {
        return getUserByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

	public User validateAndGetUserByEmail(String email) {
        return getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException(String.format(
                		"User with email %s not found", email)));

	}
	
}
