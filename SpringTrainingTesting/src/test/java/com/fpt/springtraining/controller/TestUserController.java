package com.fpt.springtraining.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.fpt.springtraining.config.AppConfig;
import com.fpt.springtraining.config.DatabaseConfig;
import com.fpt.springtraining.config.Routes;
import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.daoimpl.UserDAOImpl;
import com.fpt.springtraining.logic.serviceimpl.GroupServiceImpl;
import com.fpt.springtraining.logic.serviceimpl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
// @TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
@ContextConfiguration(classes = {
	AppConfig.class,
	UserServiceImpl.class,
	UserDAOImpl.class,
	GroupServiceImpl.class,
	DatabaseConfig.class 
})
@TestExecutionListeners({ 
	DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
})
public class TestUserController {
	
    private MockMvc mockMvc;
    
    @Autowired
    SimpleMappingExceptionResolver exceptionResolver;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
    @Before
    public void setUp() {                    
    	 mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();	
    }
    
    /**
     * Due to asynchronization inserting and retrieving, this test case does not run at this moment
     *  
     * @throws Exception
     */
    @Test
    public void testDisplayDevsThroughRest() throws Exception {
    	// create pm
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "man1"
			).param(
				"role", GroupType.MANAGER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	// create DEV
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "B"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "dev1"
			).param(
				"role", GroupType.DEVELOPER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	// create DEV
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "C"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "dev1"
			).param(
				"role", GroupType.DEVELOPER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	// display devs
    	mockMvc.perform(
			post(
				Routes.IUser.LIST_DEVS_SERVICE
			).param(
				"username", "A"
			).param(
				"password", "A"
			).param(
				"groupType", GroupType.DEVELOPER.getValue()
			).accept(
				MediaType.APPLICATION_JSON)
		).andDo(
			print()
		).andExpect(
			status().isOk()
		).andExpect(
			content().contentType("application/json;charset=UTF-8")
		);
    }
    
    @Test
    public void testCreateUser() throws Exception {
    	
    	for (int i = 0; i < 10; i ++) {
	    	mockMvc.perform(
				post(
					Routes.IUser.ADD_USER
				).param(
					"username", "A"+i
				).param(
					"password", "A" 
				).param(
					"agreed", "true"
				).param(
					"groupName", "man1"
				).param(
					"groupName", "dev1"
				).param(
					"role", GroupType.MANAGER.getValue()
				).param(
					"role", GroupType.DEVELOPER.getValue()
				)
			).andExpect(
				status().isOk()
			).andExpect(
				view().name("User is created successfully")
			);
    	}
    }

    @Test
    public void testLogin() throws Exception {
    	
    	// create user
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "man1"
			).param(
				"groupName", "dev1"
			).param(
				"role", GroupType.MANAGER.getValue()
			).param(
				"role", GroupType.DEVELOPER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
		   
    	// login user
    	mockMvc.perform(
			post(
				Routes.IUser.LOGIN_USER_PROCESSING
			).param(
				"username", "A"
			).param(
				"password", "A" 
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("You are currently logged in to the system as A")
		);
    	
    	// login user fail
    	mockMvc.perform(
			post(
				Routes.IUser.LOGIN_USER_PROCESSING
			).param(
				"username", "A"
			).param(
				"password", "B" 
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("Login fail. Please try again")
		);
    	
    	
    	// login user again
    	mockMvc.perform(
			post(
				Routes.IUser.LOGIN_USER_PROCESSING
			).param(
				"username", "A"
			).param(
				"password", "A" 
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("You are currently logged in to the system as A")
		);
    }
    
    @Test
	public void testAuthenticationFailAndSuccess() throws Exception {
    	
    	// access fail
    	mockMvc.perform(
			get(Routes.IUser.LIST_USER)	
		).andExpect(
			status().isNotFound()
		);
    	
    	MockHttpSession mocksession = new MockHttpSession();
    	
    	// create user
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "man1"
			).param(
				"groupName", "dev1"
			).param(
				"role", GroupType.MANAGER.getValue()
			).param(
				"role", GroupType.DEVELOPER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
		   
    	// login user
    	mockMvc.perform(
			post(
				Routes.IUser.LOGIN_USER_PROCESSING
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).session(
				mocksession
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("You are currently logged in to the system as A")
		);
        	
    	// access successfully
    	mockMvc.perform(
			get(
				Routes.IUser.LIST_USER
			).session(
				mocksession
			).param(
				"groupType", GroupType.MANAGER.getValue() 
			)
		).andDo(
			print()
		).andExpect(
			status().isOk()
		);
    }
    
    @Test
    public void testAssignDevsToPM() throws Exception {
    	MockHttpSession mocksession = new MockHttpSession();
    	
    	// create pm
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "man1"
			).param(
				"role", GroupType.MANAGER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	// create dev
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "C"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "dev1"
			).param(
				"role", GroupType.DEVELOPER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	// create dev
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "B"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "dev1"
			).param(
				"role", GroupType.DEVELOPER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	// login user
    	mockMvc.perform(
			post(
				Routes.IUser.LOGIN_USER_PROCESSING
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).session(
				mocksession
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("You are currently logged in to the system as A")
		);
 
    	// assign devs
    	mockMvc.perform(
			post(
				Routes.IUser.ASSIGN_DEVS_TO_PM
			).param(
				"devNames", "C"
			).param(
				"devNames", "B" 
			).session(
				mocksession
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("Developers has been assigned successfully")
		);
    }
    
    @Test
    public void testCreateGroup() throws Exception {
    	// create pm
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "man1"
			).param(
				"role", GroupType.MANAGER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);    	
    	
    	MockHttpSession mocksession = new MockHttpSession();

    	// login user
    	mockMvc.perform(
			post(
				Routes.IUser.LOGIN_USER_PROCESSING
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).session(
				mocksession
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("You are currently logged in to the system as A")
		);
    	
    	// create group
    	mockMvc.perform(
			post(
				Routes.IGroup.ADD_GROUP
			).param(
				"groupName", "man2"
			).param(
				"groupType", "MANAGER" 
			).session(
				mocksession
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("Group has been created successfully")
		);
    }
    
    @Test
    public void testAssignUsersToGroup() throws Exception {
    	// create pm
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "man1"
			).param(
				"role", GroupType.MANAGER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	

    	// create DEV
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "B"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "dev1"
			).param(
				"role", GroupType.DEVELOPER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	// create DEV
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "C"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "dev1"
			).param(
				"role", GroupType.DEVELOPER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	MockHttpSession mocksession = new MockHttpSession();

    	// login user
    	mockMvc.perform(
			post(
				Routes.IUser.LOGIN_USER_PROCESSING
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).session(
				mocksession
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("You are currently logged in to the system as A")
		);
    	
    	// create group
    	mockMvc.perform(
			post(
				Routes.IGroup.ADD_GROUP
			).param(
				"groupName", "dev2"
			).param(
				"groupType", "DEVELOPER" 
			).session(
				mocksession
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("Group has been created successfully")
		);
    	
    	// assign devs to group
    	mockMvc.perform(
			post(
				Routes.IUser.ASSIGN_DEVS_TO_GROUP
			).param(
				"groupName", "dev2"
			).param(
				"usernames", "B" 
			).param(
				"usernames", "C"
			).session(
				mocksession
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("Developers has been assigned to group successfully")
		);
    }
    
    @Test
    public void testDisplayDevs() throws Exception {
    	// create pm
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "man1"
			).param(
				"role", GroupType.MANAGER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	// create DEV
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "B"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "dev1"
			).param(
				"role", GroupType.DEVELOPER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	// create DEV
    	mockMvc.perform(
			post(
				Routes.IUser.ADD_USER
			).param(
				"username", "C"
			).param(
				"password", "A" 
			).param(
				"agreed", "true"
			).param(
				"groupName", "dev1"
			).param(
				"role", GroupType.DEVELOPER.getValue()
			)
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("User is created successfully")
		);
    	
    	MockHttpSession mocksession = new MockHttpSession();
    	
    	// login user
    	mockMvc.perform(
			post(
				Routes.IUser.LOGIN_USER_PROCESSING
			).param(
				"username", "A"
			).param(
				"password", "A" 
			).session(
				mocksession
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("You are currently logged in to the system as A")
		);
 
    	// assign devs
    	mockMvc.perform(
			post(
				Routes.IUser.ASSIGN_DEVS_TO_PM
			).param(
				"devNames", "C"
			).param(
				"devNames", "B" 
			).session(
				mocksession
			) 
		).andExpect(
			status().isOk()
		).andExpect(
			view().name("Developers has been assigned successfully")
		);
    	
    	// display devs
    	mockMvc.perform(
			get(
				Routes.IUser.LIST_DEVS
			).session(
				mocksession
			).accept(
				MediaType.APPLICATION_JSON)
		).andDo(
			print()
		).andExpect(
			status().isOk()
		).andExpect(
			content().contentType("application/json;charset=UTF-8")
		).andExpect(
			jsonPath("$", Matchers.hasSize(2))
		);
    	
    }
}
