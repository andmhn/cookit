package com.github.andmhn.cookit.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andmhn.cookit.model.User;
import com.github.andmhn.cookit.rest.dto.UserDto;
import com.github.andmhn.cookit.security.CustomUserDetails;
import com.github.andmhn.cookit.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;
	
    @GetMapping("/me")
    public UserDto getCurrentUser(
    		@AuthenticationPrincipal CustomUserDetails currentUser) {
        User user = userService.validateAndGetUserByEmail(currentUser.getUsername());
        return new UserDto(user.getId(), user.getFullname(), user.getEmail(),
        		user.getRecipies().size());
    }
}
