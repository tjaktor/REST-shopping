package com.tjaktor.restshopping.security.exception;

public class CustomJwtException extends RuntimeException {

	private static final long serialVersionUID = -4257169696873619961L;

	public CustomJwtException(String message) {
		super(message);
	}
	
	public CustomJwtException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
