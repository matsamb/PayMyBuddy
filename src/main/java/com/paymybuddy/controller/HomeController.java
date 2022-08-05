package com.paymybuddy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.paymybuddy.dto.ActivePage;
import com.paymybuddy.dto.ViewPayment;
import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.Epayment;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.bankaccount.FindBankAccountByUserEmailService;
import com.paymybuddy.service.connection.FindFconnectionByPayerUsernameService;
import com.paymybuddy.service.payment.FindPaymentByPayeeService;
import com.paymybuddy.service.payment.FindPaymentByPayerService;
import com.paymybuddy.service.transfer.FindTransactionByBankAccountService;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;

@Controller
//@RequestMapping("/user")
@RolesAllowed("USER")
//@AllArgsConstructor
@Transactional
public class HomeController {
//todo : add transfert , hrefs "/home?size=3&page=1" templates, monetization

	private static final Logger LOGGER = LogManager.getLogger("HomeController");

	@Autowired
	FindFconnectionByPayerUsernameService findFconnectionByPayerUsernameService;

	@Autowired
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

	@Autowired
	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@Autowired
	FindPaymentByPayerService findPaymentByPayerService;

	@Autowired
	FindPaymentByPayeeService findPaymentByPayeeService;

	@Autowired
	FindBankAccountByUserEmailService findBankAccountByUserEmailService;

	@Autowired
	FindTransactionByBankAccountService findTransactionByBankAccountService;

	HomeController(FindFconnectionByPayerUsernameService findFconnectionByPayerUsernameService
			,FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService
			,FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService
			,FindPaymentByPayerService findPaymentByPayerService
			,FindBankAccountByUserEmailService findBankAccountByUserEmailService
			,FindTransactionByBankAccountService findTransactionByBankAccountService
			,FindPaymentByPayeeService findPaymentByPayeeService) {
		this.findOauth2PaymybuddyUserDetailsService = findOauth2PaymybuddyUserDetailsService;
		this.findFconnectionByPayerUsernameService = findFconnectionByPayerUsernameService;
		this.findPaymybuddyUserDetailsService = findPaymybuddyUserDetailsService;
		this.findPaymentByPayerService = findPaymentByPayerService;
		this.findPaymentByPayeeService = findPaymentByPayeeService;
		this.findBankAccountByUserEmailService = findBankAccountByUserEmailService;
		this.findTransactionByBankAccountService = findTransactionByBankAccountService;
	}

	@RolesAllowed("USER")
	@GetMapping("/home")
	public String getHome(ActivePage activePage, BindingResult bindingResult, Model model, Authentication oth) {

		int displayedRows = 3;
		Float available = 0.0f;
		LOGGER.info(activePage);

		List<Epayment> paymentList = new ArrayList<>();
		List<PaymybuddyUserDetails> connList = new ArrayList<>();
		List<EbankAccount> bankAccountList = new ArrayList<>();

		List<Object> viewPaymentList = new ArrayList<>();
		ViewPayment viewPayment = new ViewPayment();

		String payerEmail;

		if (oth instanceof UsernamePasswordAuthenticationToken) {
			LOGGER.info(oth.getName() + " is instance of UsernamePasswordAuthenticationToken");
			payerEmail = oth.getName();

			available = findPaymybuddyUserDetailsService.findByEmail(payerEmail).getBalance();

		} else {
			LOGGER.info(oth.getName() + " is instance of OAuth2AuthenticationToken");
			String nameNumber = oth.getName();
			PaymybuddyUserDetails foundOauth2 = findOauth2PaymybuddyUserDetailsService.findByName(nameNumber);
			payerEmail = foundOauth2.getEmail();

			LOGGER.info(foundOauth2.getBalance());
			if (Objects.isNull(foundOauth2.getBalance())) {
				available = 0.0f;
			} else {
				available = foundOauth2.getBalance();

			}

		}

		if (findFconnectionByPayerUsernameService.findByPayerUsername(payerEmail).get(0).getEmail() != "N_A") {
			connList.addAll(findFconnectionByPayerUsernameService.findByPayerUsername(payerEmail));
			LOGGER.info("connections found for " + payerEmail);

			if (findPaymentByPayerService.findByPayer(payerEmail).get(0).getPayerEmail() != "N_A") {
				LOGGER.info(findPaymentByPayerService.findByPayer(payerEmail));

				for (Epayment e : findPaymentByPayerService.findByPayer(payerEmail)) {
					viewPayment.setConnection(e.getPayeeEmail());
					viewPayment.setDescription(e.getDescription());
					viewPayment.setAmount(-(e.getAmount()));
					LOGGER.info(payerEmail + " as payer " + viewPayment);
					viewPaymentList.add((ViewPayment) viewPayment.clone());
				}
				paymentList.addAll(findPaymentByPayerService.findByPayer(payerEmail));

			}

			if (findPaymentByPayeeService.findByPayee(payerEmail).get(0).getPayerEmail() != "N_A") {
				LOGGER.info(findPaymentByPayerService.findByPayer(payerEmail));

				for (Epayment e : findPaymentByPayeeService.findByPayee(payerEmail)) {
					viewPayment.setConnection(e.getPayerEmail());
					viewPayment.setDescription(e.getDescription());
					viewPayment.setAmount(e.getAmount());
					LOGGER.info(payerEmail + " as payee " + viewPayment);
					viewPaymentList.add((ViewPayment) viewPayment.clone());
				}

				paymentList.addAll(findPaymentByPayeeService.findByPayee(payerEmail));
			}

		}

		if (findBankAccountByUserEmailService.findBankAccountByUserEmail(payerEmail).get(0).getIban() != "N_A") {
			LOGGER.info("bank accounts found for " + payerEmail);
			bankAccountList.addAll(findBankAccountByUserEmailService.findBankAccountByUserEmail(payerEmail));
			LOGGER.info(bankAccountList);

			for (EbankAccount a : bankAccountList) {

				if (findTransactionByBankAccountService.findTransactionByBankAccount(a).get(0).getBankAccount()
						.getIban() != "N_A") {
					LOGGER.info("Transation found for " + payerEmail);

					for (Etransaction e : findTransactionByBankAccountService.findTransactionByBankAccount(a)) {
						LOGGER.info("Looping " + payerEmail + " transaction list");
						LOGGER.info(e.getFromBank());
						if (e.getFromBank() == false) {
							viewPayment.setConnection(e.getBankAccount().getIban());
							viewPayment.setDescription(e.getDescription());
							viewPayment.setAmount(-(e.getAmount()));
							LOGGER.info(payerEmail + " transfers into bank account " + viewPayment);
							viewPaymentList.add((ViewPayment) viewPayment.clone());

						} else {
							viewPayment.setConnection(e.getBankAccount().getIban());
							viewPayment.setDescription(e.getDescription());
							viewPayment.setAmount(e.getAmount());
							LOGGER.info(payerEmail + " transfers from bank account " + viewPayment);
							viewPaymentList.add((ViewPayment) viewPayment.clone());

						}
					}
					paymentList.addAll(findPaymentByPayerService.findByPayer(payerEmail));

				}

				
			}
		}

		LOGGER.info(paymentList);
		LOGGER.info(paymentList.isEmpty());

		viewPayment.setConnection("TEST");
		viewPayment.setDescription("TEST");
		viewPayment.setAmount(10f);
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPayment.setAmount(-10f);
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPayment.setAmount(10f);
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPayment.setAmount(-10f);
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPayment.setAmount(10f);
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPayment.setAmount(-10f);
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPayment.setAmount(10f);
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPayment.setAmount(-10f);
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPayment.setAmount(10f);
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());
		viewPaymentList.add((ViewPayment) viewPayment.clone());

		LOGGER.info("");
		paymentList.forEach(System.out::println);
		connList.forEach(System.out::println);
		viewPaymentList.forEach(System.out::println);
		LOGGER.info("");
		List<Object> econex = new ArrayList<>();
		econex.addAll(List.copyOf(viewPaymentList));

		PagedListHolder<Object> pagedConex = new PagedListHolder<>(econex);

		pagedConex.setPageSize(displayedRows);
		pagedConex.setPage(activePage.getPage());

		List<Integer> paginationRangeList = List.of(Math.max(activePage.getPage() - 2, 1),
				Math.max(activePage.getPage() - 1, 1), activePage.getPage(),
				Math.min(activePage.getPage() + 1, pagedConex.getPageCount()),
				Math.min(activePage.getPage() + 2, pagedConex.getPageCount()));
		Set<Integer> paginationRange = paginationRangeList.stream().collect(Collectors.toSet());

		List<Integer> pagesList = IntStream.rangeClosed(1, pagedConex.getPageCount()).boxed()
				.collect(Collectors.toList());

		Map<Integer, List<Object>> pConex = new HashMap<>();

		for (Integer i : pagesList) {
			pagedConex.setPage(i - 1);
			pConex.put(i, pagedConex.getPageList());
		}

		pConex.keySet().stream().sorted().toList().get(0);
		pagedConex.getPage();

		model.addAttribute("numberOfPages", pagedConex.getPageCount());
		model.addAttribute("paginationRange", paginationRange);
		model.addAttribute("available", available);
		model.addAttribute("pConex", pConex);
		model.addAttribute("pagedConex", pagedConex);
		model.addAttribute("connections", econex);
		model.addAttribute("pagesList", pagesList);

		return "home";
	}

}
