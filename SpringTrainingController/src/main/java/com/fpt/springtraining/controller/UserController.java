package com.fpt.springtraining.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpt.springtraining.config.Routes;
import com.fpt.springtraining.constants.GroupType;
import com.fpt.springtraining.data.entities.AssGroup;
import com.fpt.springtraining.data.entities.AssUser;
import com.fpt.springtraining.dto.UserLogin;
import com.fpt.springtraining.dto.UserRegistration;
import com.fpt.springtraining.exceptions.DuplicateObjectException;
import com.fpt.springtraining.exceptions.FailAuthenticationException;
import com.fpt.springtraining.logic.aspects.SessionLookUp;
import com.fpt.springtraining.logic.service.IGroupService;
import com.fpt.springtraining.logic.service.IUserService;

/**
 * Control all the URL relating to user
 * 
 * @author ThaiTC
 *
 */
@Controller
public class UserController {

	@Autowired
	private IUserService m_userService;
	
	@Autowired
	private IGroupService m_groupService;
	
	
	/**
	 * Add a new user
	 * 
	 * @param username
	 * @param password
	 * @param agreed
	 * @param groupName
	 * @param role
	 * @return
	 */
	@RequestMapping(value = Routes.IUser.ADD_USER, method = RequestMethod.POST)
	public String createUser(String username, String password, boolean agreed, String [] groupName, String []role) {
		
		if (groupName.length != role.length) {
			return "User is created failed";
		}
		
		String [][] groups = new String [groupName.length][role.length];
		
		for (int x = 0; x < groupName.length; x++) {
			groups[x] = new String[] {groupName[x], role[x]};
		}
		
		try {			
			UserRegistration userReg = new UserRegistration(username, password, agreed, groups) ;
			String id = m_userService.createUser(userReg);
			
			if (id != null) {
				return "User is created successfully";
			}
		} catch (DuplicateObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "User is created failed";
		}
		
		return "User is created failed";
	}
	
	/**
	 * Authenticate a use when logging in
	 * 
	 * @param request
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value = Routes.IUser.LOGIN_USER_PROCESSING, method = RequestMethod.POST)	
	public String authenticateUser(HttpServletRequest request, String username, String password) {
		AssUser user;
		try {
			user = m_userService.login(new UserLogin(username, password));
			
			request.getSession().setAttribute("username", username);
			request.getSession().setAttribute("isLogined", Boolean.TRUE);
			request.getSession().setAttribute("role", m_groupService.findGroupsByUser(user));
			
			return String.format("You are currently logged in to the system as %s", username);
		} catch (FailAuthenticationException e) {
			e.printStackTrace();
			return "Login fail. Please try again";
		}
	}

	/**
	 * delete a user (not implemented)
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = Routes.IUser.REMOVE_USER, method = RequestMethod.POST)
	public String deleteUser(@PathVariable String id) {
		return null;
	}

	/**
	 * (List all DEV and List all PMs) This will list users based on the type. If type is null, then all users will be returned
	 * 
	 * @param session
	 * @param groupType
	 * @return
	 */
	@RequestMapping(value = Routes.IUser.LIST_USER, method = RequestMethod.GET)
	@ResponseBody
	@SessionLookUp
	// @ExceptionHandler(FailAuthenticationException.class)
	public List<AssUser> displayUsers(HttpSession session, String groupType) {
		return m_userService.displayUsers(groupType);
	}

	/**
	 * (List all DEVs managed by a login PM)
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = Routes.IUser.LIST_DEVS, method = RequestMethod.GET)
	@ResponseBody
	@SessionLookUp
	public List<AssUser> displayDevs(HttpSession session) {
		String username = (String ) session.getAttribute("username");
		AssUser pm = m_userService.findByUsername(username);
		return m_userService.displayDevs(pm);
	}
	
	/**
	 * Assign a list of devs to a pm
	 * 
	 * @param session
	 * @param devNames
	 * @return
	 */
	@RequestMapping(value = Routes.IUser.ASSIGN_DEVS_TO_PM, method = RequestMethod.POST)
	@SessionLookUp
	public String assignDevsToPM(HttpSession session, String... devNames) {
		
		try {
			String pmName = (String) session.getAttribute("username");
			
			AssUser pm = m_userService.findByUsername(pmName);	
			List<AssUser> devs = m_userService.findByUsernames(devNames);
			
			for (AssUser dev : devs) {
				dev.setUser(pm);
				m_userService.updateUser(dev);
			}

			return "Developers has been assigned successfully";		
		
		} catch (DuplicateObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * Assign a list of user to group 
	 * 
	 * @param groupName
	 * @param usernames
	 * @return
	 */
	@RequestMapping(value = Routes.IUser.ASSIGN_DEVS_TO_GROUP, method = RequestMethod.POST)
	@SessionLookUp
	public String assignUsersToGroup(String groupName, String... usernames) {
		try {
			AssGroup group = m_groupService.findByName(groupName);
			
			List<AssUser> users = m_userService.findByUsernames(usernames);
			group.setUsers(users);
		
			m_groupService.updateGroup(group);
			return "Developers has been assigned to group successfully";		

		} catch (DuplicateObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * Create Group 
	 * 
	 * @param groupName
	 * @param groupType
	 * @return
	 */
	@RequestMapping(value = Routes.IGroup.ADD_GROUP, method = RequestMethod.POST)
	@SessionLookUp
	public String createGroup(String groupName, String groupType) {
		try {
			
			GroupType selected = null;
			for (GroupType type : GroupType.values()) {
				if (type.getValue().equals(groupType)) {
					selected = type;
					break;
				}
			}
			
			m_groupService.createGroup(groupName, selected);
			
			return "Group has been created successfully";		

		} catch (DuplicateObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
