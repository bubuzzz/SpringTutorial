package com.fpt.springtraining.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	/**
	 * Return the hello string with the root web context
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hello/toJson", method = RequestMethod.GET)
	@ResponseBody
	// @SessionLookUp
	public String sayHelloService() {
		return "Hello";
	}
	
	
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	protected ModelAndView sayHello(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
 
		ModelAndView model = new ModelAndView("sayHello");
		model.addObject("msg", "hello world");
 
		return model;
	}
}
