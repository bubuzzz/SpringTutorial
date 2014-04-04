package com.fpt.springtraining.logic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.data.entities.AssUser;
import com.fpt.springtraining.exceptions.DuplicateObjectException;

/**
 * Contains functions relating to group service
 * 
 * @author ThaiTC
 *
 */
@Service
public interface IGroupService {
	
	/**
	 * Create group 
	 * 
	 * @param name
	 * @param type
	 * @return
	 * @throws DuplicateObjectException
	 */
	public String createGroup(String name, GroupType type) throws DuplicateObjectException;
	
	/**
	 * Find groups of a user
	 * 
	 * @param user
	 * @return
	 */
	public List<AssGroup> findGroupsByUser(AssUser user);
	
	/**
	 * find a group based on name 
	 * 
	 * @param name
	 * @return
	 */
	public AssGroup findByName(String name);

	/**
	 * update a group
	 * 
	 * @param group
	 * @return
	 * @throws DuplicateObjectException
	 */
	public boolean updateGroup(AssGroup group) throws DuplicateObjectException;
 
}
