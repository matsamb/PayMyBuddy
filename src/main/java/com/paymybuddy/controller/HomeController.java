package com.paymybuddy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.ActivePage;
import com.paymybuddy.dto.ViewPayment;
import com.paymybuddy.entity.Epayment;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.connection.FindFconnectionByPayerUsernameService;
import com.paymybuddy.service.payment.FindPaymentByPayeeService;
import com.paymybuddy.service.payment.FindPaymentByPayerService;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;

@Controller
//@RequestMapping("/user")
@RolesAllowed("USER")
//@AllArgsConstructor
@Transactional
public class HomeController {
//todo : pending payment

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

	HomeController(FindFconnectionByPayerUsernameService findFconnectionByPayerUsernameService,
			FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService,
			FindPaymentByPayerService findPaymentByPayerService, FindPaymentByPayeeService findPaymentByPayeeService) {
		this.findFconnectionByPayerUsernameService = findFconnectionByPayerUsernameService;
		this.findPaymybuddyUserDetailsService = findPaymybuddyUserDetailsService;
		this.findPaymentByPayerService = findPaymentByPayerService;
		this.findPaymentByPayeeService = findPaymentByPayeeService;
	}

	@RolesAllowed("USER")
	@GetMapping("/home")
	public String getHome(ActivePage activePage, BindingResult bindingResult, Model model, Authentication oth) {
		int displayedRows = 3;

		Float available = 0.0f;
		LOGGER.info(activePage);

		List<Epayment> paymentList = new ArrayList<>();
		List<PaymybuddyUserDetails> connList = new ArrayList<>();

		List<ViewPayment> viewPaymentList = new ArrayList<>();
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

			if (findPaymentByPayerService.findByPayer(payerEmail).get(0).getPayerEmail() != "N_A") {
				LOGGER.info(findPaymentByPayerService.findByPayer(payerEmail));

				for (Epayment e : findPaymentByPayerService.findByPayer(payerEmail)) {
					viewPayment.setConnection(e.getPayeeEmail());
					viewPayment.setDescription(e.getDescription());
					viewPayment.setAmount(-(e.getAmount()));
					LOGGER.info(payerEmail+" as payer "+viewPayment);
					viewPaymentList.add((ViewPayment) viewPayment.clone());
				}
				paymentList.addAll(findPaymentByPayerService.findByPayer(payerEmail));

			}

			if (findPaymentByPayeeService.findByPayee(payerEmail).get(0).getPayeeEmail() != "N_A") {
				LOGGER.info(findPaymentByPayerService.findByPayer(payerEmail));

				for (Epayment e : findPaymentByPayeeService.findByPayee(payerEmail)) {
					viewPayment.setConnection(e.getPayerEmail());
					viewPayment.setDescription(e.getDescription());
					viewPayment.setAmount(e.getAmount());
					LOGGER.info(payerEmail+" as payee "+viewPayment);
					viewPaymentList.add((ViewPayment) viewPayment.clone());
				}

				paymentList.addAll(findPaymentByPayeeService.findByPayee(payerEmail));
			}

		}

		LOGGER.info(paymentList);
		LOGGER.info(paymentList.isEmpty());

		LOGGER.info("");
		paymentList.forEach(System.out::println);
		connList.forEach(System.out::println);
		viewPaymentList.forEach(System.out::println);
		LOGGER.info("");
		List<ViewPayment> econex = new ArrayList<>();
		econex.addAll(List.copyOf(viewPaymentList));

		PagedListHolder<ViewPayment> pagedConex = new PagedListHolder<>(econex);

		pagedConex.setPageSize(displayedRows);
		pagedConex.setPage(activePage.getPage());

		List<Integer> pagesList = IntStream.rangeClosed(1, pagedConex.getPageCount()).boxed()
				.collect(Collectors.toList());

		Map<Integer, List<ViewPayment>> pConex = new HashMap<>();

		for (Integer i : pagesList) {
			pagedConex.setPage(i - 1);
			pConex.put(i, pagedConex.getPageList());
		}

		model.addAttribute("available", available);
		model.addAttribute("pConex", pConex);
		model.addAttribute("pagedConex", pagedConex);
		model.addAttribute("connections", econex);
		model.addAttribute("pagesList", pagesList);

		return "home";
	}

}
