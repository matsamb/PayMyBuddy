package com.paymybuddy.service.payment;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.Epayment;
import com.paymybuddy.repository.PaymentRepository;

@Service
public class FindPaymentByPayerService {

	static final Logger LOGGER = LogManager.getLogger("FindPaymentByPayerService");

	@Autowired
	PaymentRepository paymentRepository;

	FindPaymentByPayerService(PaymentRepository paymentRepositoryAtFindPaymentByEconnectionIdService) {
		this.paymentRepository = paymentRepositoryAtFindPaymentByEconnectionIdService;
	}

	public List<Epayment> findByPayer(String payerEmail) {

		if (paymentRepository.findByPayerEmail(payerEmail).get().isEmpty()) {
			LOGGER.info("No payment as payer for " + payerEmail);
			Epayment details = new Epayment("N_A");
			List<Epayment> detailsList = new ArrayList<>();
			detailsList.add(details);
			LOGGER.info(detailsList);

			return detailsList;
			
			
		} else {
			LOGGER.info("Payment as payer found for " + payerEmail);
			LOGGER.info(paymentRepository.findByPayerEmail(payerEmail).get());

			return paymentRepository.findByPayerEmail(payerEmail).get();

		}
	}

}
