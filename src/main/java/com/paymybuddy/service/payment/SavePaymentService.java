package com.paymybuddy.service.payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.Epayment;
import com.paymybuddy.repository.PaymentRepository;

@Service
public class SavePaymentService {

	private static final Logger LOGGER = LogManager.getLogger("SavePaymentService");

	@Autowired
	PaymentRepository paymentRepository;
	
	SavePaymentService(PaymentRepository paymentRepository){
		this.paymentRepository = paymentRepository;
	}

	public void savePayment(Epayment ePayment) {
		
		LOGGER.info(ePayment+" saved");
		paymentRepository.save(ePayment);	
	}
	
	
	
}
