package com.paymybuddy.service.users;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindOauth2PaymybuddyUserDetailsService {

	private final Logger LOGGER = LogManager.getLogger("FindOauth2PaymybuddyUserDetailsService");

	@Autowired
	PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository;

	public PaymybuddyUserDetails findByName(String nameNumber) {
		List<PaymybuddyUserDetails> user = paymybuddyUserDetailsRepository.findAll();
		PaymybuddyUserDetails result = new PaymybuddyUserDetails("N_A");

		if (user.isEmpty()) {
			LOGGER.info("User not found");

		} else {
			for (PaymybuddyUserDetails p : user) {
				if (Objects.equals(p.getName(), nameNumber)) {
					result = p;
					LOGGER.info("User "+result.getEmail()+" found");
				}
			}

		}
		return result;
	}

}
