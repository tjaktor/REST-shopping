package com.tjaktor.restshopping.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tjaktor.restshopping.security.user.UserRole;

@Repository
public interface ApplicationUserRoleRepository extends JpaRepository<UserRole, Long> {

	// Find by role's "name"
 	public UserRole findByRolename(String role);
	
}
