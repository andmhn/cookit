package com.github.andmhn.cookit.rest.dto;
import lombok.Data;

@Data
public class SignUpRequest {
	private String email;
	private String fullname;
	private String password;
}
