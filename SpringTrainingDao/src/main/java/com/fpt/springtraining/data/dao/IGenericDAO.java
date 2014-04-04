package com.fpt.springtraining.data.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Contains all the generic functions 
 * 
 * @author ThaiTC
 *
 * @param <O>
 * @param <PK>
 */

public interface IGenericDAO<O, PK extends Serializable> {

	/**
	 * save a instance 
	 * 
	 * @param newInstance
	 * @return
	 */
	PK save(O newInstance);

	/**
	 * update an instance 
	 * 
	 * @param object
	 */
	void update(O object);

	/**
	 * delete an instance
	 * 
	 * @param object
	 */
	void delete(O object);

	/**
	 * find instance by id
	 * 
	 * @param id
	 * @return
	 */
	O findById(PK id);

	/**
	 * find all instances 
	 * 
	 * @return
	 */
	List<O> findAll();
}