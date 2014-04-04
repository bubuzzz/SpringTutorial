package com.fpt.springtraining.data.daoimpl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.dao.IGroupDAO;
import com.fpt.springtraining.data.dao.IUserDAO;
import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.data.entities.AssUser;

@Repository
public class UserDAOImpl extends GenericDAOImpl<AssUser, String> implements IUserDAO {

	@Autowired
	private IGroupDAO m_groupDAO;
	
	public AssUser findUserByAuthentication (String username, String password) {
		
		return (AssUser) getSession().createCriteria(
			AssUser.class
		).add(
			Restrictions.eq("m_username", username)
		).add(
			Restrictions.eq("m_password", password)
		).uniqueResult();		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<AssUser> findUsersByGroupType(GroupType type) {
		
		return getSession().createCriteria(
			AssUser.class
		).createAlias(
			"m_groups", "groups"
		).add(
			Restrictions.eq("groups.m_type", type.getValue()
		)).list();
	}
	
	private boolean isPM(List<AssGroup> groups) {
		for (AssGroup group : groups) {
			if (group.getType().equals(GroupType.MANAGER.getValue())) {
				return true;
			}
		}
		
		return false;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<AssUser> findDevsByPM(AssUser user) {
		
		// check group if the input user is a PM
		List<AssGroup> groups = m_groupDAO.findGroupsByUser(user);
		
		if (!isPM(groups)) {
			return null;
		}
	
		List<AssUser> foundDevs = getSession().createCriteria(
			AssUser.class
		).createAlias(
			"m_user", "pm"
		).add(
			Restrictions.eq("pm.m_id", user.getId())
		).list();
		
		return foundDevs;
		
	}
	
	public AssUser findPMByDev(AssUser user) {
		return null;
	}


	@Override
	public AssUser findByUsername(String username) {

		return (AssUser) getSession().createCriteria(
			AssUser.class
		).add(
			Restrictions.eq("m_username", username)
		).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<AssUser> findByUsernames(String ... usernames) {
		
		return (List<AssUser>) getSession().createCriteria(
			AssUser.class
		).add(
			Restrictions.in("m_username", usernames)
		).list();		
	}
}
