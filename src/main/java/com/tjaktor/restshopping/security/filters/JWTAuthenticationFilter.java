package com.tjaktor.restshopping.security.filters;

import static com.tjaktor.restshopping.security.config.SecurityConstants.APPLICATION_SECRET;
import static com.tjaktor.restshopping.security.config.SecurityConstants.AUTHORIZATION_HEADER;
import static com.tjaktor.restshopping.security.config.SecurityConstants.TOKEN_EXPIRATION_TIME;
import static com.tjaktor.restshopping.security.config.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjaktor.restshopping.security.user.ApplicationUser;
import com.tjaktor.restshopping.security.user.ShoppingUserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
												HttpServletResponse res) throws AuthenticationException {
		try {
			ApplicationUser creds = new ObjectMapper()
                    .readValue(req.getInputStream(), ApplicationUser.class);

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getUsername(),
							creds.getPassword(),
							new ArrayList<>())
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req,
											HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {
		// String username = ((ShoppingUserDetails) auth.getPrincipal()).getUsername();
		String email = ((ShoppingUserDetails) auth.getPrincipal()).getEmail();
		String token = Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, APPLICATION_SECRET.getBytes())
				.compact();
		res.addHeader(AUTHORIZATION_HEADER, TOKEN_PREFIX + " " + token);
	}
	
	
}
