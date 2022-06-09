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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.ViewPayment;
import com.paymybuddy.entity.Econnection;
import com.paymybuddy.entity.Epayment;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.entity.Users;
import com.paymybuddy.service.connection.FindEconnectionByPayerUsernameService;
import com.paymybuddy.service.payment.SavePaymentService;

@RolesAllowed("USER")
@Controller
public class MakePaymentController {

	final static Logger makePaymentControllerLogger = LogManager.getLogger("MakePaymentController");
	
	@Autowired
	FindEconnectionByPayerUsernameService findEconnectionByPayerUsernameServiceAtMakePaymentController;
	
	@Autowired
	SavePaymentService savePaymentServiceAtMakePaymentController;
	
	MakePaymentController(SavePaymentService savePaymentServiceAtMakePaymentController
			,FindEconnectionByPayerUsernameService findEconnectionByPayerUsernameServiceAtMakePaymentController
			){
		this.findEconnectionByPayerUsernameServiceAtMakePaymentController = findEconnectionByPayerUsernameServiceAtMakePaymentController;
		this.savePaymentServiceAtMakePaymentController = savePaymentServiceAtMakePaymentController;
	}
	
	@GetMapping("/makepayment")
	public String getAddConnection(ViewPayment payment, Users euser, BindingResult bindingResult, Authentication auth, Model model) {
		
		makePaymentControllerLogger.info("Payment initiated");
				
		List<Econnection> connectionList = new ArrayList<>();
		UserDetails currentUser = new PaymybuddyUserDetails();
		currentUser = (UserDetails)auth.getPrincipal();
		if(Objects.isNull(findEconnectionByPayerUsernameServiceAtMakePaymentController.findByFkPayerUsername(currentUser.getUsername()))) {
			makePaymentControllerLogger.info("No connection for "+currentUser.getUsername());
		}else {
			connectionList.addAll(findEconnectionByPayerUsernameServiceAtMakePaymentController.findByFkPayerUsername(currentUser.getUsername()));
		}
	
		model.addAttribute("econnections", connectionList);
		
		makePaymentControllerLogger.info(connectionList);
	
		return "/makepayment";
		
	}
	
	@PostMapping("/makepayment")
	public ModelAndView createConnection(ViewPayment payment, Users euser, BindingResult bindingResult, Authentication auth, Model model) {
		
		ModelAndView result = null;
		
		Epayment ePayment = new Epayment();
		
		List<Econnection> connectionList = new ArrayList<>();
		UserDetails currentUser = new PaymybuddyUserDetails();
		currentUser = (UserDetails)auth.getPrincipal();
		if(Objects.isNull(findEconnectionByPayerUsernameServiceAtMakePaymentController.findByFkPayerUsername(currentUser.getUsername()))) {
			makePaymentControllerLogger.info("No connection for "+currentUser.getUsername());
			result = new ModelAndView("redirect:/makepayment?error=true");
		}else {
			connectionList.addAll(findEconnectionByPayerUsernameServiceAtMakePaymentController.findByFkPayerUsername(currentUser.getUsername()));

			Timestamp u = new java.sql.Timestamp(System.currentTimeMillis());
			
			ePayment.setPaymentDate(u);
			ePayment.setAmount(payment.getAmount());
			ePayment.setDescription(payment.getDescription());
			
			for(Econnection c : connectionList) {
				if(Objects.equals(c.fkPayeeUserName, payment.getConnection())) {	
					ePayment.setIdConnection(c.getId());
				}
			}
			
			result = new ModelAndView("redirect:/makepayment?success=true");
		}
	
		model.addAttribute("econnections", connectionList);
		
		savePaymentServiceAtMakePaymentController.savePayment(ePayment);
		
		System.out.println(payment.getDescription()+" and "+payment.getConnection());
		
		return result;
		
	}
	
}
