package com.paymybuddy.controller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.paymybuddy.entity.Eusers;
import com.paymybuddy.service.connection.AddConnectionService;

@RolesAllowed("USER")
@Controller
public class MakePaymentController {

	final static Logger addConnectionControllerLogger = LogManager.getLogger("AddConnectionController");
	
/*	@Autowired
	AddPaymentService addPaymentServiceAtMakePaymentController;*/
	
/*	MakePaymentController(AddPaymentService addPaymentServiceAtMakePaymentController){
		this.addPaymentServiceAtMakePaymentController = addPaymentServiceAtMakePaymentController;
	}*/
	
	
	@GetMapping("/makepayment")
	public String getAddConnection() {
		
/*		addPaymentServiceAtMakePaymentController.addPayment(1,"bowl", "ywa");
		addConnectionControllerLogger.info("Payment initiated");*/
		
		return "/makepayment";
		
	}
	
	@PostMapping("/makepayment")
	public String createConnection(@Validated Eusers euser, BindingResult bindingResult) {
		
/*		addConnectionServiceAtAddConectionController.addConnection(1,"bowl", "ywa");
		addConnectionControllerLogger.info("Connection requested");*/
		
		return "/home";
		
	}
	
}
