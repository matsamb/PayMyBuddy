package com.paymybuddy.controller;

import java.util.HashSet;
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

	final static Logger LOGGER = LogManager.getLogger("AddConnectionController");

	@Autowired
	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@Autowired
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@Autowired
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

	//@Autowired
	//AddConnectionService addConnectionService;

	// @Autowired
	// CheckUsersService checkUsersService;

	
	 AddConnectionController(//AddConnectionService addConnectionService
			 //,CheckUsersService checkUsersService 
			 FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService 
			 ,SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService	
			 ,FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService
			 ){ 
		// this.addConnectionService = addConnectionService; 
		// this.checkUsersService = checkUsersService;
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
		LOGGER.info(viewConnection);
		LOGGER.info("addconnection page displayed and connection posted");
		LOGGER.info(findPaymybuddyUserDetailsService.findByEmail(viewConnection.getConnection()).getEmail());
		if (findPaymybuddyUserDetailsService.findByEmail(viewConnection.getConnection()).getEmail() != "N_A") {
			LOGGER.info("User "+viewConnection.getConnection()+" found");

			PaymybuddyUserDetails foundUser = findPaymybuddyUserDetailsService
					.findByEmail(viewConnection.getConnection());

			if (auth instanceof UsernamePasswordAuthenticationToken) {
				
				LOGGER.info("User "+viewConnection.getConnection()+" is instance of UsernamePasswordAuthenticationToken");

				String email = auth.getName();
				
				PaymybuddyUserDetails currentUser = findPaymybuddyUserDetailsService
														.findByEmail(email);

				Set<PaymybuddyUserDetails> connectionSet = currentUser.getMyconnection();
				connectionSet.add(foundUser);

				currentUser.setMyconnection(Set.copyOf(connectionSet));
				LOGGER.info(currentUser);

				savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(currentUser);
				LOGGER.info(viewConnection + " added friend connection's list");
				result = new ModelAndView("redirect:/addconnection?success=true");

			} else {

				LOGGER.info("User "+viewConnection.getConnection()+" is instance of OAuth2AuthenticationToken");
				
				String nameNumber = auth.getName();
				PaymybuddyUserDetails foundOauth2 = findOauth2PaymybuddyUserDetailsService.findByName(nameNumber);
				String email = foundOauth2.getEmail();

				
				PaymybuddyUserDetails currentUser = findPaymybuddyUserDetailsService
						.findByEmail(email);

				Set<PaymybuddyUserDetails> connectionSet = new HashSet<>();
				connectionSet = currentUser.getMyconnection();
				LOGGER.info(connectionSet);
				connectionSet.add(foundUser);

				currentUser.setMyconnection(Set.copyOf(connectionSet));
				LOGGER.info(currentUser);
				
				savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(currentUser);
				LOGGER.info(viewConnection + " added friend connection's list");
				result = new ModelAndView("redirect:/addconnection?success=true");

			}
		} else {
			LOGGER.debug(viewConnection + " is not  registered");
			result = new ModelAndView("redirect:/addconnection?error=true");
		}

		return result;

	}

}
