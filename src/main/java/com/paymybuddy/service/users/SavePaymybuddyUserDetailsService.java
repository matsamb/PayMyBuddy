package com.paymybuddy.service.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;

@Service
public class SavePaymybuddyUserDetailsService {

	private static Logger logger = LogManager.getLogger("SavePaymybuddyUserDetailsService");
	
	@Autowired
	PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository;
	
	SavePaymybuddyUserDetailsService(PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository){
		this.paymybuddyUserDetailsRepository = paymybuddyUserDetailsRepository;
	}

	public void savePaymybuddyUserDetails(PaymybuddyUserDetails buddyUser) {
		paymybuddyUserDetailsRepository.save(buddyUser);
		logger.info("Saving "+buddyUser.getEmail()+" user details");
	}
	
}
