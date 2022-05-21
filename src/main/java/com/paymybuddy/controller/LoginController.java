package com.paymybuddy.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.entity.Eusers;
import com.paymybuddy.entity.PaymybuddyUserDetails;

@Controller
public class LoginController{
	
	//private final Logger loginControllerLogger = LogManager.getLogger("Login Controller");
	
	@GetMapping("/login")
	public String login() {	
		return "login";
	}

	@PostMapping("/login")
	public ModelAndView checkUsers(@ModelAttribute Eusers eusers) {	
		
		PaymybuddyUserDetails userDetails = new PaymybuddyUserDetails(eusers);
		
		userDetails.getUsername();
		
		
		return new ModelAndView("redirect:/home");
	}
	
	
}
