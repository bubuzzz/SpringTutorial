package com.fpt.springtraining.exceptions;

/**
 * Exception when having wrong access
 * 
 * @author ThaiTC
 *
 */
public class FailAuthenticationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public FailAuthenticationException() {
		super("You need to log in to the system before processing");
	}
}
