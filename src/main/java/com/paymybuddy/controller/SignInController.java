package com.paymybuddy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.entity.Authorities;
import com.paymybuddy.entity.Users;
import com.paymybuddy.model.ViewUser;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.PaymybuddyUserDetailsService;
import com.paymybuddy.service.authority.AuthoritiesSaveAuthorityService;
import com.paymybuddy.service.users.UsersSaveUserService;

@Controller
public class SignInController {

	private final Logger SignInControllerLogger = LogManager.getLogger("SignInController");
	
	@Autowired
	UsersSaveUserService usersSaveUserService;
	
	@Autowired
	AuthoritiesSaveAuthorityService authoritiesSaveAuthorityService;
	
	@Autowired
	PaymybuddyPasswordEncoder paymybuddyPasswordEncoder;
	
	SignInController(UsersSaveUserService usersSaveUserService
					,AuthoritiesSaveAuthorityService authoritiesSaveAuthorityService
					){
		this.usersSaveUserService = usersSaveUserService;
		this.authoritiesSaveAuthorityService = authoritiesSaveAuthorityService;
	}
	

	@GetMapping("/signin")
	public String signin(ViewUser neweuser, BindingResult bindingresult, Model model) {	
		SignInControllerLogger.info("Sign In page displayed");
		return "signin";
	}

	@PostMapping("/signin")
	public ModelAndView checkSignin(ViewUser neweuser, String confirmpassword, BindingResult bindingresult) {	

		ModelAndView result = null;
		
		Users user = new Users();
		System.out.println(confirmpassword);
		
		Boolean mailReturn = true;
		SignInControllerLogger.info("Mail confirmation data recovered");

		List<String> roles = new ArrayList<>();
		roles.add("ROLE_USER");
		roles.add("ROLE_ADMIN");
		
		Authorities newAuthority = new Authorities();
		
		if(mailReturn==true && Objects.equals(neweuser.getPassword(),confirmpassword)) {
	
			user.setUsername(neweuser.getUsername());
			neweuser.setPassword(paymybuddyPasswordEncoder
									.getPasswordEncoder()
									.encode(neweuser.getPassword())
									);
			String pass = neweuser.getPassword().substring(8);
			user.setPassword(pass);
			user.setEnabled(mailReturn);
			
			newAuthority.setUsername(neweuser.getUsername());
			newAuthority.setAuthority(roles.get(0));
			
			usersSaveUserService.saveUser(user);
			authoritiesSaveAuthorityService.saveAuthorities(newAuthority);
			result = new ModelAndView("redirect:/signinconfirm");
			SignInControllerLogger.info("Sign data retrieved and loaded to database");

		}else {
			result = new ModelAndView("redirect:/signin?error2=true");
			SignInControllerLogger.info("data entry error");

		}
		
		return result;
	}
	
	@GetMapping("/signinconfirm")
	public String signinconfirm() {	
		SignInControllerLogger.info("Sign In confirmation page displayed");

		return "signinconfirm";
	}
	
}
