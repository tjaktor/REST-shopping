package com.tjaktor.restshopping.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tjaktor.restshopping.security.repository.ApplicationUserRepository;
import com.tjaktor.restshopping.security.user.ApplicationUser;
import com.tjaktor.restshopping.security.user.ShoppingUserDetails;

@Service
public class DatabaseUserdetailsService implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(DatabaseUserdetailsService.class);
	
	private ApplicationUserRepository applicationUserRepository;
	
	@Autowired
	public DatabaseUserdetailsService(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		ApplicationUser user = this.applicationUserRepository.findByEmail(email);
		
		if (null == user) {
			throw new UsernameNotFoundException("User not found: " + email);
		}
		
		if (user.getRoles().isEmpty()) {
			throw new UsernameNotFoundException("User "+ user.getEmail() +" has no roles or authorities.");
		}
		
		return new ShoppingUserDetails(user);
	}
}
