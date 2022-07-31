package com.paymybuddy.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.users.UserRole;

public class WithMockPayMyBuddyUserSecurityContextFactory 
	implements WithSecurityContextFactory<WithMockPayMyBuddyUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockPayMyBuddyUser annotation) {
			SecurityContext context = SecurityContextHolder.createEmptyContext();

			PaymybuddyUserDetails principal =
				new PaymybuddyUserDetails(annotation.email());
			Authentication auth =
				new UsernamePasswordAuthenticationToken(principal, "pass", principal.getAuthorities());
			context.setAuthentication(auth);
			return context;

	}
	
	
	
}
