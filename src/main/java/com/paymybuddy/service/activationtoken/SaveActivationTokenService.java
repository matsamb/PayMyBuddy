package com.paymybuddy.service.activationtoken;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.ActivationToken;
import com.paymybuddy.repository.ActivationTokenRepository;

@Service
public class SaveActivationTokenService {

	public static Logger logger = LogManager.getLogger("ActivationTokenService");	
	
	@Autowired
	private ActivationTokenRepository activationTokenRepository;
	
	SaveActivationTokenService (ActivationTokenRepository activationTokenRepository){
		this.activationTokenRepository = activationTokenRepository;
	}
	
	public void saveActivationToken(ActivationToken token) {
		activationTokenRepository.save(token);
	}
	
}
