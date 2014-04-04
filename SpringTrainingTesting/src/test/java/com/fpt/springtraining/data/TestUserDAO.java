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
import org.springframework.transaction.annotation.Transactional;

import com.fpt.springtraining.config.DatabaseConfig;
import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.dao.IGroupDAO;
import com.fpt.springtraining.data.dao.IUserDAO;
import com.fpt.springtraining.data.daoimpl.GroupDAOImpl;
import com.fpt.springtraining.data.daoimpl.RightDAOImpl;
import com.fpt.springtraining.data.daoimpl.UserDAOImpl;
import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.data.entities.AssUser;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
// @TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@ContextConfiguration(classes = { UserDAOImpl.class, RightDAOImpl.class, GroupDAOImpl.class, DatabaseConfig.class })
public class TestUserDAO {

	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private IGroupDAO groupDAO;

	@Test
	public void testSave() {
		AssUser user = new AssUser();

		user.setUsername("A");
		user.setPassword("A");

		userDAO.save(user);

		assertNotNull(user.getId());
	}

	@Test
	public void testUpdate() {
		AssUser user = new AssUser();

		user.setUsername("A");
		user.setPassword("A");
		userDAO.save(user);

		assertNotNull(user.getId());

		user.setPassword("B");
		userDAO.update(user);

		AssUser oldUser = userDAO.findById(user.getId());
		assertEquals(oldUser.getPassword(), "B");
	}

	@Test
	public void testDelete() {
		AssUser user = new AssUser();

		user.setUsername("A");
		user.setPassword("A");
		userDAO.save(user);

		assertNotNull(user.getId());
		userDAO.delete(user);

		AssUser oldUser = userDAO.findById(user.getId());

		assertNull(oldUser);
	}

	@Test
	public void testFindAll() {
		AssUser user = new AssUser();
		user.setUsername("A");
		user.setPassword("A");

		AssUser user1 = new AssUser();
		user1.setUsername("B");
		user1.setPassword("A");

		AssUser user2 = new AssUser();
		user2.setUsername("C");
		user2.setPassword("A");

		AssUser user3 = new AssUser();
		user3.setUsername("D");
		user3.setPassword("A");

		userDAO.save(user);
		userDAO.save(user1);
		userDAO.save(user2);
		userDAO.save(user3);

		assertEquals(4, userDAO.findAll().size());
	}
	
	@Test
	public void testFindUserByAuthentication() {
		AssUser user = new AssUser();
		
		user.setUsername("A");
		user.setPassword("A");
		
		userDAO.save(user);
		
		AssUser loginUser = userDAO.findUserByAuthentication("A", "A");
		
		assertNotNull(loginUser);
	}

	@Test
	public void testFindUsersByGroupType() {
		
		AssGroup g1 = new AssGroup();
		g1.setName("man1");
		g1.setType(GroupType.MANAGER.getValue());
	
		AssGroup g2 = new AssGroup();
		g2.setName("dev1");
		g2.setType(GroupType.DEVELOPER.getValue());
		
		AssUser user = new AssUser();
		user.setUsername("A");
		user.setPassword("A");
		
		List<AssUser> users = new ArrayList<AssUser>();
		users.add(user);
		g1.setUsers(users);
		
		AssUser user1 = new AssUser();
		user1.setUsername("B");
		user1.setPassword("A");
		
		AssUser user2 = new AssUser();
		user1.setUsername("C");
		user1.setPassword("A");
		
		List<AssUser> users1 = new ArrayList<AssUser>();
		users1.add(user1);
		users1.add(user2);
		g2.setUsers(users1);
	
		groupDAO.save(g1);
		groupDAO.save(g2);
		
		
		List<AssUser> mans = userDAO.findUsersByGroupType(GroupType.MANAGER);
		List<AssUser> devs = userDAO.findUsersByGroupType(GroupType.DEVELOPER);
		
		assertNotNull(mans);
		assertNotNull(devs);
		
		assertEquals(1, mans.size());
		assertEquals("A", mans.get(0).getUsername());
		assertEquals(2, devs.size());
	}
	
	@Test
	public void testFindDevsByPM() {
		AssGroup g1 = new AssGroup();
		g1.setName("man1");
		g1.setType(GroupType.MANAGER.getValue());
	
		AssGroup g2 = new AssGroup();
		g2.setName("dev1");
		g2.setType(GroupType.DEVELOPER.getValue());
		
		AssUser user1 = new AssUser();
		user1.setUsername("A");
		user1.setPassword("A");
		
		List<AssUser> users = new ArrayList<AssUser>();
		users.add(user1);
		g1.setUsers(users);
		
		AssUser user2 = new AssUser();
		user2.setUsername("B");
		user2.setPassword("A");
		
		AssUser user3 = new AssUser();
		user3.setUsername("C");
		user3.setPassword("A");
		
		List<AssUser> users1 = new ArrayList<AssUser>();
		users1.add(user1);
		users1.add(user2);
		users1.add(user3);
		g2.setUsers(users1);
		
		List<AssUser> addedDevs = new ArrayList<AssUser>();
		addedDevs.add(user2);
		addedDevs.add(user3);
		
		user2.setUser(user1);
		user3.setUser(user1);
		
		userDAO.save(user2);
		userDAO.save(user3);
		
		userDAO.save(user1);
	
		groupDAO.save(g1);
		groupDAO.save(g2);
		
		List<AssUser> mans = userDAO.findUsersByGroupType(GroupType.MANAGER);
		assertNotNull(mans);
		assertEquals(1, mans.size());
		
		AssUser man = mans.get(0);
		
		List<AssUser> devs = userDAO.findDevsByPM(man);
		assertEquals(2, devs.size());
	}
	
	@Test
	public void testFindByUsername() {
		AssUser user1 = new AssUser();
		user1.setUsername("A");
		user1.setPassword("A");
		userDAO.save(user1);

		AssUser user = userDAO.findByUsername("A");
		assertNotNull(user);
		assertEquals("A", user.getUsername());
		
	}
	
	@Test
	public void testFindByUsernames() {
		AssUser user1 = new AssUser();
		user1.setUsername("A");
		user1.setPassword("A");
		userDAO.save(user1);
		
		AssUser user2 = new AssUser();
		user2.setUsername("B");
		user2.setPassword("A");
		userDAO.save(user2);
		
		List<AssUser> found = userDAO.findByUsernames("A", "B");
		
		assertNotNull(found);
		assertEquals(2, found.size());
	}
}

