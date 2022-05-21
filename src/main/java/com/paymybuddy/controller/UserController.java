package com.paymybuddy.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;



/*@Controller
//@RequestMapping("/user")
@RolesAllowed("USER")
public class UserController {
	
	@GetMapping("/user")
	public String logout() {	
		return "logout";
	}
	
	@PostMapping("/user")
	public ModelAndView logoutPost() {	

			return new ModelAndView("redirect:/login?logout=true");
	}
	
}*/
