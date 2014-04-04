package com.fpt.springtraining.logic.serviceimpl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.dao.IGroupDAO;
import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.data.entities.AssUser;
import com.fpt.springtraining.exceptions.DuplicateObjectException;
import com.fpt.springtraining.logic.service.IGroupService;

@Transactional
public class GroupServiceImpl implements IGroupService{

	@Autowired 
	private IGroupDAO m_groupDAO;
	
	@Override
	public List<AssGroup> findGroupsByUser(AssUser user) {
		return m_groupDAO.findGroupsByUser(user);
	}
	
	public AssGroup findByName(String name) {
		return m_groupDAO.findByName(name);
	}

	@Override
	public String createGroup(String name, GroupType type) throws DuplicateObjectException {
		try {

			AssGroup group = new AssGroup();
			group.setName(name);
			group.setType(type.getValue());
			
			m_groupDAO.save(group);
			return group.getId();
			
		} catch (ConstraintViolationException ex) {
			Logger.getLogger(GroupServiceImpl.class.getCanonicalName()).info(ex.getSQLException().getSQLState());
				
			// 23505 for postgres, ORA-00001 for oracle
			if (ex.getSQLException().getErrorCode() == 23505 || ex.getSQLException().getSQLState().equals("ORA-00001")) {
				throw new DuplicateObjectException("User is upduplicated");
			}
			
			ex.printStackTrace();
			return null;
		
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(GroupServiceImpl.class.getCanonicalName()).info(ex.getMessage());
			return null;
		}
	}

	@Override
	public boolean updateGroup(AssGroup group) throws DuplicateObjectException {
		try {
			m_groupDAO.update(group);
			
			return true;
		} catch (ConstraintViolationException ex) {
			Logger.getLogger(GroupServiceImpl.class.getCanonicalName()).info(ex.getSQLException().getSQLState());
			
			// 23505 for postgres, ORA-00001 for oracle
			if (ex.getSQLException().getErrorCode() == 23505 || ex.getSQLException().getSQLState().equals("ORA-00001")) {
				throw new DuplicateObjectException("User is duplicated");
			}
			
			ex.printStackTrace();
			return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.getLogger(GroupServiceImpl.class.getCanonicalName()).info(ex.getMessage());
			return false;
		}
	}
}
