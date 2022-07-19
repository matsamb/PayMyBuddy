package com.paymybuddy.restcontroller;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.dto.ViewPayment;

@RestController
@RolesAllowed("USER")
public class PostBankTransactionController {
//todo : upadate balance for inbound payment

	
	@PostMapping("/banktransaction")//?iban=<iban>
	public Iterable<ViewPayment> addBankTransaction(@RequestParam String iban){
		return null;
		
	}
	
}
