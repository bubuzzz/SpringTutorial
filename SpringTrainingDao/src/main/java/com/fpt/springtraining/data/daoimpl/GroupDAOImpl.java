package com.fpt.springtraining.data.daoimpl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.fpt.springtraining.data.dao.IGroupDAO;
import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.data.entities.AssUser;

@Repository
public class GroupDAOImpl extends GenericDAOImpl<AssGroup, String> implements IGroupDAO {
	
	@SuppressWarnings("unchecked")
	public List<AssGroup> findByType (String type) {
		return (List<AssGroup>) getSession().createCriteria(
			AssGroup.class
		).add(
			Restrictions.like("m_type", type)
		).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<AssGroup> findByTypes(String ... types) {
		
		return getSession().createCriteria(
			AssGroup.class
		).add(
			Restrictions.in("m_type", types)
		).list();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AssGroup> findGroupsByUser(AssUser user) {
		String hql = String.format(
			"SELECT DISTINCT G FROM AssGroup G join G.m_users U where U.id = '%s'", 
			user.getId()
		);
		
		return getSession().createQuery(hql).list();	
	}
	
	public AssGroup findByName(String name) {
		return (AssGroup) getSession().createCriteria(
			AssGroup.class
		).add(
			Restrictions.eq("m_name", name)
		).uniqueResult();
	}	
}
