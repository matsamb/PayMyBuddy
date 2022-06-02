package com.paymybuddy.service.payment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.Epayment;
import com.paymybuddy.repository.PaymentRepository;

@Service
public class SavePaymentService {

	static final Logger savePaymentServiceLogger = LogManager.getLogger("SavePaymentService");

	@Autowired
	PaymentRepository paymentRepositoryAtSavePaymentService;
	
	SavePaymentService(PaymentRepository paymentRepositoryAtSavePaymentService){
		this.paymentRepositoryAtSavePaymentService = paymentRepositoryAtSavePaymentService;
	}

	public void savePayment(Epayment ePayment) {
		paymentRepositoryAtSavePaymentService.save(ePayment);	
	}
	
	
	
}
