package com.tjaktor.restshopping.core.service.dto.user;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangeUserPasswordDto {

	@NotEmpty(message = "current password can not be empty")
	private String currentPassword;
	
	@NotEmpty(message = "password can not be empty")
	private String password;
	
	@NotEmpty(message = "password confirmation can not be empty")
	private String passwordAgain;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
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
	
	
	
}
