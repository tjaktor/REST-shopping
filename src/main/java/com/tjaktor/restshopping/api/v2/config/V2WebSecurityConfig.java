package com.tjaktor.restshopping.api.v2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.tjaktor.restshopping.security.filters.JWTAuthenticationFilter;
import com.tjaktor.restshopping.security.filters.JWTAuthorizationFilter;
import com.tjaktor.restshopping.security.repository.ApplicationUserRepository;

/** 
 * The security configuration for the version 2 API ("/api/v2")
 */
@Configuration
@Order(3)
public class V2WebSecurityConfig extends WebSecurityConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(V2WebSecurityConfig.class);
	
	private static final String API_BASE_URL = "/api/v2";
	private ApplicationUserRepository applicationUserRepository;
	
	@Autowired
	public V2WebSecurityConfig(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher(API_BASE_URL + "/**")
				.authorizeRequests()
				.antMatchers(API_BASE_URL + "/admin/**").hasRole("ADMIN")
				.antMatchers(API_BASE_URL + "/**").hasRole("USER")
				.anyRequest().authenticated().and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), this.applicationUserRepository));
	}
}
