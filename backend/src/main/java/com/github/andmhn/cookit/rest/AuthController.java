package com.github.andmhn.cookit.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.andmhn.cookit.model.User;
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
    public String signUp(@RequestBody SignUpRequest signUpRequest) {
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new RuntimeException(String.format("Email %s is already"
            		+ " been used", signUpRequest.getEmail()));
        }

        User user = userService.saveUser(createUser(signUpRequest));
        return "Id: " + user.getId() + " Name: " + user.getFullname();
    }

    private User createUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setPassword(signUpRequest.getPassword());
        user.setFullname(signUpRequest.getFullname());
        user.setEmail(signUpRequest.getEmail());
        return user;
    }
}
