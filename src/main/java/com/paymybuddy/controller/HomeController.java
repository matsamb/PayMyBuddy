package com.paymybuddy.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@RolesAllowed("USER")
	@RequestMapping("/**")
	public String getUser() {
		return "Hi Mate";
	} 

	@RolesAllowed({ "USER", "ADMIN" })
	@RequestMapping("/admin")
	public String getAdmin() {
		return "Hi Admin";
	}

	
}
