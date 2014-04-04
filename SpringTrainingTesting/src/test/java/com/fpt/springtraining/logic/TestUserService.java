package com.fpt.springtraining.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.springtraining.config.DatabaseConfig;
import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.daoimpl.GroupDAOImpl;
import com.fpt.springtraining.data.daoimpl.UserDAOImpl;
import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.data.entities.AssUser;
import com.fpt.springtraining.dto.UserLogin;
import com.fpt.springtraining.dto.UserRegistration;
import com.fpt.springtraining.exceptions.DuplicateObjectException;
import com.fpt.springtraining.exceptions.FailAuthenticationException;
import com.fpt.springtraining.logic.service.IGroupService;
import com.fpt.springtraining.logic.service.IUserService;
import com.fpt.springtraining.logic.serviceimpl.GroupServiceImpl;
import com.fpt.springtraining.logic.serviceimpl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
// @TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@ContextConfiguration(classes={
	UserServiceImpl.class, 
	GroupServiceImpl.class,
	UserDAOImpl.class, 
	GroupDAOImpl.class, 
	DatabaseConfig.class
})
public class TestUserService {
    
	@Autowired
    IUserService userService;
	
	@Autowired
	IGroupService groupService;
	
    @Before
    public void setup() {
    }
    
    @Test
    public void testCreateUser() throws DuplicateObjectException {
    	UserRegistration registration = new UserRegistration("A", "A", true, new String [][] {{"man1", GroupType.MANAGER.getValue()}});
    	String id = userService.createUser(registration);
    	
    	assertNotNull(id);
    	
    	try {
    		UserRegistration registration1 = new UserRegistration("A", "A", true, new String [][] {{"man1", GroupType.MANAGER.getValue()}});
    		String id1 = userService.createUser(registration1);
    	} catch (Exception ex) {
        	assertEquals("User is duplicated", ex.getMessage());
    	}
    }
    
    @Test
    public void testCreateAndLogin() throws FailAuthenticationException, DuplicateObjectException {
    	UserRegistration registration = new UserRegistration("A", "A", true, new String [][] {{"man1", GroupType.MANAGER.getValue()}});
    	userService.createUser(registration);
    	
    	UserLogin login = new UserLogin("A", "A");
    	AssUser user = userService.login(login);
    	
    	assertNotNull(user);
    	
    	List<AssGroup> groups = groupService.findGroupsByUser(user);
    	
    	assertNotNull(groups);
    	assertEquals(GroupType.MANAGER.getValue(), groups.get(0).getType());
    }
    
    @Test
    public void testDisplayUsers() throws DuplicateObjectException {
    	
    	UserRegistration registration = new UserRegistration("A", "A", true, new String [][] {{"man1", GroupType.MANAGER.getValue()}});
    	UserRegistration registration1 = new UserRegistration("B", "A", true, new String [][] {{"dev1", GroupType.DEVELOPER.getValue()}});
    	
    	userService.createUser(registration1);
    	userService.createUser(registration);
    	
    	assertEquals(1, userService.displayUsers(GroupType.DEVELOPER.getValue()).size());
    	assertEquals(1, userService.displayUsers(GroupType.MANAGER.getValue()).size());
    }
    
    @Test
    public void testDisplayDevs()  throws DuplicateObjectException {
    	UserRegistration registration = new UserRegistration("A", "A", true, new String [][] {{"man1", GroupType.MANAGER.getValue()}});
    	UserRegistration registration1 = new UserRegistration("B", "A", true, new String [][] {{"dev1", GroupType.DEVELOPER.getValue()}});
    	
    	userService.createUser(registration1);
    	userService.createUser(registration);
    	
    	// assertEquals(2, userService.displayUsers().size());

    
    }
    
    @Test
    public void testFindByUsername() throws DuplicateObjectException {
    	UserRegistration registration = new UserRegistration("A", "A", true, new String [][] {{"man1", GroupType.MANAGER.getValue()}});
    	userService.createUser(registration);
    	
    
    	AssUser user = userService.findByUsername("A");
    	assertNotNull(user);
    	assertEquals("A", user.getUsername());
    }
}