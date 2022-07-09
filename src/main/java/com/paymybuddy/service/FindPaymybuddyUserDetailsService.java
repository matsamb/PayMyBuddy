package com.paymybuddy.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.StandardClaimAccessor;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;

@Service
public class FindPaymybuddyUserDetailsService {

	@Autowired
	PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository;

	public PaymybuddyUserDetails findByEmail(String email) {

		PaymybuddyUserDetails user = paymybuddyUserDetailsRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
		
		return user;
	}
	

	
}
