package com.paymybuddy.controller;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.ViewConnection;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;

import lombok.AllArgsConstructor;

@RolesAllowed("USER")
@Controller
//@AllArgsConstructor
public class AddConnectionController {
//todo : add connection confirmation
	final static Logger LOGGER = LogManager.getLogger("AddConnectionController");

	@Autowired
	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@Autowired
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@Autowired
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;
	
	

	 AddConnectionController(FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService 
			 ,SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService
			 ,FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService
			 ){ 
		 this.findPaymybuddyUserDetailsService = findPaymybuddyUserDetailsService;
		 this.savePaymybuddyUserDetailsService = savePaymybuddyUserDetailsService;
		 this.findOauth2PaymybuddyUserDetailsService = findOauth2PaymybuddyUserDetailsService;
	 }

	@GetMapping("/addconnection")
	public String getAddConnection(Authentication auth, ViewConnection viewConnection, BindingResult binding) {
		LOGGER.info("addconnection page displayed");
		return "addconnection";
	}

	@PostMapping("/addconnection")
	public ModelAndView createConnection(Authentication auth, ViewConnection viewConnection, BindingResult binding) {

		ModelAndView result;
		
		String loggedUserEmail;

		LOGGER.debug(viewConnection);
		LOGGER.info("addconnection page displayed and connection posted");
		LOGGER.debug(findPaymybuddyUserDetailsService.findByEmail(viewConnection.getConnection()));
				
		if (auth instanceof UsernamePasswordAuthenticationToken) {
			LOGGER.info(auth.getName() + " is instance of UsernamePasswordAuthenticationToken");
			loggedUserEmail = auth.getName();

		} else {
			LOGGER.info(auth.getName() + " is instance of OAuth2AuthenticationToken");
			String nameNumber = auth.getName();
			PaymybuddyUserDetails foundOauth2 = findOauth2PaymybuddyUserDetailsService.findByName(nameNumber);
			loggedUserEmail = foundOauth2.getEmail();

		}
		
		LOGGER.trace(findPaymybuddyUserDetailsService.findByEmail(viewConnection.getConnection()));

		
		if (findPaymybuddyUserDetailsService.findByEmail(viewConnection.getConnection()).getEmail() != "N_A") {
			LOGGER.info("User "+viewConnection.getConnection()+" found");

			PaymybuddyUserDetails userToAdd = findPaymybuddyUserDetailsService
					.findByEmail(viewConnection.getConnection());
			
			PaymybuddyUserDetails currentUser = findPaymybuddyUserDetailsService
					.findByEmail(loggedUserEmail);

			Set<PaymybuddyUserDetails> connectionSet = new HashSet<>();
			if((currentUser.getMyconnection())!= null) {
			connectionSet = currentUser.getMyconnection();
			
			}
			LOGGER.debug(connectionSet);
			connectionSet.add(userToAdd);

			currentUser.setMyconnection(Set.copyOf(connectionSet));
			LOGGER.debug(currentUser);
			
			savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(currentUser);
			LOGGER.info(viewConnection + " added friend connection's list");
			result = new ModelAndView("redirect:/addconnection?success=true");

			
		} else {
			LOGGER.info(viewConnection + " is not  registered");
			result = new ModelAndView("redirect:/addconnection?error=true");
		}

		return result;

	}

}
