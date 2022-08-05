package com.paymybuddy.service.transfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FindTransactionByBankAccountService {

	private static final Logger LOGGER = LogManager.getLogger("FindTransactionByBankAccountService");

	@Autowired
	TransactionRepository transactionRepository;
	
	public List<Etransaction> findTransactionByBankAccount(EbankAccount a) {
		
		List<Etransaction> result = new ArrayList<>();
		EbankAccount account = new EbankAccount("N_A");
		Etransaction transaction = new Etransaction(account);
		
		LOGGER.info("size of transaction list "+transactionRepository.findAll().size());
		if(transactionRepository.findAll().isEmpty()) {
			LOGGER.info("No transaction found");
			result.add(transaction);
		}else {
			LOGGER.info("Transaction found");
			for(Etransaction t: transactionRepository.findAll()) {
				LOGGER.info("Looping transaction list");
				if(Objects.equals(a.getIban(),t.getBankAccount().getIban())) {
					LOGGER.info("Transaction found for :"+a);
					result.add(t);
				}
			}
		}
		if(result.isEmpty()) {
			LOGGER.info("No transaction found for :"+a);
			result.add(transaction);
		}

		return result;
	}
	
	
	
}
