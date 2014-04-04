package com.fpt.springtraining.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.springtraining.config.DatabaseConfig;
import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.dao.IGroupDAO;
import com.fpt.springtraining.data.dao.IUserDAO;
import com.fpt.springtraining.data.daoimpl.GroupDAOImpl;
import com.fpt.springtraining.data.daoimpl.UserDAOImpl;
import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.data.entities.AssUser;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
// @TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@ContextConfiguration(classes = {
		GroupDAOImpl.class, 
		UserDAOImpl.class, 
		DatabaseConfig.class
})
public class TestGroupDAO {

	@Autowired
	protected IGroupDAO groupDAO;
	
	@Autowired
	protected IUserDAO userDAO;
	
	@BeforeTransaction
    public void beforeTransaction() {}
	
	@AfterTransaction
    public void afterTransaction() {}

	@Test
	public void testCreate() {
		AssGroup newGroup = new AssGroup();
		newGroup.setName("man1");
		newGroup.setType(GroupType.MANAGER.getValue());
		
		groupDAO.save(newGroup);
		
		assertNotNull(newGroup.getId());
	}	

	@Test
	public void testUpdate() {
		AssGroup newGroup = new AssGroup();
		newGroup.setName("man1");
		newGroup.setType(GroupType.MANAGER.getValue());
		
		groupDAO.save(newGroup);
		
		newGroup.setName("dev1");
		newGroup.setType(GroupType.DEVELOPER.getValue());
		
		groupDAO.save(newGroup);
		
		AssGroup group = (AssGroup) groupDAO.findById(newGroup.getId());
		
		// remember this compares the values of objects, not the objects
		assertEquals(group, newGroup); 
		assertEquals(GroupType.DEVELOPER.getValue(), group.getType());	
	}
	
	@Test
	public void testDelete() {
		AssGroup newGroup = new AssGroup();
		newGroup.setName("man1");
		newGroup.setType(GroupType.MANAGER.getValue());
		
		groupDAO.save(newGroup);
		groupDAO.delete(newGroup);
		
		AssGroup group = (AssGroup) groupDAO.findById(newGroup.getId());

		assertNull(group);
	}
	
	@Test
	public void testFindById() {
		AssGroup newGroup = new AssGroup();
		newGroup.setName("man1");
		newGroup.setType(GroupType.MANAGER.getValue());
		
		groupDAO.save(newGroup);
		
		AssGroup group = (AssGroup) groupDAO.findById(newGroup.getId());
		
		assertNotNull(group);
		assertEquals(GroupType.MANAGER.getValue(), group.getType());	
		assertEquals(group, newGroup);
	}
	
	@Test
	public void testFindAll() {
		AssGroup newGroup = new AssGroup();
		newGroup.setName("man1");
		newGroup.setType(GroupType.MANAGER.getValue());
		
		AssGroup newGroup1 = new AssGroup();
		newGroup1.setName("dev1");
		newGroup1.setType(GroupType.DEVELOPER.getValue());
		
		groupDAO.save(newGroup);
		groupDAO.save(newGroup1);
		
		assertEquals(2, groupDAO.findAll().size());
	}
	
	@Test
	public void testFindByType() {
		AssGroup newGroup = new AssGroup();
		newGroup.setName("man1");
		newGroup.setType(GroupType.MANAGER.getValue());
		
		AssGroup newGroup1 = new AssGroup();
		newGroup1.setName("dev1");
		newGroup1.setType(GroupType.DEVELOPER.getValue());		
		
		groupDAO.save(newGroup);
		groupDAO.save(newGroup1);
		
		assertEquals(2, groupDAO.findAll().size());
		List<AssGroup> found = groupDAO.findByType(GroupType.MANAGER.getValue());
		
		assertNotNull(found);
		assertEquals(1, found.size());
	}
	
	
	@Test
	public void testFindByTypes() {
		AssGroup newGroup = new AssGroup();
		newGroup.setName("man1");
		newGroup.setType(GroupType.MANAGER.getValue());
		
		AssGroup newGroup1 = new AssGroup();
		newGroup1.setName("dev1");
		newGroup1.setType(GroupType.DEVELOPER.getValue());		
		
		groupDAO.save(newGroup);
		groupDAO.save(newGroup1);
		List<AssGroup> groups = groupDAO.findByTypes(GroupType.MANAGER.getValue(), GroupType.DEVELOPER.getValue());
		
		assertNotNull(groups);
		assertEquals(2, groups.size());
	}
	
	@Test
	public void testFindGroupsByUser() {
		
		AssGroup group = new AssGroup();
		group.setName("man1");
		group.setType(GroupType.MANAGER.getValue());
		groupDAO.save(group);
		
		AssGroup saveGroup = groupDAO.findById(group.getId());
		List<AssGroup> groups = new ArrayList<AssGroup>();
		groups.add(saveGroup);
		
		AssUser user = new AssUser();
		user.setUsername("A");
		user.setPassword("A");
		
 		user.setGroups(groups);
 		userDAO.save(user);
 		
 		AssUser foundUser = userDAO.findById(user.getId());
 		List<AssGroup> foundGroups = groupDAO.findGroupsByUser(foundUser);
 		
 		assertNotNull(foundGroups);
 		assertEquals(1, foundGroups.size());
 		assertEquals(GroupType.MANAGER.getValue(), foundGroups.get(0).getType());
	}
	
	@Test
	public void testFindByName() {
		AssGroup group = new AssGroup();
		group.setName("man1");
		group.setType(GroupType.MANAGER.getValue());
		groupDAO.save(group);
		
		AssGroup found = groupDAO.findByName("man1");
		
		assertEquals("man1", found.getName());
	}
}


