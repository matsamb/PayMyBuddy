package com.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController{
	
	private final Logger loginControllerLogger = LogManager.getLogger("LoginController");
	
	@GetMapping("/login")
	public String login() {	
		loginControllerLogger.info("Log In page displayed");
		return "login";
	}

	@PostMapping("/login")
	public ModelAndView checkUsers() {	
		loginControllerLogger.info("user logged in");
		return new ModelAndView("redirect:/home?size=3&page=1");
	}
	
	
}
