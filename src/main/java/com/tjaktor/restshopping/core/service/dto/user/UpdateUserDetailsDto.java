package com.tjaktor.restshopping.core.service.dto.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UpdateUserDetailsDto {
	
	@NotEmpty(message = "username can not be empty")
	private String username;
	
	@Email(message = "email address has to be valid address")
	@NotEmpty(message = "email address can not be empty")
	private String email;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
