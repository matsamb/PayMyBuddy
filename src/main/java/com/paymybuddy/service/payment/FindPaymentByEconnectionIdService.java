package com.paymybuddy.service.payment;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.Epayment;
import com.paymybuddy.repository.EconnectionRepository;
import com.paymybuddy.repository.PaymentRepository;

/*@Service
public class FindPaymentByEconnectionIdService {

	static final Logger findPaymentByEconnectionIdServiceLogger = LogManager.getLogger("FindPaymentByEconnectionIdService");
	
	@Autowired
	PaymentRepository paymentRepositoryAtFindPaymentByEconnectionIdService;
	
	FindPaymentByEconnectionIdService(PaymentRepository paymentRepositoryAtFindPaymentByEconnectionIdService){
		this.paymentRepositoryAtFindPaymentByEconnectionIdService = paymentRepositoryAtFindPaymentByEconnectionIdService;
	}

	public List<Epayment> findPaymentById(int id) {
		return paymentRepositoryAtFindPaymentByEconnectionIdService.findByIdConnection(id);
	}
	
	
	
}*/
