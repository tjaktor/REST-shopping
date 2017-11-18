package com.tjaktor.restshopping.api.v1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.tjaktor.restshopping.security.filters.JWTAuthenticationFilter;
import com.tjaktor.restshopping.security.filters.JWTAuthorizationFilter;
import com.tjaktor.restshopping.security.filters.SecurityExceptionHandler;
import com.tjaktor.restshopping.security.repository.ApplicationUserRepository;

/** 
 * The security configuration for the version 1 API ("/api/v1")
 */
@Configuration
@Order(2)
public class V1WebSecurityConfig extends WebSecurityConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(V1WebSecurityConfig.class);
	
	private static final String API_BASE_URL = "/api/v1";
	private ApplicationUserRepository applicationUserRepository;
	
	@Autowired
	public V1WebSecurityConfig(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.antMatcher(API_BASE_URL + "/**")
				.authorizeRequests()
				.antMatchers(API_BASE_URL + "/carts").hasAnyRole("USER", "ADMIN")
				.antMatchers(API_BASE_URL + "/admin/**").hasRole("ADMIN")
				.antMatchers(API_BASE_URL + "/**").hasAnyRole("USER", "ADMIN")
				.anyRequest().authenticated().and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), this.applicationUserRepository))
				.addFilterBefore(new SecurityExceptionHandler(), JWTAuthorizationFilter.class);
	}
}
