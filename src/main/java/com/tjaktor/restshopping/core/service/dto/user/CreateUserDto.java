package com.tjaktor.restshopping.core.service.dto.user;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


public class CreateUserDto implements Serializable {

	private static final long serialVersionUID = -2655834917681237903L;
		
	@NotEmpty(message = "username can not be empty")
	private String username; 
	
	@NotEmpty(message = "password can not be empty")
	private String password;
	
	@NotEmpty(message = "password can not be empty")
	private String passwordAgain;

	@Email(message = "email address has to be valid address")
	@NotEmpty(message = "email address can not be empty")
	private String email;
	
	private List<String> roles;
	
	public CreateUserDto() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain = passwordAgain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
