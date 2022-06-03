package com.paymybuddy.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.dto.ViewPayment;

@RestController
@RolesAllowed("USER")
public class BankTransactionController {

	@GetMapping("/banktransaction")//?iban=<iban>
	public Iterable<ViewPayment> getBankTransaction(@RequestParam String iban){
	
		return null;
		
	}
	
	@PostMapping("/banktransaction")
	public Iterable<ViewPayment> addBankTransaction(){
		return null;
		
	}
	
}
