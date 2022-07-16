package com.paymybuddy.service.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;

@Service
public class FindPaymybuddyUserDetailsService {

	private static final Logger LOGGER = LogManager.getLogger("FindPaymybuddyUserDetailsService");

	@Autowired
	PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository;

	public PaymybuddyUserDetails findByEmail(String email) {

		PaymybuddyUserDetails user = new PaymybuddyUserDetails("N_A");
		LOGGER.info(paymybuddyUserDetailsRepository.findByEmail(email).isPresent());

		if (paymybuddyUserDetailsRepository.findByEmail(email).isPresent()==false) {
			LOGGER.info("User " + email + " not found");
			return user;
		} else {
			LOGGER.info("User " + email + " found");
			user = paymybuddyUserDetailsRepository.findByEmail(email).get();
			return user;
		}
	}

}
