package com.fpt.springtraining.config;

/**
 * Define routes / URL for the system
 * @author ThaiTC
 *
 */
public class Routes {
	
	/**
	 * Contains url relating to users
	 * @author ThaiTC
	 *
	 */
	public interface IUser {
		public static final String ADD_USER 				= "/user/add";
		public static final String REMOVE_USER 				= "/user/delete/{userId}";
		public static final String UPDATE_USER 				= "/user/update";
		
		public static final String LIST_USER 				= "/user/list";
		public static final String LIST_DEVS 				= "/user/devs";
		
		public static final String ASSIGN_DEVS_TO_PM		= "/user/assign";
		public static final String ASSIGN_DEVS_TO_GROUP		= "/user/assign/group";
		
		public static final String LOGIN_USER_PROCESSING 	= "/user/login";
	}
	
	/**
	 * Contains url relating to groups
	 * 
	 * @author ThaiTC
	 *
	 */
	public interface IGroup {
		public static final String ADD_GROUP 				= "/group/add";
	}
	
}
