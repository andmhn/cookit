package com.github.andmhn.cookit.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.github.andmhn.cookit.model.User;
import com.github.andmhn.cookit.rest.dto.AuthResponse;
import com.github.andmhn.cookit.rest.dto.LoginRequest;
import com.github.andmhn.cookit.rest.dto.SignUpRequest;
import com.github.andmhn.cookit.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
	
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new RuntimeException(String.format("Email %s is already"
            		+ " been used", signUpRequest.getEmail()));
        }

        User user = userService.saveUser(createUser(signUpRequest));
        return new AuthResponse(user.getId(), user.getEmail(), user.getFullname());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
    		@RequestBody LoginRequest loginRequest) {

    	Optional<User> userOptional = userService.validEmailAndPassword(
    			loginRequest.getEmail(), loginRequest.getPassword());

    	if(userOptional.isPresent()) {
    		User user = userOptional.get();
    		return ResponseEntity.ok(new AuthResponse(
    				user.getId(), user.getEmail(), user.getFullname()));
    	} else {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    }

    private User createUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setPassword(signUpRequest.getPassword());
        user.setFullname(signUpRequest.getFullname());
        user.setEmail(signUpRequest.getEmail());
        return user;
    }
}
