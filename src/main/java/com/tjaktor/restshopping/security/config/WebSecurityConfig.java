package com.tjaktor.restshopping.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tjaktor.restshopping.security.filters.JWTAuthenticationFilter;
import com.tjaktor.restshopping.security.filters.JWTAuthorizationFilter;
import com.tjaktor.restshopping.security.filters.SecurityExceptionHandler;
import com.tjaktor.restshopping.security.repository.ApplicationUserRepository;

/**
 * The login point of the application
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	private ApplicationUserRepository applicationUserRepository;
	private PasswordEncoder passwordEncoder;
		
	@Autowired
	public WebSecurityConfig(UserDetailsService userDetailsService, 
			ApplicationUserRepository applicationUserRepository,
								PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.applicationUserRepository = applicationUserRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.antMatcher("/api/authentication/login")
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/authentication/login").permitAll()
				.anyRequest().authenticated().and()
				.addFilter(getJWTAuthenticationFilter())
				.addFilter(new JWTAuthorizationFilter(authenticationManager(), this.applicationUserRepository))
				.addFilterBefore(new SecurityExceptionHandler(), JWTAuthorizationFilter.class);
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}
	
	// Change the login path
	public JWTAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
		final JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/api/authentication/login");
		return filter;
	}
	
	
	/**
	 * 	Only authenticated users may connect to the application
	 */
	@Configuration
	public class AuthorizedOnlyConfig extends WebSecurityConfigurerAdapter {
		
		private ApplicationUserRepository applicationUserRepository;
		
		@Autowired
		public AuthorizedOnlyConfig(ApplicationUserRepository applicationUserRepository) {
			this.applicationUserRepository = applicationUserRepository;
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {

			http
				.antMatcher("/**")
					.authorizeRequests()
					.anyRequest().authenticated().and()
					.addFilter(new JWTAuthenticationFilter(authenticationManager()))
					.addFilter(new JWTAuthorizationFilter(authenticationManager(), this.applicationUserRepository))
					.addFilterBefore(new SecurityExceptionHandler(), JWTAuthorizationFilter.class);
		}	
	}
}
