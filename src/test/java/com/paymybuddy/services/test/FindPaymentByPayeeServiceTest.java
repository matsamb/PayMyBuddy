package com.paymybuddy.services.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.entity.Epayment;
import com.paymybuddy.repository.PaymentRepository;
import com.paymybuddy.service.payment.FindPaymentByPayeeService;

@ExtendWith(MockitoExtension.class)
@WithMockUser(roles = "USER")
public class FindPaymentByPayeeServiceTest {

	@Mock
	private PaymentRepository paymentRepository;

	@InjectMocks
	private FindPaymentByPayeeService findPaymentByPayeeService;

	@Test
	public void givenMaxWithTwoPayment_whenFindPaymentByPayeeServiceCalled_thenItShouldReturnAListOfTwoPayment()
			throws Exception {

		Epayment ePay = new Epayment();
		ePay.setId(1);
		ePay.setPayeeEmail("max");

		Epayment dePay = new Epayment();
		dePay.setId(2);
		dePay.setPayeeEmail("max");

		List<Epayment> payList = new ArrayList<>();
		payList.add(ePay);
		payList.add(dePay);

		Optional<List<Epayment>> oPayList = Optional.of(payList);

		when(paymentRepository.findByPayeeEmail("max")).thenReturn(oPayList);

		List<Epayment> payResult = findPaymentByPayeeService.findByPayee("max");

		assertThat(payResult).isEqualTo(payList);

	}

	@Test
	public void givenMaxWithoutPayment_whenFindPaymentByPayeeServiceCalled_thenItShouldReturnAListWithDefaultPayment() throws Exception 	{

		List<Epayment> payList = new ArrayList<>();

		Optional<List<Epayment>> oPayList = Optional.of(payList);
		
		when(paymentRepository.findByPayeeEmail("max")).thenReturn(oPayList);
		
		List<Epayment> referenceList = new ArrayList<>();

		referenceList.add(new Epayment("N_A"));

		List<Epayment> payResult = findPaymentByPayeeService.findByPayee("max");

		assertThat(payResult).isEqualTo(referenceList);
	}

}
