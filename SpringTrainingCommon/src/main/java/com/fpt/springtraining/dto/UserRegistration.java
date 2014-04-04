package com.fpt.springtraining.dto;

/**
 * holding data transferred from controller to service for registration 
 * 
 * @author ThaiTC
 *
 */
public class UserRegistration {
	
	private String m_username;
	private String m_password;
	private String[][] m_role;
	private boolean m_agreed;
	
	private boolean m_isAdmin; 

	/**
	 * Main constructor 
	 * 
	 * @param username
	 * @param password
	 * @param agreed
	 * @param role
	 */
	public UserRegistration(String username, String password, boolean agreed, String [][] role ) {
		this.m_username = username;
		this.m_password = password;
		this.m_role 	= role;
		this.m_agreed 	= agreed;
	}
	
	/**
	 * get username
	 * 
	 * @return
	 */
	public String getUsername() {
		return m_username;
	}

	/**
	 * set username 
	 * 
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

	/**
	 * get roles
	 * 
	 * @return
	 */
	public String[][] getRoles() {
		return m_role;
	}

	/**
	 * set role 
	 * 
	 * @param role
	 */
	public void setRoles(String[][] role) {
		this.m_role = role;
	}

	/**
	 * set term of use agreement
	 * 
	 * @return
	 */
	public boolean isAgreed() {
		return m_agreed;
	}

	/**
	 * get term of use agreement
	 * @param agreed
	 */
	public void setAgreed(boolean agreed) {
		this.m_agreed = agreed;
	}

	/**
	 * check current user whether is super admin
	 * @return
	 */
	public boolean isAdmin() {
		return m_isAdmin;
	}

	/**
	 * set super admin right to current user
	 * 
	 * @param isAdmin
	 */
	public void setAdmin(boolean isAdmin) {
		this.m_isAdmin = isAdmin;
	}

}
