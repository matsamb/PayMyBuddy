package com.paymybuddy.service.connection;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;

@Service
public class FindFconnectionByPayerUsernameService {

	private final static Logger LOGGER = LogManager.getLogger("FindFconnectionByPayerUsernameService");
	
	@Autowired
	PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository;
	
	FindFconnectionByPayerUsernameService(PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository){
		this.paymybuddyUserDetailsRepository = paymybuddyUserDetailsRepository;
	}

	public List<PaymybuddyUserDetails> findByPayerUsername(String fkPayerUsername) {
		if(paymybuddyUserDetailsRepository.findByEmail(fkPayerUsername).get().getMyconnection().isEmpty()) {
			
			LOGGER.info("No connection for "+fkPayerUsername);
			
			PaymybuddyUserDetails details = new PaymybuddyUserDetails("N_A");
			List<PaymybuddyUserDetails> detailsList = new ArrayList<>();
			detailsList.add(details);
			
			LOGGER.debug(detailsList);

			return detailsList;
		}else {
			
			LOGGER.info("Connections found for "+fkPayerUsername);
			LOGGER.debug(paymybuddyUserDetailsRepository.findByEmail(fkPayerUsername).get().getMyconnection());
			
			return paymybuddyUserDetailsRepository.findByEmail(fkPayerUsername).get().getMyconnection().stream().toList();
		}
	}
	
	
	
}
