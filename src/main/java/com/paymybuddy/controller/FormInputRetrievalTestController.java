package com.paymybuddy.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.entity.Users;

@Controller
public class FormInputRetrievalTestController {

	
	@RolesAllowed("USER")
	@GetMapping("/test")
	public String getTest(Users euser) {
		
		return "test";
	}
	
	
	@RolesAllowed("USER")
	@PostMapping("/test")
	public ModelAndView getValues(@Validated Users euser, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return new ModelAndView("redirect:/test?error=true");
		}
		System.out.println(euser.getUsername()+" "+euser.getPassword());
	
		return new ModelAndView("redirect:/home");
	}
	
}
