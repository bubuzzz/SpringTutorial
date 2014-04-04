package com.fpt.springtraining.exceptions;

/**
 * Exception when a object is saved duplicatedly
 * @author ThaiTC
 *
 */
public class DuplicateObjectException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Main constructor
	 * 
	 * @param msg
	 */
	public DuplicateObjectException(String msg) {
		super(msg);
	}

}
