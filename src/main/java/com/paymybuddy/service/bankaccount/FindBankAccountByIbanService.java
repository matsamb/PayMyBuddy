package com.paymybuddy.service.bankaccount;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.repository.BankAccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindBankAccountByIbanService {

	private static final Logger LOGGER = LogManager.getLogger("FindBankAccountByIbanService");

	@Autowired
	BankAccountRepository bankAccountRepository;
	
	public EbankAccount findBankAccountByIban(String iban) {

		EbankAccount bankAccount = new EbankAccount("N_A") ;
		LOGGER.info(bankAccountRepository.findById(iban));
		
		if(bankAccountRepository.findById(iban).isEmpty()) {
			LOGGER.info("No bank account registered with iban: "+iban);
		}else {
			LOGGER.info("bank account found for iban: "+iban);

			bankAccount = bankAccountRepository.findById(iban).get();
		}
		LOGGER.info(bankAccount);
		return bankAccount;
	}
	
	
	
	
}
