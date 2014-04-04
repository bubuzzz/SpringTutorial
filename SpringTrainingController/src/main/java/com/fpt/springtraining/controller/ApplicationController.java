package com.fpt.springtraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpt.springtraining.logic.aspects.SessionLookUp;

/**
 * Return the root web context
 * 
 * @author ThaiTC
 *
 */
@Controller
public class ApplicationController {
	
	/**
	 * Return the hello string with the root web context
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	@SessionLookUp
	public String sayHello() {
		return "Hello";
	}
}
