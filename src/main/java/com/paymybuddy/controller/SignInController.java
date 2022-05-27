package com.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.service.PaymybuddyUserDetailsService;

@Controller
public class SignInController {

	private final Logger SignInControllerLogger = LogManager.getLogger("SignInController");
	
	PaymybuddyUserDetailsService userDetails;

	@GetMapping("/signin")
	public String signin() {		
		return "signin";
	}

	@PostMapping("/signin")
	public ModelAndView checkSignin() {	
		return new ModelAndView("redirect:/signinconfirm");
	}
	
	@GetMapping("/signinconfirm")
	public String signinconfirm() {		
		return "signinconfirm";
	}
	
/*	@PostMapping("/signinconfirm")
	public ModelAndView checkConfirm() {	
		return new ModelAndView("redirect:/login");
	}*/
	
}
