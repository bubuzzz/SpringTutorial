package com.fpt.springtraining.logic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fpt.springtraining.data.entities.AssUser;
import com.fpt.springtraining.dto.UserLogin;
import com.fpt.springtraining.dto.UserRegistration;
import com.fpt.springtraining.exceptions.DuplicateObjectException;
import com.fpt.springtraining.exceptions.FailAuthenticationException;

/**
 * Contains functions relating to user service
 * 
 * @author ThaiTC
 *
 */
@Service
public interface IUserService {
	
	/**
	 * Create a user 
	 * 
	 * @param registration
	 * @return
	 * @throws DuplicateObjectException
	 */
	public String createUser(UserRegistration registration) throws DuplicateObjectException;

	/**
	 * Delete a user 
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteUser(String id);

	/**
	 * Display users based on group type
	 *  
	 * @param groupType
	 * @return
	 */
	public List<AssUser> displayUsers(String groupType);
	
	/**
	 * Display devs of a pm
	 *  
	 * @param user
	 * @return
	 */
	public List<AssUser> displayDevs(AssUser user);

	/**
	 * find a user based on username 
	 * 
	 * @param username
	 * @return
	 */
	public AssUser findByUsername(String username);
	
	/**
	 * find users based on a list of usernames
	 * 
	 * @param userNames
	 * @return
	 */
	public List<AssUser> findByUsernames(String... userNames);
	
	/**
	 * update a user 
	 * 
	 * @param user
	 * @return
	 * @throws DuplicateObjectException
	 */
	public boolean updateUser(AssUser user) throws DuplicateObjectException;

	/**
	 * find a user based on authentication
	 *  
	 * @param login
	 * @return
	 * @throws FailAuthenticationException
	 */
	public AssUser login(UserLogin login) throws FailAuthenticationException;

}
