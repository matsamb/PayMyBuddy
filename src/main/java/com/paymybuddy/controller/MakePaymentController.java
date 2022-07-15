package com.paymybuddy.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.Users;
import com.paymybuddy.dto.ViewPayment;
import com.paymybuddy.entity.Epayment;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.connection.FindFconnectionByPayerUsernameService;
import com.paymybuddy.service.payment.SavePaymentService;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;

import lombok.AllArgsConstructor;

@RolesAllowed("USER")
@Controller
@AllArgsConstructor
public class MakePaymentController {
//TODO : payment status in case of new non validated connection

	private final static Logger LOGGER = LogManager.getLogger("MakePaymentController");

	@Autowired
	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@Autowired
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

	@Autowired
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@Autowired
	FindFconnectionByPayerUsernameService findFconnectionByPayerUsernameService;

	@Autowired
	SavePaymentService savePaymentServiceAtMakePaymentController;

	@GetMapping("/makepayment")
	public String getAddConnection(ViewPayment payment, Users euser, BindingResult bindingResult, Authentication auth,
			Model model) {

		LOGGER.info("Payment initiated");
		
		Epayment ePayment = new Epayment();

		List<PaymybuddyUserDetails> connectionList = new ArrayList<>();

		String email;

		if (auth instanceof UsernamePasswordAuthenticationToken) {
			LOGGER.info(auth.getName() + " is instance of UsernamePasswordAuthenticationToken");
			email = auth.getName();
		} else {
			LOGGER.info(auth.getName() + " is instance of OAuth2AuthenticationToken");
			String nameNumber = auth.getName();
			PaymybuddyUserDetails foundOauth2 = findOauth2PaymybuddyUserDetailsService.findByName(nameNumber);
			email = foundOauth2.getEmail();
		}

		if (findFconnectionByPayerUsernameService.findByPayerUsername(email).get(0).getEmail() == "N_A") {
			LOGGER.info("No connection for " + email);
		} else {
			LOGGER.info("Connection found for " + email);
			connectionList.addAll(findFconnectionByPayerUsernameService.findByPayerUsername(email));
		
	/*		for (PaymybuddyUserDetails c : connectionList) {
				if (Objects.equals(c.getEmail(), email)) {

					Timestamp u = new Timestamp(System.currentTimeMillis());

					ePayment.setPayeeEmail(c.getEmail());
					ePayment.setPaymentDate(u);
					ePayment.setAmount(payment.getAmount());
					ePayment.setDescription(payment.getDescription());

					ePayment.setPayerEmail(email);
					LOGGER.info("New payment for " + email);
					LOGGER.info(ePayment);

					PaymybuddyUserDetails updatePayerBalance = findPaymybuddyUserDetailsService
							.findByEmail(ePayment.getPayerEmail());
					Float oldPayerBalance = updatePayerBalance.getBalance();
					updatePayerBalance.setBalance(oldPayerBalance - ePayment.getAmount());
					LOGGER.info(updatePayerBalance);

					PaymybuddyUserDetails updatePayeeBalance = findPaymybuddyUserDetailsService
							.findByEmail(ePayment.getPayerEmail());
					Float oldPayeeBalance = updatePayeeBalance.getBalance();
					updatePayeeBalance.setBalance(oldPayeeBalance + ePayment.getAmount());
					LOGGER.info(updatePayeeBalance);

					savePaymentServiceAtMakePaymentController.savePayment(ePayment);
					savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(updatePayeeBalance);
					savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(updatePayerBalance);

				}
			}*/
		
		}

		LOGGER.info(connectionList);
		model.addAttribute("econnections", connectionList);

		LOGGER.info(connectionList);

		return "/makepayment";

	}

	@PostMapping("/makepayment")
	public ModelAndView createConnection(ViewPayment payment, Users euser, BindingResult bindingResult,
			Authentication auth, Model model) {

		LOGGER.info("post method");

		ModelAndView result = null;

		Epayment ePayment = new Epayment();

		List<PaymybuddyUserDetails> connectionList = new ArrayList<>();
		String payerEmail;

		if (auth instanceof UsernamePasswordAuthenticationToken) {
			LOGGER.info(auth.getName() + " is instance of UsernamePasswordAuthenticationToken");
			payerEmail = auth.getName();
		} else {
			LOGGER.info(auth.getName() + " is instance of OAuth2AuthenticationToken");
			String nameNumber = auth.getName();
			PaymybuddyUserDetails foundOauth2 = findOauth2PaymybuddyUserDetailsService.findByName(nameNumber);
			payerEmail = foundOauth2.getEmail();
		}

		if (findFconnectionByPayerUsernameService.findByPayerUsername(payerEmail).get(0).getEmail()=="N_A") {
			LOGGER.info(payerEmail+" not registered");
			result = new ModelAndView("redirect:/makepayment?error=true");
		} else {
			LOGGER.info(payerEmail+" registered");
			connectionList.addAll(findFconnectionByPayerUsernameService.findByPayerUsername(payerEmail));

			for (PaymybuddyUserDetails c : connectionList) {
				LOGGER.info(payment.getConnection()+" compare "+c.getEmail());
				if (Objects.equals(c.getEmail(), payment.getConnection())) {

					Timestamp u = new Timestamp(System.currentTimeMillis());
					LOGGER.info(u);
					ePayment.setPayeeEmail(c.getEmail());
					ePayment.setPaymentDate(u);
					ePayment.setAmount(payment.getAmount());
					ePayment.setDescription(payment.getDescription());

					ePayment.setPayerEmail(payerEmail);
					LOGGER.info("New payment for " + payerEmail);
					LOGGER.info(ePayment);

					PaymybuddyUserDetails updatePayerBalance = findPaymybuddyUserDetailsService
							.findByEmail(ePayment.getPayerEmail());
					Float oldPayerBalance = updatePayerBalance.getBalance();
					updatePayerBalance.setBalance(oldPayerBalance - ePayment.getAmount());
					LOGGER.info(updatePayerBalance);

					PaymybuddyUserDetails updatePayeeBalance = findPaymybuddyUserDetailsService
							.findByEmail(ePayment.getPayeeEmail());
					Float oldPayeeBalance = updatePayeeBalance.getBalance();
					updatePayeeBalance.setBalance(oldPayeeBalance + ePayment.getAmount());
					LOGGER.info(updatePayeeBalance);

					savePaymentServiceAtMakePaymentController.savePayment(ePayment);
					savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(updatePayeeBalance);
					savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(updatePayerBalance);

				}
			}

			result = new ModelAndView("redirect:/makepayment?success=true");

		}

		model.addAttribute("econnections", connectionList);

		System.out.println(payment.getDescription() + " and " + payment.getConnection());

		return result;

	}

}
