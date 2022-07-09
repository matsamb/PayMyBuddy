package com.paymybuddy.controller;

import java.security.Principal;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.entity.GoogleOAuth2User;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.PaymybuddyUserDetailsService;
import com.paymybuddy.service.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

import lombok.AllArgsConstructor;

//@RestController
@Controller
@AllArgsConstructor
public class OAuth2Controller {

	private final Logger LOGGER = LogManager.getLogger("OAuth2Controller");

	@Autowired
	OAuth2AuthorizedClientService authorizedClientService;

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
	public /*ResponseEntity<String>*/String getGitHub(OAuth2AuthenticationToken authentication) {

		String email = authentication.getPrincipal().getAttribute("email");

		//if (findPaymybuddyUserDetailsService.findByEmail(email).getEmail() != email) {

			PaymybuddyUserDetails buddyUserDetails = new PaymybuddyUserDetails();
			GoogleOAuth2User googleOAuth2User = new GoogleOAuth2User();
			
			buddyUserDetails.setEmail(email);
			googleOAuth2User.setEmail(email);
			buddyUserDetails.setName(authentication.getName());
			
			PasswordEncoder passWordEncoder = paymybuddyPasswordEncoder.getPasswordEncoder();
			String pass = passWordEncoder.encode(authentication.getName());// .substring(8);
			buddyUserDetails.setPassword(pass);// pass
			//googleOAuth2User.setName(pass);
						
			buddyUserDetails.setUserRole(UserRole.USER);
			buddyUserDetails.setEnabled(true);
			//buddyUserDetails.setBalance(0.0f);

			savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(buddyUserDetails);

			//return ResponseEntity.ok("You have been signed in");
			return "/oauth2";
		//}		
		
		//paymybuddyUserDetailsService.loadUserByUsername(email);
		//return ResponseEntity.ok("You have been logged in");
		//return "/oauth2";
	}

/*	@RequestMapping("/userinfo")
	public String userinfo(OAuth2AuthenticationToken authentication) {
		// authentication.getAuthorizedClientRegistrationId() returns the
		// registrationId of the Client that was authorized during the Login flow
		OAuth2AuthorizedClient authorizedClient = this.authorizedClientService
				.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		System.out.println(authentication.getCredentials());
		String accessToken = authorizedClient.getAccessToken().getTokenValue();

		return authentication.getPrincipal().getAttribute("email") + "____" + authentication.getName() + "__ATOK::"
				+ accessToken;
	}*/

}
