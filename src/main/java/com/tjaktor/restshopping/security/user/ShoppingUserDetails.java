package com.tjaktor.restshopping.security.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ShoppingUserDetails implements UserDetails, Serializable {

	private static final long serialVersionUID = 7535671988190341109L;

	private String username;
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean locked;
	
	public ShoppingUserDetails(ApplicationUser user) {
		this.username = user.getUsername();
		this.password = user.getPassword();	
		this.email = user.getEmail();
		this.authorities = setAuthorities(user.getRoles());
		this.locked = setLockedStatus(user.isLocked());
	}
	
	private boolean setLockedStatus(boolean locked) {
		return (locked == false) ? true : false;
	}

	private Collection<? extends GrantedAuthority> setAuthorities(Set<UserRole> roles) {
		
		Set<GrantedAuthority> userAuthorities = new HashSet<>();
				
		for (UserRole role : roles) {
			
			// Add roles to the set
			userAuthorities.add(new SimpleGrantedAuthority(role.getRolename().toUpperCase()));

			// Add privileges to the set
			role.getPrivileges().stream().forEach(privilege -> userAuthorities.add(new SimpleGrantedAuthority(privilege.getPrivilege().toUpperCase())));
		}
		
		return userAuthorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
