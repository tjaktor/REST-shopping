package com.tjaktor.restshopping.core.exception;

public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = 6669573198272299170L;

	public UserServiceException() {
		super();
	}

	public UserServiceException(String message) {
		super(message);
	}

	public UserServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
