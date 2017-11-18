package com.tjaktor.restshopping.security.filters;

import static com.tjaktor.restshopping.security.config.SecurityConstants.APPLICATION_SECRET;
import static com.tjaktor.restshopping.security.config.SecurityConstants.AUTHORIZATION_HEADER;
import static com.tjaktor.restshopping.security.config.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.tjaktor.restshopping.security.exception.CustomJwtException;
import com.tjaktor.restshopping.security.repository.ApplicationUserRepository;
import com.tjaktor.restshopping.security.user.ApplicationUser;
import com.tjaktor.restshopping.security.user.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.DefaultClaims;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private ApplicationUserRepository applicationUserRepository;
	
	public JWTAuthorizationFilter(AuthenticationManager authManager, ApplicationUserRepository applicationUserRepository) {
		super(authManager);
		this.applicationUserRepository = applicationUserRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req,
									HttpServletResponse res,
									FilterChain chain) throws IOException, ServletException {
		String header = req.getHeader(AUTHORIZATION_HEADER);

		if (null == header || ! header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		
		String token = request.getHeader(AUTHORIZATION_HEADER);
		
		if (null != token) {
			
			Claims claims = new DefaultClaims();
			
			try {
				claims = Jwts.parser().setSigningKey(APPLICATION_SECRET.getBytes()).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			} catch (SignatureException ex) {
				throw new CustomJwtException("Bad JWT signature.", ex);
			} catch (ExpiredJwtException ex) {
				throw new CustomJwtException("JWT expired.", ex);
			} catch (JwtException ex) {
				throw new CustomJwtException("JWT authentication error." , ex);
			}
			
			String user = claims.getSubject();
			
			if (null != user) {
				
				ApplicationUser currentUser = this.applicationUserRepository.findByEmail(user);
				
				Collection<? extends GrantedAuthority> roles = convertRolesToAuthorities(currentUser.getRoles());
				return new UsernamePasswordAuthenticationToken(currentUser, null, roles);
			}
			return null;
		}
		return null;
	}
	
	/**
	 * Make and return a list of authorities from a list of roles
	 * 
	 * @param roles
	 * @return
	 */
	private Collection<? extends GrantedAuthority> convertRolesToAuthorities(Collection<UserRole> roles) {
				
		Set<GrantedAuthority> userAuthorities = new HashSet<>();
		
		for (UserRole role : roles) {
			
			// Add roles to the set
			userAuthorities.add(new SimpleGrantedAuthority(role.getRolename().toUpperCase()));

			// Add privileges to the set
			role.getPrivileges().stream().forEach(privilege -> userAuthorities.add(new SimpleGrantedAuthority(privilege.getPrivilege().toUpperCase())));
		}
		return userAuthorities;
	}
}
