package com.paymybuddy.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.Users;
import com.paymybuddy.dto.ViewPayment;
import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.Epayment;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.bankaccount.FindBankAccountByUserEmailService;
import com.paymybuddy.service.connection.FindFconnectionByPayerUsernameService;
import com.paymybuddy.service.payment.SavePaymentService;
import com.paymybuddy.service.transfer.SaveTransferService;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;

@RolesAllowed("USER")
@Controller
public class MakePaymentController {

	private final static Logger LOGGER = LogManager.getLogger("MakePaymentController");

	@Autowired
	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@Autowired
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

	@Autowired
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@Autowired
	FindBankAccountByUserEmailService findBankAccountByUserEmailService;

	@Autowired
	FindFconnectionByPayerUsernameService findFconnectionByPayerUsernameService;

	@Autowired
	SavePaymentService savePaymentServiceAtMakePaymentController;

	@Autowired
	SaveTransferService saveTransferService;

	MakePaymentController(FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService,
			SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService,
			FindBankAccountByUserEmailService findBankAccountByUserEmailService,
			FindFconnectionByPayerUsernameService findFconnectionByPayerUsernameService,
			SavePaymentService savePaymentServiceAtMakePaymentController, SaveTransferService saveTransferService,
			FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService) {
		this.findPaymybuddyUserDetailsService = findPaymybuddyUserDetailsService;
		this.savePaymybuddyUserDetailsService = savePaymybuddyUserDetailsService;
		this.findBankAccountByUserEmailService = findBankAccountByUserEmailService;
		this.findFconnectionByPayerUsernameService = findFconnectionByPayerUsernameService;
		this.savePaymentServiceAtMakePaymentController = savePaymentServiceAtMakePaymentController;
		this.saveTransferService = saveTransferService;
		this.findOauth2PaymybuddyUserDetailsService = findOauth2PaymybuddyUserDetailsService;
	}

	@GetMapping("/makepayment")
	public String getAddConnection(ViewPayment payment, Users euser/* , BindingResult bindingResult */,
			Authentication auth, Model model) {

		LOGGER.info("get method");

		List<PaymybuddyUserDetails> connectionList = new ArrayList<>();
		List<EbankAccount> bankAccountList = new ArrayList<>();

		String email;

		Float available = 0.0f;

		if (auth instanceof UsernamePasswordAuthenticationToken) {
			LOGGER.info(auth.getName() + " is instance of UsernamePasswordAuthenticationToken");
			email = auth.getName();

			available = findPaymybuddyUserDetailsService.findByEmail(email).getBalance();

		} else {
			LOGGER.info(auth.getName() + " is instance of OAuth2AuthenticationToken");
			String nameNumber = auth.getName();
			PaymybuddyUserDetails foundOauth2 = findOauth2PaymybuddyUserDetailsService.findByName(nameNumber);
			email = foundOauth2.getEmail();

			available = foundOauth2.getBalance();
		}

		if (findFconnectionByPayerUsernameService.findByPayerUsername(email).get(0).getEmail() == "N_A") {
			LOGGER.info("No connection for " + email);
		} else {
			LOGGER.info("Connection found for " + email);
			connectionList.addAll(findFconnectionByPayerUsernameService.findByPayerUsername(email));
		}

		if (findBankAccountByUserEmailService.findBankAccountByUserEmail(email).get(0).getIban() == "N_A") {
			LOGGER.info("No bank account " + email);
		} else {
			LOGGER.info("Iban found for " + email);
			bankAccountList.addAll(findBankAccountByUserEmailService.findBankAccountByUserEmail(email));
		}

		LOGGER.info(connectionList);

		model.addAttribute("bankaccounts", bankAccountList);
		model.addAttribute("econnections", connectionList);
		model.addAttribute("available", available);

		LOGGER.info(connectionList);

		return "/makepayment";

	}

	@PostMapping("/makepayment")
	public ModelAndView createConnection(ViewPayment payment, Users euser, BindingResult bindingResult,
			Authentication auth, Model model) {

		LOGGER.info("post method payment " + payment);
		LOGGER.info("post method");

		ModelAndView result = null;

		Epayment ePayment = new Epayment();
		Etransaction eTransaction = new Etransaction();

		List<PaymybuddyUserDetails> connectionList = new ArrayList<>();
		List<EbankAccount> bankAccountList = new ArrayList<>();

		String payerEmail;
		Timestamp u = new Timestamp(System.currentTimeMillis());
		Float availableBalance = 0.0f;

		Float transactionFee;
		Float paymentFee;
		Float feeRate = 0.05f;

		if (auth instanceof UsernamePasswordAuthenticationToken) {
			LOGGER.info(auth.getName() + " is instance of UsernamePasswordAuthenticationToken");
			payerEmail = auth.getName();

			availableBalance = findPaymybuddyUserDetailsService.findByEmail(payerEmail).getBalance();

		} else {
			LOGGER.info(auth.getName() + " is instance of OAuth2AuthenticationToken");
			String nameNumber = auth.getName();
			PaymybuddyUserDetails foundOauth2 = findOauth2PaymybuddyUserDetailsService.findByName(nameNumber);
			payerEmail = foundOauth2.getEmail();

			availableBalance = foundOauth2.getBalance();

		}

		PaymybuddyUserDetails payer = findPaymybuddyUserDetailsService.findByEmail(payerEmail);
		LOGGER.info("Payer details " + payer);

		LOGGER.info(payerEmail + " registered");
		connectionList.addAll(findFconnectionByPayerUsernameService.findByPayerUsername(payerEmail));
		bankAccountList.addAll(findBankAccountByUserEmailService.findBankAccountByUserEmail(payerEmail));

		for (EbankAccount a : bankAccountList) {
			LOGGER.info(payment.getConnection() + " compare " + a.getIban());
			if (Objects.equals(a.getIban(), payment.getConnection())) {
				LOGGER.info("bank transfer");

				u.setTime(System.currentTimeMillis());

				LOGGER.info(u);
				eTransaction.setAmount(payment.getAmount());
				eTransaction.setDate(u);
				eTransaction.setDescription(payment.getDescription());
				eTransaction.setFromBank(false);
				eTransaction.setBankTransactionId(-3);
				eTransaction.setBankAccount(a);
				LOGGER.info(eTransaction);

				LOGGER.info("balance before transfert :" + availableBalance);
				availableBalance = availableBalance - eTransaction.getAmount();
				LOGGER.info("balance after transfert :" + availableBalance);

				payer.setBalance(availableBalance);
				LOGGER.info("Updated payer's balance " + payer);

				transactionFee = eTransaction.getAmount() * feeRate;
				eTransaction.setFee(transactionFee);

				LOGGER.info("Loading transfer details " + eTransaction);
				saveTransferService.saveTransfer(eTransaction);

				savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(payer);

			}
		}

		for (PaymybuddyUserDetails c : connectionList) {
			LOGGER.info(payment.getConnection() + " compare " + c.getEmail());
			if (Objects.equals(c.getEmail(), payment.getConnection())) {
				LOGGER.info("internal app payment");

				u.setTime(System.currentTimeMillis());

				LOGGER.info(u);
				ePayment.setPayeeEmail(c.getEmail());
				ePayment.setPaymentDate(u);
				ePayment.setAmount(payment.getAmount());
				ePayment.setDescription(payment.getDescription());

				ePayment.setPayerEmail(payerEmail);
				LOGGER.info("New payment for " + payerEmail);
				LOGGER.info(ePayment);

				LOGGER.info("balance before payment :" + availableBalance);
				availableBalance = availableBalance - ePayment.getAmount();
				LOGGER.info("balance after payment :" + availableBalance);

				payer.setBalance(availableBalance);
				LOGGER.info("Updated payer's balance " + payer);

				PaymybuddyUserDetails updatePayeeBalance = findPaymybuddyUserDetailsService
						.findByEmail(ePayment.getPayeeEmail());
				LOGGER.info("payee's old balance " + updatePayeeBalance.getBalance());

				Float oldPayeeBalance;
				oldPayeeBalance = updatePayeeBalance.getBalance();

				updatePayeeBalance.setBalance(oldPayeeBalance + ePayment.getAmount());
				LOGGER.info("Updated payee's balance " + updatePayeeBalance);

				paymentFee = ePayment.getAmount() * feeRate;
				LOGGER.info(paymentFee);
				ePayment.setFee(paymentFee);

				savePaymentServiceAtMakePaymentController.savePayment(ePayment);
				savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(updatePayeeBalance);
				savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(payer);

			}
		}

		result = new ModelAndView("redirect:/makepayment?success=true");

		model.addAttribute("econnections", connectionList);
		model.addAttribute("available", availableBalance);

		return result;

	}

}
