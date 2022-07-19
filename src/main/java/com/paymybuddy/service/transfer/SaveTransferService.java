package com.paymybuddy.service.transfer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.repository.TransactionRepository;

@Service
public class SaveTransferService {

	private static final Logger LOGGER = LogManager.getLogger("SaveTransferService");
	
	@Autowired
	TransactionRepository transactionRepository;
	
	public void saveTransfer(Etransaction transfer) {
		LOGGER.info("loading "+transfer+" into database");
		transactionRepository.save(transfer);
		
	}

	
	
}
