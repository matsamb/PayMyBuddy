package com.paymybuddy.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.dto.JsonBankAccount;
import com.paymybuddy.dto.JsonTransaction;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.bankaccount.FindBankAccountByIbanService;
import com.paymybuddy.service.transfer.FindAllTransactionsService;
import com.paymybuddy.service.transfer.FindTransactionByBankAccountService;
import com.paymybuddy.service.transfer.SaveTransferService;

import lombok.AllArgsConstructor;

@RestController
@RolesAllowed("USER")
@AllArgsConstructor
public class GetBankTransactionRestController {

	private static final Logger LOGGER = LogManager.getLogger("GetBankTransactionRestController");
	
/*	@Autowired
	FindTransactionByBankAccountService findTransactionByBankAccountService;
	
	@Autowired
	FindBankAccountByIbanService findBankAccountByIbanService;
*/	
	@Autowired
	FindAllTransactionsService findAllTransactionsService;
	
	@Autowired
	SaveTransferService saveTransferService;
	
	@GetMapping("/transactions")
	public Iterable<JsonTransaction> getPendingTransactions(){
		
		List<JsonTransaction> JsonTransactionList = new ArrayList<>();

		LOGGER.info("returned list: "+findAllTransactionsService.findAllTransactions());
		
		if(findAllTransactionsService.findAllTransactions().get(0).getBankTransactionId()!=-5) {
			
			LOGGER.info("Transaction list size: "+findAllTransactionsService.findAllTransactions().size());
			JsonTransaction jsonTransaction = new JsonTransaction();
			JsonBankAccount jsonbankAccount = new JsonBankAccount();
			for(Etransaction e: findAllTransactionsService.findAllTransactions()) {
				if(e.getBankTransactionId()==-3 && e.getFromBank()==false) {
					jsonTransaction.setTransactionId(e.getTransactionId());
					jsonTransaction.setAmount(e.getAmount());
					jsonTransaction.setDescription(e.getDescription()); 
					
					jsonbankAccount.setIban(e.getBankAccount().getIban());
					LOGGER.info("current bank account "+e.getBankAccount());
					LOGGER.info("current user bank account "+e.getBankAccount().getUser());

					jsonbankAccount.setUserEmail("buddyApp");
					
					jsonTransaction.setBankAccount((JsonBankAccount) jsonbankAccount.clone());
					e.setBankTransactionId(-1);
					
					LOGGER.info("Adding :"+jsonTransaction+" to list");
					saveTransferService.saveTransfer(e);
					JsonTransactionList.add((JsonTransaction)jsonTransaction.clone());

				}
				}
		}
		
		LOGGER.info("Returned List size: "+JsonTransactionList.size());
		
		return JsonTransactionList;
				
	}
	
/*	@GetMapping("/banktransaction")//?iban=<iban>
	public Iterable<Etransaction> getIbanTransactions(@RequestParam String iban){
		
		return findTransactionByBankAccountService.findTransactionByBankAccount(
				findBankAccountByIbanService.findBankAccountByIban(iban));
		
	}*/

	
}
