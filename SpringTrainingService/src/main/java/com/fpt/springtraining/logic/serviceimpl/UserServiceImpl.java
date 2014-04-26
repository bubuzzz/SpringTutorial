package com.fpt.springtraining.logic.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.dao.IGroupDAO;
import com.fpt.springtraining.data.dao.IUserDAO;
import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.data.entities.AssUser;
import com.fpt.springtraining.dto.UserLogin;
import com.fpt.springtraining.dto.UserRegistration;
import com.fpt.springtraining.exceptions.DuplicateObjectException;
import com.fpt.springtraining.exceptions.FailAuthenticationException;
import com.fpt.springtraining.logic.service.IUserService;

@Transactional
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDAO m_userDAO;
	
	@Autowired
	private IGroupDAO m_groupDAO;
	
	public String createUser(UserRegistration registration) throws DuplicateObjectException {
		
		try {
			AssUser user = new AssUser();
			
			user.setUsername(registration.getUsername());
			user.setPassword(registration.getPassword());
			user.setAdmin(registration.isAdmin() == true ? 1 : 0);
			
			m_userDAO.save(user);
			
			List<AssUser> users = new ArrayList<AssUser>();
			users.add(user);
			
			for (String[] role : registration.getRoles()) {
				AssGroup group = m_groupDAO.findByName(role[0]);
				
				if (group == null || !group.getType().equals(role[1])) {
					group = new AssGroup();
					group.setName(role[0]);
					group.setType(role[1]);
				}
				
				if (group.getUsers() != null) {
					users.addAll(group.getUsers());
				}
				
				group.setUsers(users);
				m_groupDAO.update(group);
			}
		
			return user.getId();
		
		} catch (ConstraintViolationException ex) {
			Logger.getLogger(UserServiceImpl.class.getCanonicalName()).info(ex.getSQLException().getSQLState());
			
			// 23505 for postgres, ORA-00001 for oracle
			if (ex.getSQLException().getErrorCode() == 23505 ||
					ex.getSQLException().getErrorCode() == 1
					//ex.getSQLException().getSQLState().equals("ORA-00001")
				) {
				throw new DuplicateObjectException("User is duplicated");
			}
			
			ex.printStackTrace();
			return null;

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(UserServiceImpl.class.getCanonicalName()).info(ex.getMessage());
			return null;
		}
	}

	public boolean deleteUser(String id) {
		AssUser user = m_userDAO.findById(id);
		
		if (user != null) {
			m_userDAO.delete(user);
			return true;
		}
		else {
			return false;
		}
	
	}
	
	public AssUser findByUsername(String username) {
		return m_userDAO.findByUsername(username);
	}
	
	public List<AssUser> findByUsernames(String ... userNames) {
		return m_userDAO.findByUsernames(userNames);
	}

	public List<AssUser> displayUsers(String groupType) {
		
		if (groupType == null) {
			return m_userDAO.findAll();
		}
		
		GroupType selected = null;
		for (GroupType type : GroupType.values()) {
			if (type.getValue().equals(groupType)) {
				selected = type;
			}
		}
		
		return m_userDAO.findUsersByGroupType(selected);
	}
	
	public List<AssUser> displayDevs(AssUser user) {
		return m_userDAO.findDevsByPM(user);
	}
 
	public boolean updateUser(AssUser user) throws DuplicateObjectException {
		
		try {
			m_userDAO.update(user);
			
			return true;
		} catch (ConstraintViolationException ex) {
			Logger.getLogger(UserServiceImpl.class.getCanonicalName()).info(ex.getSQLException().getSQLState());
			
			// 23505 for postgres, ORA-00001 for oracle
			if (ex.getSQLException().getErrorCode() == 23505 || ex.getSQLException().getSQLState().equals("ORA-00001")) {
				throw new DuplicateObjectException("User is duplicated");
			}
			
			ex.printStackTrace();
			return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(UserServiceImpl.class.getCanonicalName()).info(ex.getMessage());
			return false;
		}
		
	}

	public AssUser login(UserLogin login) throws FailAuthenticationException {
		AssUser user = m_userDAO.findUserByAuthentication(login.getUsername(), login.getPassword());
		
		if (user == null) {
			throw new FailAuthenticationException();
		} else {
			return user;
		}
	}
}
