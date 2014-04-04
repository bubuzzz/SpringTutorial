package com.fpt.springtraining.logic;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.springtraining.config.DatabaseConfig;
import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.daoimpl.GroupDAOImpl;
import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.exceptions.DuplicateObjectException;
import com.fpt.springtraining.logic.service.IGroupService;
import com.fpt.springtraining.logic.serviceimpl.GroupServiceImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
// @TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@ContextConfiguration(classes={
	GroupServiceImpl.class,
	GroupDAOImpl.class, 
	DatabaseConfig.class
})
public class TestGroupService {
	@Autowired
	private IGroupService groupService;
	
	
	@Test
	public void testCreateGroup() throws DuplicateObjectException{
		
		groupService.createGroup("g1", GroupType.MANAGER);
		
		AssGroup group = groupService.findByName("g1");
		assertNotNull(group);
		assertEquals("g1", group.getName());
	}
	
}
