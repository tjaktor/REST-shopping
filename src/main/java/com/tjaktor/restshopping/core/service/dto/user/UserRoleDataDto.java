package com.tjaktor.restshopping.core.service.dto.user;

import java.util.Set;

public class UserRoleDataDto {

	private String rolename;
	private Set<UserPrivilegeDataDto> privileges;
	
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Set<UserPrivilegeDataDto> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<UserPrivilegeDataDto> privileges) {
		this.privileges = privileges;
	}
	
	
	
}
