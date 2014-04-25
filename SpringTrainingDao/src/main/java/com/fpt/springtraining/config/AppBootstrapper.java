package com.fpt.springtraining.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fpt.springtraining.data.dao.IUserDAO;
import com.fpt.springtraining.data.entities.AssUser;

@Transactional
@Component
public class AppBootstrapper implements ApplicationListener<ContextRefreshedEvent> {
  
  @Autowired
  IUserDAO m_user;
  
  @Override
  public void onApplicationEvent(final ContextRefreshedEvent event) {
	System.out.println("======================== SYSTEM IS LOADING ==========================");
	
	AssUser admin = new AssUser();
	AssUser oldAdmin = m_user.findById(admin.getId());
	
	if (oldAdmin == null) {
	  admin.setUsername("bubuzzz");
	  admin.setPassword("12345678");
	  admin.setAdmin(1);
	  m_user.save(admin);
	}
  }
}
