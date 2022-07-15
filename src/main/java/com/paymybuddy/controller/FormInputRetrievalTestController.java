package com.paymybuddy.controller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.Users;

@Controller
public class FormInputRetrievalTestController {

	static final Logger Logger = LogManager.getLogger("FormInputRetrievalTestController");

	
	@RolesAllowed("USER")
	@GetMapping("/test")
	public String getTest(Users euser, Authentication oth, OAuth2AuthenticationToken authentication) {
		
		if(oth.getPrincipal() instanceof OAuth2AuthenticationToken) {		
			Logger.info(authentication);
		}
		if(oth.getPrincipal() instanceof UsernamePasswordAuthenticationToken) {
			Logger.info(oth);
		}

		Logger.info(oth);
		Logger.info(authentication);
		
		return "test";
	}
	
	
	@RolesAllowed("USER")
	@PostMapping("/test")
	public ModelAndView getValues(@Validated Users euser, BindingResult bindingResult, Authentication oth, OAuth2AuthenticationToken authentication) {
		
		Logger.info(oth);
		//Logger.info(authentication);
		
		if(bindingResult.hasErrors()) {
			return new ModelAndView("redirect:/test?error=true");
		}
		System.out.println(euser.getUsername()+" "+euser.getPassword());
	
		return new ModelAndView("redirect:/home");
	}
	
}
