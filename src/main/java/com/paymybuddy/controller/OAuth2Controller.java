package com.paymybuddy.controller;

import java.util.Objects;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

import lombok.AllArgsConstructor;

//@RestController
@Controller
@AllArgsConstructor
public class OAuth2Controller {

	private final Logger LOGGER = LogManager.getLogger("OAuth2Controller");

	@Autowired
	PaymybuddyUserDetailsService paymybuddyUserDetailsService;

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
		String email = authentication.getPrincipal().getAttribute("email");
		LOGGER.info(email);

			PaymybuddyUserDetails buddyUserDetails = new PaymybuddyUserDetails();
			LOGGER.info(buddyUserDetails);
			LOGGER.info(email);
			buddyUserDetails.setEmail(email);
			buddyUserDetails.setName(authentication.getName());
			LOGGER.info(buddyUserDetails);

			PasswordEncoder passWordEncoder = paymybuddyPasswordEncoder.getPasswordEncoder();
			String pass = passWordEncoder.encode(authentication.getName());// .substring(8);
			buddyUserDetails.setPassword(pass);// pass

			//buddyUserDetails.setBalance(100f);
			LOGGER.info(buddyUserDetails);

			buddyUserDetails.setUserRole(UserRole.USER);
			buddyUserDetails.setEnabled(true);

			LOGGER.info(buddyUserDetails);
			savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(buddyUserDetails);

			//result = new ModelAndView("/oauth2");
			result = "/oauth2";
		
		return result;
	}

}
