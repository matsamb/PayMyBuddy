package com.paymybuddy.service.activationtoken;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.ActivationToken;
import com.paymybuddy.repository.ActivationTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindActivationTokenByTokenService {

	private static Logger logger = LogManager.getLogger("FindActivationTokenService");
	
	@Autowired
	ActivationTokenRepository activationTokenRepository;

	public ActivationToken findByToken(String token) {

		logger.info("token "+token+" found");
		
		return activationTokenRepository.findByToken(token);
	}
	
	
	
	
}
