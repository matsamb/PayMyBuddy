package com.paymybuddy.service.payment;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.Epayment;
import com.paymybuddy.repository.PaymentRepository;

import lombok.AllArgsConstructor;

@Service
//@AllArgsConstructor
public class FindPaymentByPayeeService {
	
	private static final Logger LOGGER = LogManager.getLogger("FindPaymentByPayeeService");
	
	@Autowired
	PaymentRepository paymentRepository;

	FindPaymentByPayeeService(PaymentRepository paymentRepository){
		this.paymentRepository = paymentRepository;
	}
	
	public List<Epayment> findByPayee(String payeeEmail) {
		
	if(paymentRepository.findByPayeeEmail(payeeEmail).get().isEmpty()) {
		LOGGER.info("No payment as payee for "+payeeEmail);
		Epayment details = new Epayment("N_A");
		List<Epayment> detailsList = new ArrayList<>();
		detailsList.add(details);
		return detailsList;
		
	}else {	
		LOGGER.info("payment as payee found for "+payeeEmail);
		return paymentRepository.findByPayeeEmail(payeeEmail).get();
	}
	}
}
