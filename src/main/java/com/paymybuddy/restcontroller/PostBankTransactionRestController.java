package com.paymybuddy.restcontroller;

import java.sql.Timestamp;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.dto.JsonTransaction;
import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.bankaccount.FindBankAccountByIbanService;
import com.paymybuddy.service.transfer.FindTransactionByBankAccountService;
import com.paymybuddy.service.transfer.SaveTransferService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;

@RestController
@RolesAllowed("USER")
public class PostBankTransactionRestController {

	private static final Logger LOGGER = LogManager.getLogger("PostBankTransactionRestController");

	@Autowired
	FindBankAccountByIbanService findBankAccountByIbanService;

	@Autowired
	SaveTransferService saveTransferService;

	@Autowired
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@Autowired
	FindTransactionByBankAccountService findTransactionByBankAccountService;

	@PostMapping("/transactions") // ?iban=<iban>
	public ResponseEntity<JsonTransaction> addBankTransaction(@RequestBody JsonTransaction jsonTransaction) {

		LOGGER.info("Post method, inbound transaction");
		LOGGER.info("Request body: " + jsonTransaction);
		Float fee;
		Float feeRate = 0.05f;
		Float availableBalance;

		if (Objects.isNull(jsonTransaction)) {
			LOGGER.error("Posted person request body is empty");
			return ResponseEntity.noContent().build();
		} else if (findBankAccountByIbanService.findBankAccountByIban(jsonTransaction.getBankAccount().getIban())
				.getUser().getEmail() != "N_A") {

			LOGGER.info("IBAN: " + jsonTransaction.getBankAccount().getIban() + " found");

			EbankAccount ebankAccount = findBankAccountByIbanService 
					.findBankAccountByIban(jsonTransaction.getBankAccount().getIban());
			LOGGER.info("Found :" + ebankAccount);

			int count = 0;
			for (Etransaction t : findTransactionByBankAccountService
					.findTransactionByBankAccount(ebankAccount)) {
				
				if(Objects.equals(t.getBankTransactionId(),jsonTransaction.getTransactionId())) {
					LOGGER.info("Transaction all ready uploaded to database");
					count++;				
				}
			}	
			if(count<1) {
					LOGGER.info("Uploading transaction to database");
					
					PaymybuddyUserDetails user = ebankAccount.getUser();
					availableBalance = user.getBalance();
					LOGGER.info("Balance before update :" + availableBalance);
					availableBalance = availableBalance + jsonTransaction.getAmount();
					LOGGER.info("Balance after update :" + availableBalance);
					user.setBalance(availableBalance);
					ebankAccount.setUser(user);
					LOGGER.info("Updated user :" + user);

					Etransaction etransaction = new Etransaction();
					Timestamp time = new Timestamp(System.currentTimeMillis());

					etransaction.setDate(time);
					etransaction.setBankTransactionId(jsonTransaction.getTransactionId());
					etransaction.setFromBank(true);
					etransaction.setAmount(jsonTransaction.getAmount());
					etransaction.setDescription(jsonTransaction.getDescription());

					fee = jsonTransaction.getAmount() * feeRate;

					etransaction.setFee(fee);
					etransaction.setBankAccount(ebankAccount);
					LOGGER.info("Posted transaction :" + etransaction);

					savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(user);
					saveTransferService.saveTransfer(etransaction);
			}
			
			return ResponseEntity.ok(jsonTransaction);

		} else {
			LOGGER.error("IBAN: " + jsonTransaction.getBankAccount().getIban() + " not registered");
			return ResponseEntity.notFound().build();

		}
	}

}
