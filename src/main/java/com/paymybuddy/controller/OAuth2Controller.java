package com.paymybuddy.controller;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.paymybuddy.entity.GoogleOAuth2User;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.GoogleOAuth2UserRepository;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
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
	OAuth2AuthorizedClientService rizedClientService;
	
	@Autowired
	GoogleOAuth2UserRepository googleOAuth2UserRepository;
	
	@Autowired
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

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
	public String getOauth2(OAuth2AuthenticationToken authentication, Authentication oth, Principal user) {

		String result;
		
		LOGGER.info("Oauth2 details "+authentication);
		
		String email = authentication.getPrincipal().getAttribute("email");
		String nameNumber = oth.getName();
		
		LOGGER.info(nameNumber);
		OAuth2AuthenticationToken authKen = (OAuth2AuthenticationToken) user;
		
		OAuth2AuthorizedClient rizedClient =
				this.rizedClientService.loadAuthorizedClient(authKen.getAuthorizedClientRegistrationId()
						,authKen.getName());
		
/*		LOGGER.info(oth.getName() + " is instance of OAuth2AuthenticationToken");
		String nameNumber = oth.getName();
		PaymybuddyUserDetails foundOauth2 = findOauth2PaymybuddyUserDetailsService.findByName(nameNumber);
		String othEmail = foundOauth2.getEmail();
		LOGGER.info(othEmail);*/

//		if (findPaymybuddyUserDetailsService.findByEmail(email).getEmail() == "N_A") {
			LOGGER.info("User not registered, loading into database initiated");

			PaymybuddyUserDetails buddyUserDetails = new PaymybuddyUserDetails();
			GoogleOAuth2User OauthUser = new GoogleOAuth2User();
			LOGGER.info(buddyUserDetails);
			LOGGER.info(email);
			buddyUserDetails.setEmail(email);
			OauthUser.setEmail(email);
			buddyUserDetails.setName(authentication.getName());
			LOGGER.info(buddyUserDetails);

			PasswordEncoder passWordEncoder = paymybuddyPasswordEncoder.getPasswordEncoder();
			String pass = passWordEncoder.encode(authentication.getName());// .substring(8);
			buddyUserDetails.setPassword(pass);// pass

			buddyUserDetails.setBalance(0f);
			LOGGER.info(buddyUserDetails);

			buddyUserDetails.setUserRole(UserRole.USER);
			buddyUserDetails.setEnabled(true);

			LOGGER.info(buddyUserDetails);
			googleOAuth2UserRepository.save(OauthUser);
			savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(buddyUserDetails);

			result = "/oauth2";
		
/*		} else {
			
			LOGGER.info("User registered, redirected to home page");
			result = "redirect:/home?size=3&page=1";
		
		}*/
		return result;
	}
	
	@GetMapping("/userinfo")
	public String userinfo(OAuth2AuthenticationToken authentication) {
		// authentication.getAuthorizedClientRegistrationId() returns the
		// registrationId of the Client that was authorized during the Login flow
		OAuth2AuthorizedClient authorizedClient = rizedClientService
				.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		System.out.println(authentication.getCredentials());
		String accessToken = authorizedClient.getAccessToken().getTokenValue();

		return authentication.getPrincipal().getAttribute("email") + "____" + authentication.getName() + "__ATOK::"
				+ accessToken;
	}

}
