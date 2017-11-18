package com.tjaktor.restshopping.security.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.tjaktor.restshopping.security.user.ApplicationUser;

@Repository
public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

	/**
	 * Find a user by username
	 * 
	 * @param username
	 * @return ApplicationUser
	 */
	public ApplicationUser findByUsername(String username);
	
	/**
	 * Find a user by email address
	 * 
	 * @param email
	 * @return ApplicationUser
	 */
	public ApplicationUser findByEmail(String email);
	
	/**
	 * Find a user by an ID
	 * 
	 * @param userId
	 * @return
	 */
	public ApplicationUser findById(Long userId);
	
}
