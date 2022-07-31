package com.paymybuddy.service.bankaccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.repository.BankAccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindBankAccountByUserEmailService {

	private static final Logger LOGGER = LogManager.getLogger("FindBankAccountByUserEmailService");

	@Autowired
	BankAccountRepository bankAccountRepository;
	
	public List<EbankAccount> findBankAccountByUserEmail(String loggedUserEmail) {

		List<EbankAccount> result = new ArrayList<>();
		EbankAccount bankAccount = new EbankAccount("N_A") ;
		LOGGER.info(bankAccountRepository.findAll());
		
		if(bankAccountRepository.findAll().isEmpty()) {
			LOGGER.info("No bank account registered");
			result.add(bankAccount);
		}else {
			LOGGER.info("bank account found");
			for(EbankAccount e : bankAccountRepository.findAll()) {
				LOGGER.info("looping bank account list");
				if(Objects.equals(e.getUser().getEmail(),loggedUserEmail)) {
					LOGGER.info(e+" "+loggedUserEmail);
					bankAccount = e;
					result.add(/*bankAccount*/(EbankAccount)bankAccount.clone());
				}
			}
			if(result.isEmpty()) {
				LOGGER.info("No bank account registered for: "+loggedUserEmail);
				result.add(bankAccount);
			}
			
		}
		LOGGER.info(result);
		return result;
	}
	
}
