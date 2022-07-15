package com.paymybuddy.service.users;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.StandardClaimAccessor;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;

@Service
public class FindPaymybuddyUserDetailsService {

	private final Logger LOGGER = LogManager.getLogger("FindPaymybuddyUserDetailsService");
	
	@Autowired
	PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository;

	public PaymybuddyUserDetails findByEmail(String email) {
		
		PaymybuddyUserDetails user = paymybuddyUserDetailsRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + " not found"));

		if (paymybuddyUserDetailsRepository.findByEmail(email).isEmpty()) {
			LOGGER.info("User "+email+" not found");	
			return new PaymybuddyUserDetails("N_A");	
		}
		LOGGER.info("User "+email+" found");
		return user;
	}
	
}
