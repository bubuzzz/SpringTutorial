package com.fpt.springtraining.data.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.entities.AssUser;

/**
 * Contains function relating to user 
 *  
 * @author ThaiTC
 *
 */
@Component
public interface IUserDAO extends IGenericDAO<AssUser, String>{
	
	/**
	 * Find a user by username, password 
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public AssUser findUserByAuthentication(String username, String password);
	
	/**
	 * find users based on group type
	 * 
	 * @param type
	 * @return
	 */
	public List<AssUser> findUsersByGroupType(GroupType type);
	
	/**
	 * find devs of a pm
	 * 
	 * @param user
	 * @return
	 */
	public List<AssUser> findDevsByPM(AssUser user);
	
	/**
	 * find pm of a dev
	 * 
	 * @param user
	 * @return
	 */
	public AssUser findPMByDev(AssUser user);
	
	/**
	 * find user by username 
	 * 
	 * @param username
	 * @return
	 */
	public AssUser findByUsername(String username);
	
	/**
	 * find users by usernames
	 * 
	 * @param userNames
	 * @return
	 */
	public List<AssUser> findByUsernames(String ...userNames);
}
