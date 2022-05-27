package com.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.service.PaymybuddyUserDetailsService;

@Controller
public class LoginController{
	
	private final Logger loginControllerLogger = LogManager.getLogger("LoginController");
	
	PaymybuddyUserDetailsService userDetails;

	@GetMapping("/login")
	public String login() {		
		return "login";
	}

	@PostMapping("/login")
	public ModelAndView checkUsers() {	
		return new ModelAndView("redirect:/home");
	}
	
	
}
