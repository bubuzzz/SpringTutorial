package com.fpt.springtraining.constants;

/**
 * Contains right constants of the system
 * 
 * @author ThaiTC
 *
 */
public enum RightType {
	CREATE("CREATE"), UPDATE("UPDATE"), DELETE("DELETE"), VIEW("VIEW");

	RightType(String value) {
		this.value = value;
	}

	private String value;

	/**
	 * Return value of the current enumeration
	 * 
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
}
