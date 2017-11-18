package com.tjaktor.restshopping.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.tjaktor.restshopping.security.filters.JWTAuthenticationFilter;
import com.tjaktor.restshopping.security.filters.JWTAuthorizationFilter;
import com.tjaktor.restshopping.security.filters.SecurityExceptionHandler;
import com.tjaktor.restshopping.security.repository.ApplicationUserRepository;

@Configuration
@Order(99)
@Profile("development")
public class DevelopmentConfig extends WebSecurityConfigurerAdapter {

	Logger logger = LoggerFactory.getLogger(DevelopmentConfig.class);

	private ApplicationUserRepository applicationUserRepository;
	
	@Autowired
	public DevelopmentConfig(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			// Fix console not showing
			.headers().frameOptions().disable().and()
			.antMatcher("/console/**")
				.authorizeRequests()
				.anyRequest().permitAll().and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), this.applicationUserRepository))
				.addFilterBefore(new SecurityExceptionHandler(), JWTAuthorizationFilter.class);
		
			logger.info("Development mode up and running. Database console enabled.");
	}	
}
