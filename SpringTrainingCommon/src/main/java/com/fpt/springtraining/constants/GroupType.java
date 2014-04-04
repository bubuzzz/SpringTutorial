package com.fpt.springtraining.constants;

/**
 * Contains type of groups
 * 
 * @author ThaiTC
 *
 */
public enum GroupType {
	MANAGER("MANAGER"),
	DEVELOPER("DEVELOPER");
	
	String value; 
	
	/**
	 * Constructor
	 * 
	 * @param value
	 */
	GroupType(String value ) {
		this.value = value;
	}
	
	/**
	 * Return value of the enumeration
	 * 
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
}