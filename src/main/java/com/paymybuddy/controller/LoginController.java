package com.paymybuddy.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController{

	@GetMapping("/login")
	public String login() {	
		return "login";
	}
	
	@RolesAllowed("USER")
	@RequestMapping("/home")
	public String getHome() {
		return "home";
	}
	
}
