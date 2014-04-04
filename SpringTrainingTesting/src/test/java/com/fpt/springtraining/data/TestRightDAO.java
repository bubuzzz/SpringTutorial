package com.fpt.springtraining.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.springtraining.config.DatabaseConfig;
import com.fpt.springtraining.constants.RightType;
import com.fpt.springtraining.data.dao.IRightDAO;
import com.fpt.springtraining.data.daoimpl.GroupDAOImpl;
import com.fpt.springtraining.data.daoimpl.RightDAOImpl;
import com.fpt.springtraining.data.entities.AssRight;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
// @TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@ContextConfiguration(classes = {RightDAOImpl.class, GroupDAOImpl.class, DatabaseConfig.class})
public class TestRightDAO {
	
	@Autowired
	private IRightDAO rightDAO;
	
	@Test
	public void testSave() {
		AssRight right = new AssRight();
		right.setType(RightType.CREATE.getValue());
		rightDAO.save(right);
		
		assertNotNull(right.getId());
	}

	@Test
	public void testUpdate() {
		
		AssRight right = new AssRight();
		right.setType(RightType.CREATE.getValue());
		rightDAO.save(right);
		
		assertNotNull(right.getId());
		
		right.setType(RightType.VIEW.getValue());
		
		rightDAO.update(right);
		
		AssRight oldRight = rightDAO.findById(right.getId());
		
		assertEquals(oldRight, right);
		assertEquals(oldRight.getType(), RightType.VIEW.getValue());		

	}

	@Test
	public void testDelete() {
		AssRight right = new AssRight();
		
		right.setType(RightType.CREATE.getValue());
		rightDAO.save(right);
		
		assertNotNull(right.getId());
		rightDAO.delete(right);
		
		AssRight oldRight = rightDAO.findById(right.getId());
		
		assertNull(oldRight);
	}

	@Test
	public void testFindAll() {
		AssRight newRight = new AssRight();
		newRight.setType(RightType.CREATE.getValue());
		
		AssRight newRight1 = new AssRight();
		newRight1.setType(RightType.UPDATE.getValue());
		
		AssRight newRight2 = new AssRight();
		newRight2.setType(RightType.DELETE.getValue());
		
		AssRight newRight3 = new AssRight();
		newRight3.setType(RightType.VIEW.getValue());
		
		rightDAO.save(newRight);
		rightDAO.save(newRight1);
		rightDAO.save(newRight2);
		rightDAO.save(newRight3);
		
		assertEquals(4, rightDAO.findAll().size());
	}

}
