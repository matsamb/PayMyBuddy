package com.paymybuddy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.ActivePage;
import com.paymybuddy.dto.ViewPayment;
import com.paymybuddy.entity.Econnection;
import com.paymybuddy.entity.Epayment;
import com.paymybuddy.entity.GoogleOAuth2User;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.connection.FindEconnectionByPayerUsernameService;
import com.paymybuddy.service.payment.FindPaymentByEconnectionIdService;

@Controller
//@RequestMapping("/user")
@RolesAllowed("USER")
public class HomeController {

	static final Logger homeControllerLogger = LogManager.getLogger("HomeController");

	@Autowired
	FindEconnectionByPayerUsernameService findEconnectionByPayerUsernameServiceAtHomeController;

	@Autowired
	FindPaymentByEconnectionIdService findPaymentByEconnectionIdServiceAtHomeController;

	HomeController(FindEconnectionByPayerUsernameService findEconnectionByPayerUsernameServiceAtHomeController,
			FindPaymentByEconnectionIdService findPaymentByEconnectionIdServiceAtHomeController) {
		this.findEconnectionByPayerUsernameServiceAtHomeController = findEconnectionByPayerUsernameServiceAtHomeController;
		this.findPaymentByEconnectionIdServiceAtHomeController = findPaymentByEconnectionIdServiceAtHomeController;
	}

	@RolesAllowed("USER")
	@GetMapping("/home")
	public String getHome(ActivePage activePage, BindingResult bindingResult, Model model, Authentication oth) {

		int displayedRows = 3;

		System.out.println(activePage);
		
		UserDetails currentUser = new PaymybuddyUserDetails();
		OAuth2User currentOAuth = new GoogleOAuth2User();
		
		
		
		
		List<Epayment> paymentList = new ArrayList<>();
		List<Econnection> connList = new ArrayList<>();
		
		List<ViewPayment> viewPaymentList = new ArrayList<>();
		ViewPayment viewPayment = new ViewPayment();
		
		if(oth.getPrincipal() instanceof UserDetails) {
			
			currentUser = (UserDetails) oth.getPrincipal();
			System.out.println(oth.getPrincipal().toString());
			connList.addAll(findEconnectionByPayerUsernameServiceAtHomeController.findByFkPayerUsername(currentUser.getUsername()));
			
			for(Econnection c: connList) {
				if(Objects.isNull(findPaymentByEconnectionIdServiceAtHomeController.findPaymentById(c.getId())) ) {
					homeControllerLogger.info("No payment found for "+c.getFkPayeeUserName());
				}else {
					paymentList.addAll(findPaymentByEconnectionIdServiceAtHomeController.findPaymentById(c.getId()));
				}
			}
		}else {
			
			currentOAuth = (OAuth2User) oth.getPrincipal();
			System.out.println(oth.getPrincipal().toString());
			connList.addAll(findEconnectionByPayerUsernameServiceAtHomeController.findByFkPayerUsername(currentUser.getUsername()));
			for(Econnection c: connList) {
				if(Objects.isNull(findPaymentByEconnectionIdServiceAtHomeController.findPaymentById(c.getId())) ) {
					homeControllerLogger.info("No payment found for "+c.getFkPayeeUserName());
				}else {
					paymentList.addAll(findPaymentByEconnectionIdServiceAtHomeController.findPaymentById(c.getId()));
				}
			}
		}
		
		if(Objects.isNull(paymentList)) {
			homeControllerLogger.info("No payment available");
		} else {
			for(Econnection c: connList) {
				for(Epayment e: paymentList) {
						if(Objects.equals(c.getId(), e.getIdConnection())) {
					viewPayment.setConnection(c.getFkPayeeUserName());
					viewPayment.setDescription(e.getDescription());
					viewPayment.setAmount(e.getAmount());
					
					viewPaymentList.add((ViewPayment)viewPayment.clone());
					}				
				}					
			}
		}
		
	/*	paymentList.forEach(System.out::println);
		connList.forEach(System.out::println);
		viewPaymentList.forEach(System.out::println);*/
		
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

		model.addAttribute("pConex", pConex);
		model.addAttribute("pagedConex", pagedConex);
		model.addAttribute("connections", econex);
		model.addAttribute("pagesList", pagesList);

		return "home";
	}

	@RolesAllowed("USER")
	@PostMapping("/home")
	public ModelAndView userOut(ActivePage activePage, BindingResult bindingResult, Model model, Authentication oth) {
		ModelAndView result;

		if (activePage.equals(1)) {

			int displayedRows = 3;

			System.out.println(activePage);

			UserDetails currentUser = new PaymybuddyUserDetails();
			currentUser = (UserDetails) oth.getPrincipal();
			List<Epayment> paymentList = new ArrayList<>();
			List<Econnection> connList = new ArrayList<>();

			List<ViewPayment> viewPaymentList = new ArrayList<>();
			ViewPayment viewPayment = new ViewPayment();

			connList.addAll(findEconnectionByPayerUsernameServiceAtHomeController
					.findByFkPayerUsername(currentUser.getUsername()));
			for (Econnection c : connList) {
				if (Objects.isNull(findPaymentByEconnectionIdServiceAtHomeController.findPaymentById(c.getId()))) {
					homeControllerLogger.info("No payment found for " + c.getFkPayeeUserName());
				} else {
					paymentList.addAll(findPaymentByEconnectionIdServiceAtHomeController.findPaymentById(c.getId()));
				}

			}

			if (Objects.isNull(paymentList)) {
				homeControllerLogger.info("No payment available");
			} else {
				for (Econnection c : connList) {
					for (Epayment e : paymentList) {
						if (Objects.equals(c.getId(), e.getIdConnection())) {
							viewPayment.setConnection(c.getFkPayeeUserName());
							viewPayment.setDescription(e.getDescription());
							viewPayment.setAmount(e.getAmount());

							viewPaymentList.add((ViewPayment) viewPayment.clone());
						}
					}
				}
			}

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

			pConex.get(activePage.getPage());

			model.addAttribute("pConex", pConex);
			model.addAttribute("pagedConex", pagedConex);
			model.addAttribute("connections", econex);
			model.addAttribute("pagesList", pagesList);
			result = new ModelAndView("redirect:/home?size=" + displayedRows + "&page=" + activePage.getPage());
		} else if (activePage.equals(1)) {

			result = new ModelAndView("redirect:/login?logout=true");
		} else {
			result = new ModelAndView("redirect:/makepayment");
		}

		return result;
	}

}
