package com.paymybuddy.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.users.PaymybuddyUserDetailsService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger LOGGER = LogManager.getLogger("CustomAuthenticationProvider");
	
	@Autowired
	PaymybuddyUserDetailsService paymybuddyUserDetailsService;
	
	
	//GoogleOAuth2User googleOAuth2User;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String username = (authentication.getPrincipal() == null) ? "NotOAuth2User" : authentication.getName();

		LOGGER.info("start anthentication");
		if (username.isEmpty()) {
			LOGGER.info("invalid login details");
			throw new BadCredentialsException("invalid login details");
		}
		// get user details using Spring security user details service
		UserDetails user = new PaymybuddyUserDetails();
		try {
			user = paymybuddyUserDetailsService.loadUserByUsername(username);
			LOGGER.info("loading user");
		} catch (UsernameNotFoundException exception) {
			LOGGER.info("user not found");
			throw new BadCredentialsException("invalid login details");
		}
		return createSuccessfulAuthentication(authentication, user);
	}

	private Authentication createSuccessfulAuthentication( Authentication authentication, UserDetails user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),
				authentication.getCredentials(), user.getAuthorities());
		token.setDetails(authentication.getDetails());
		LOGGER.info(token);
		return token;
	}

	

	@Override
	public boolean supports(Class<?> authentication) {
		authentication.equals(ExternalServiceAuthenticationToken.class);
		return false;
	}

}
