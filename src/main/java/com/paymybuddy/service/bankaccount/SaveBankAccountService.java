package com.paymybuddy.service.bankaccount;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.repository.BankAccountRepository;

@Service
public class SaveBankAccountService {

	private static final Logger LOGGER = LogManager.getLogger("SaveBankAccountService");
	
	@Autowired
	BankAccountRepository bankAccountRepository;

	public void saveBankAccount(EbankAccount currentUserBankAccount) {
		LOGGER.info("saving "+currentUserBankAccount.getUser().getEmail()+" bank account");		
		bankAccountRepository.save(currentUserBankAccount);
	}
	
	
}
