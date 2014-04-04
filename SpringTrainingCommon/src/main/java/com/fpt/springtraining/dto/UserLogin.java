package com.fpt.springtraining.dto;

/**
 * Holding data transfer from controller to service when user login
 * 
 * @author ThaiTC
 *
 */
public class UserLogin {
	
	private String m_username; 
	private String m_password;
	
	/**
	 * Main constructor
	 * 
	 * @param username
	 * @param password
	 */
	public UserLogin(String username, String password) {
		this.m_username = username;
		this.m_password = password;
	}
	
	/**
	 * get username
	 * @return
	 */
	public String getUsername() {
		return m_username;
	}
	
	/**
	 * set username
	 * @param username
	 */
	public void setUsername(String username) {
		this.m_username = username;
	}
	
	/**
	 * get password
	 * 
	 * @return
	 */
	public String getPassword() {
		return m_password;
	}
	
	/**
	 * set password 
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.m_password = password;
	}
}
