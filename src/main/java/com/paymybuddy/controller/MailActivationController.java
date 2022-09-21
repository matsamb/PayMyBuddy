package com.paymybuddy.controller;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.paymybuddy.entity.ActivationToken;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.activationtoken.FindActivationTokenByTokenService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MailActivationController {

	private final Logger LOGGER = LogManager.getLogger("MailActivationRestController");

	@Autowired
	FindActivationTokenByTokenService findActivationTokenByTokenService;

	@Autowired
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@GetMapping("accountactivation") // ?token=<token>
	public String updatePaymybuddyUserDetails(@RequestParam String token) {

		ActivationToken activationToken	= findActivationTokenByTokenService.findByToken(token);
		LOGGER.info("account validation token " + token + " found");
		
		Timestamp activationTime = new Timestamp(System.currentTimeMillis());

		if (activationToken.getExpirationTime().compareTo(activationTime) > 0
				&& activationToken.getUser().getEnabled() == false) {
			
			PaymybuddyUserDetails updatedPaymybuddyUserDetails = activationToken.getUser();
			updatedPaymybuddyUserDetails.setEnabled(true);
			
			savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(updatedPaymybuddyUserDetails);
			LOGGER.info(updatedPaymybuddyUserDetails.getEmail() + " account enabled");
			
			return "accountactivation";
		}
		
		LOGGER.info("token " + token + " expired");
		return "redirect:/tokenexpired";

	}

}
