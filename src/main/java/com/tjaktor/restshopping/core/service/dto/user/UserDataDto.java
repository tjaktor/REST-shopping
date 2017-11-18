package com.tjaktor.restshopping.core.service.dto.user;

import java.io.Serializable;
import java.util.Set;

public class UserDataDto implements Serializable {

	private static final long serialVersionUID = -2655834917681237903L;
	
	private Long id;
	private String username;
	private String email;
	private Set<UserRoleDataDto> roles;
	
	public UserDataDto() {}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Set<UserRoleDataDto> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRoleDataDto> roles) {
		this.roles = roles;
	}
	
}
