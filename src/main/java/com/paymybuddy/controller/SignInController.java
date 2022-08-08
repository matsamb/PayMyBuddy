package com.paymybuddy.controller;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.ViewUser;
import com.paymybuddy.entity.ActivationToken;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.activationtoken.SaveActivationTokenService;
import com.paymybuddy.service.email.EmailSenderService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

@Controller
//@AllArgsConstructor
public class SignInController {

	private final Logger SignInControllerLogger = LogManager.getLogger("SignInController");
	
	@Autowired
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@Autowired
	SaveActivationTokenService saveActivationTokenService;
	
	@Autowired
	EmailSenderService emailSenderService;
	
	@Autowired
	PaymybuddyPasswordEncoder paymybuddyPasswordEncoder;
	
	SignInController(SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService		
				,SaveActivationTokenService saveActivationTokenService
				,EmailSenderService emailSenderService
			){
		this.savePaymybuddyUserDetailsService = savePaymybuddyUserDetailsService;
		this.saveActivationTokenService = saveActivationTokenService;
		this.emailSenderService = emailSenderService;
	}
	

	@GetMapping("/signin")
	public String signin(ViewUser neweuser, BindingResult bindingresult, Model model) {	
		SignInControllerLogger.info("Sign In page displayed");
		return "signin";
	}

	@PostMapping("/signin")
	public ModelAndView checkSignin(ViewUser neweuser, Float balance, String name, String confirmpassword, BindingResult bindingresult, Model model) {	

		ModelAndView result = null;
						
		PaymybuddyUserDetails buddyUser = new PaymybuddyUserDetails();
		
		Boolean mailReturn = false;
		SignInControllerLogger.info("Mail confirmation data recovered");
		
		if(mailReturn==false && Objects.equals(neweuser.getPassword(),confirmpassword)) {
	
			SignInControllerLogger.info("email confirmed and password confirmed");
			
			buddyUser.setEmail(neweuser.getUsername());
			SignInControllerLogger.debug("email set");
			buddyUser.setUsername(name);
			SignInControllerLogger.debug("pseudo set");
			
			buddyUser.setBalance(balance);
			SignInControllerLogger.debug("balance set");
			
		/*	neweuser.setPassword(paymybuddyPasswordEncoder
									.getPasswordEncoder()
									.encode(neweuser.getPassword())
									);*/
			SignInControllerLogger.debug("password encoded");
			PasswordEncoder passWordEncoder = paymybuddyPasswordEncoder
					.getPasswordEncoder();
			String pass = passWordEncoder
					.encode(neweuser.getPassword());//.substring(8);
			buddyUser.setPassword(pass);
			SignInControllerLogger.debug("password set");

			buddyUser.setEnabled(mailReturn);
			SignInControllerLogger.debug("email verified");
			buddyUser.setUserRole(UserRole.USER);
			SignInControllerLogger.debug("role set");
			
			savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(buddyUser);
			
			ActivationToken activationToken = new ActivationToken();
			String token = UUID.randomUUID().toString();
			long tokenMinuteLength = 1;
			Timestamp startTime = new Timestamp(System.currentTimeMillis());
			Timestamp endTime = new Timestamp(System.currentTimeMillis()+tokenMinuteLength*60*1000);
			activationToken.setStartTime(startTime);
			activationToken.setExpirationTime(endTime);
			activationToken.setUser(buddyUser);
			activationToken.setToken(token);
			
			saveActivationTokenService.saveActivationToken(activationToken);
			
			int port = 9080;
			String address = activationToken.getUser().getEmail();
			String url = "http://localhost:"+port+"/accountactivation?token="+activationToken.getToken();
			String message = "To activate your account click <a href='"+url+"'>here</a>";
			
			emailSenderService.send(address, message);
			
			result = new ModelAndView("redirect:/signinconfirm");
			SignInControllerLogger.info("New User's data retrieved and loaded to database");

		}else {
			result = new ModelAndView("redirect:/signin?error2=true");
			SignInControllerLogger.info("data entry error");
		}
		SignInControllerLogger.info("Sign In posted");
		return result;
	}
	
	@GetMapping("/signinconfirm")
	public String signinconfirm() {	
		SignInControllerLogger.info("Sign In confirmation page displayed");
		return "signinconfirm";
	}
	
}
