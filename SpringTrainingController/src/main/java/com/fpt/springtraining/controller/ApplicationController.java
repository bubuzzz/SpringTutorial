package com.fpt.springtraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Return the root web context
 * 
 * @author ThaiTC
 *
 */
@Controller
public class ApplicationController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index () {
		return new ModelAndView("index");
	}
	
	/**
	 * Return the hello string with the root web context
	 * 	
	 * @return
	 */
	@RequestMapping(value = "/hello/toJson")
	@ResponseBody
	public String sayHelloService() {
		return "Hello";
	}	
}
