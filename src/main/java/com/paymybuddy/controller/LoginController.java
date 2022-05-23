package com.paymybuddy.controller;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.entity.Eusers;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.PaymybuddyUserDetailsService;

@Controller
public class LoginController{
	
	private final Logger loginControllerLogger = LogManager.getLogger("LoginController");
	
	PaymybuddyUserDetailsService userDetails;
	
	/*@ModelAttribute("euser")
	public Eusers getEuser() {
		return new Eusers();
	}*/
	
	@GetMapping("/login")
	public String login() {	

		return "login";
	}

	@RequestMapping(value="/login", params = "loginbutton", method = RequestMethod.POST)
	public ModelAndView checkUsers(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("balance") int balance) {	
				
		
		loginControllerLogger.info(username);
		loginControllerLogger.info(password+" "+balance);
		
		return new ModelAndView("redirect:/home");
	}
	
	
}
