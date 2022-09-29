package com.paymybuddy.controller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class OAuth2Controller {

	private final Logger LOGGER = LogManager.getLogger("OAuth2Controller");

	@Autowired
	PaymybuddyPasswordEncoder paymybuddyPasswordEncoder;

	@Autowired
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@Autowired
	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@RolesAllowed({ "USER", "ADMIN" })
	@GetMapping("/oauth2")
	public String getOauth2(OAuth2AuthenticationToken authentication) {

		String result;
		
		LOGGER.info("Oauth2 details "+authentication);
		
		String email = authentication.getPrincipal().getAttribute("email");

		if (findPaymybuddyUserDetailsService.findByEmail(email).getEmail() == "N_A") {
			LOGGER.info("User not registered, loading into database initiated");

			PaymybuddyUserDetails buddyUserDetails = new PaymybuddyUserDetails();
			LOGGER.info(buddyUserDetails);
			LOGGER.info(email);
			buddyUserDetails.setEmail(email);
			buddyUserDetails.setName(authentication.getPrincipal().getAttribute("name"));
			LOGGER.info(buddyUserDetails);

			PasswordEncoder passWordEncoder = paymybuddyPasswordEncoder.getPasswordEncoder();
			String pass = passWordEncoder.encode(authentication.getName());// .substring(8);
			buddyUserDetails.setPassword(pass);

			buddyUserDetails.setBalance(100f);
			LOGGER.info(buddyUserDetails);

			buddyUserDetails.setUserRole(UserRole.USER);
			buddyUserDetails.setEnabled(true);
			buddyUserDetails.setLocked(false);

			LOGGER.info(buddyUserDetails);
			savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(buddyUserDetails);

			result = "/oauth2";
		
		} else {
			LOGGER.info("User registered, redirected to home page");
			result = "redirect:/home?size=3&page=1";
		
		}
		return result;
	}


}
