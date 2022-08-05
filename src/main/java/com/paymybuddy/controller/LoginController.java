package com.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.users.PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;

@Controller
public class LoginController{
	
	private final Logger loginControllerLogger = LogManager.getLogger("LoginController");
	
	@GetMapping("/login")
	public String login() {	
		loginControllerLogger.info("Log In page displayed");
		return "login";
	}

	@PostMapping("/login")
	public ModelAndView checkUsers(/*Boolean remember, BindingResult bindingResult*/) {	

		return new ModelAndView("redirect:/home?size=3&page=1");
	}
	
	
}
