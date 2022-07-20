package com.paymybuddy.service.transfer;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.dto.JsonBankAccount;
import com.paymybuddy.dto.JsonTransaction;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.repository.TransactionRepository;

@Service
public class FindAllTransactionsService {

	private static final Logger LOGGER = LogManager.getLogger("FindAllTransactionsService");
	
	@Autowired
	TransactionRepository transactionRepository;

	public List<Etransaction> findAllTransactions() {
		

		LOGGER.info("Dowloading inbound and outbound bank transactions from database");
		if(transactionRepository.findAll().size()>0) {
			LOGGER.info("Transaction list size: "+transactionRepository.findAll().size());
			return transactionRepository.findAll();		
		}else {
			LOGGER.info("No transaction found");
			List<Etransaction> JsonTransactionList = new ArrayList<>();
			Etransaction t = new Etransaction(-5);
			JsonTransactionList.add(t);
			return JsonTransactionList;

		}
		
	}
	
	
}
