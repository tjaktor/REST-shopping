package com.tjaktor.restshopping.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjaktor.restshopping.api.v1.exception.ApiError;
import com.tjaktor.restshopping.security.exception.CustomJwtException;

public class SecurityExceptionHandler extends OncePerRequestFilter {

	Logger logger = LoggerFactory.getLogger(SecurityExceptionHandler.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			filterChain.doFilter(request, response);
		} catch (CustomJwtException ex) {
			setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
		} catch (Exception ex) {
			setErrorResponse(HttpStatus.BAD_REQUEST, response, ex);
		}
	}
	
	private void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) {
		response.setStatus(status.value());
		response.setContentType("application/json");

		ApiError apiError = new ApiError(status, ex.getMessage());

		try {
			ObjectMapper mapper = new ObjectMapper();
			String errorJson = mapper.writeValueAsString(apiError);
			response.getWriter().write(errorJson);
		} catch (IOException ioEx) {
			logger.warn(ex.getMessage());
		}
	}

}
