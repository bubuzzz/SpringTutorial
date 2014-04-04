package com.fpt.springtraining.data.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.data.entities.AssUser;

/**
 * Contains functions relating to groups
 * 
 * @author ThaiTC
 *
 */
@Component
public interface IGroupDAO extends IGenericDAO<AssGroup, String>  {
	/**
	 * find a group by type 
	 * 
	 * @param type
	 * @return
	 */
	public List<AssGroup> findByType(String type);
	
	/**
	 * find groups by many types
	 * 
	 * @param types
	 * @return
	 */
	public List<AssGroup> findByTypes(String ... types);
	
	/**
	 * find groups of a user
	 * 
	 * @param user
	 * @return
	 */
	public List<AssGroup> findGroupsByUser(AssUser user);
	
	/**
	 * find a group by its name
	 * 
	 * @param name
	 * @return
	 */
	public AssGroup findByName(String name);
}
