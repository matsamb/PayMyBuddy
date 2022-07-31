package com.paymybuddy.controllers.IT;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.Epayment;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.bankaccount.FindBankAccountByUserEmailService;
import com.paymybuddy.service.connection.FindFconnectionByPayerUsernameService;
import com.paymybuddy.service.payment.SavePaymentService;
import com.paymybuddy.service.transfer.SaveTransferService;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "max"
, password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" //{Bcrypt}
, authorities = "USER")
public class MakePaymentControllerIT {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@MockBean
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

	@MockBean
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@MockBean
	FindBankAccountByUserEmailService findBankAccountByUserEmailService;

	@MockBean
	FindFconnectionByPayerUsernameService findFconnectionByPayerUsernameService;

	@MockBean
	SavePaymentService savePaymentService;

	@MockBean
	SaveTransferService saveTransferService;

	@BeforeEach
	public void setUp(WebApplicationContext context) {

		mockMvc = MockMvcBuilders.webAppContextSetup(context).defaultRequest(get("/makepayment"))
				.apply(springSecurity()).build();

	}

	@Test
	public void getMakepayment() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max");
		max.setUserRole(UserRole.USER);
		max.setBalance(27f);

		when(findPaymybuddyUserDetailsService.findByEmail("max")).thenReturn(max);
		when(findOauth2PaymybuddyUserDetailsService.findByName("nameNumber")).thenReturn(max);

		List<PaymybuddyUserDetails> findConnectionList = new ArrayList<>();
		findConnectionList.add((PaymybuddyUserDetails) max);
		findConnectionList.add((PaymybuddyUserDetails) max);

		when(findFconnectionByPayerUsernameService.findByPayerUsername("max")).thenReturn(findConnectionList);

		EbankAccount bankAccount = new EbankAccount();
		bankAccount.setIban("man");
		List<EbankAccount> foundAccountList = new ArrayList<>();
		foundAccountList.add(bankAccount);
		foundAccountList.add(bankAccount);

		when(findBankAccountByUserEmailService.findBankAccountByUserEmail("max")).thenReturn(foundAccountList);

		mockMvc.perform(get("/makepayment")).andExpect(status().isOk());

	}

	@Test
	public void postMakePayment() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max");
		max.setUserRole(UserRole.USER);
		max.setBalance(27f);

		when(findPaymybuddyUserDetailsService.findByEmail("max")).thenReturn(max);
		when(findOauth2PaymybuddyUserDetailsService.findByName("nameNumber")).thenReturn(max);

		List<PaymybuddyUserDetails> findConnectionList = new ArrayList<>();
		findConnectionList.add((PaymybuddyUserDetails) max);
		findConnectionList.add((PaymybuddyUserDetails) max);

		when(findFconnectionByPayerUsernameService.findByPayerUsername("max")).thenReturn(findConnectionList);

		EbankAccount bankAccount = new EbankAccount();
		bankAccount.setIban("iban");
		List<EbankAccount> foundAccountList = new ArrayList<>();
		foundAccountList.add(bankAccount);
		foundAccountList.add(bankAccount);

		when(findBankAccountByUserEmailService.findBankAccountByUserEmail("max")).thenReturn(foundAccountList);

		mockMvc.perform(post("/makepayment")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/makepayment?success=true"));

	}

	@Disabled
	@Test
	public void postMakePaymentWithPayment() throws Exception {

		BindingResult payment = mock(BindingResult.class);
		when(payment.getFieldValue("connection")).thenReturn("max");

		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max");
		max.setUserRole(UserRole.USER);
		max.setBalance(27f);

		when(findPaymybuddyUserDetailsService.findByEmail("max")).thenReturn(max);
		when(findOauth2PaymybuddyUserDetailsService.findByName("nameNumber")).thenReturn(max);

		List<PaymybuddyUserDetails> findConnectionList = new ArrayList<>();
		findConnectionList.add((PaymybuddyUserDetails) max);
		findConnectionList.add((PaymybuddyUserDetails) max);

		when(findFconnectionByPayerUsernameService.findByPayerUsername("max")).thenReturn(findConnectionList);

		EbankAccount bankAccount = new EbankAccount();
		bankAccount.setIban("iban");
		List<EbankAccount> foundAccountList = new ArrayList<>();
		foundAccountList.add(bankAccount);
		foundAccountList.add(bankAccount);

		when(findBankAccountByUserEmailService.findBankAccountByUserEmail("max")).thenReturn(foundAccountList);

		Epayment paymentCase = new Epayment("max");
		paymentCase.setAmount(5f);

		mockMvc.perform(post("/makepayment")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/makepayment?success=true"));

		verify(savePaymentService, times(1)).savePayment(paymentCase);

	}

	@Disabled
	@Test
	public void postMakePaymentWithTransaction() throws Exception {

		BindingResult payment = mock(BindingResult.class);
		when(payment.getFieldValue("connection")).thenReturn("iban");

		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max");
		max.setUserRole(UserRole.USER);
		max.setBalance(27f);

		when(findPaymybuddyUserDetailsService.findByEmail("max")).thenReturn(max);
		when(findOauth2PaymybuddyUserDetailsService.findByName("nameNumber")).thenReturn(max);

		List<PaymybuddyUserDetails> findConnectionList = new ArrayList<>();
		findConnectionList.add((PaymybuddyUserDetails) max);
		findConnectionList.add((PaymybuddyUserDetails) max);

		when(findFconnectionByPayerUsernameService.findByPayerUsername("max")).thenReturn(findConnectionList);

		EbankAccount bankAccount = new EbankAccount();
		bankAccount.setIban("iban");
		List<EbankAccount> foundAccountList = new ArrayList<>();
		foundAccountList.add(bankAccount);
		foundAccountList.add(bankAccount);

		when(findBankAccountByUserEmailService.findBankAccountByUserEmail("max")).thenReturn(foundAccountList);

		Etransaction transactionCase = new Etransaction();
		transactionCase.setAmount(5f);

		mockMvc.perform(post("/makepayment")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/makepayment?success=true"));

		verify(saveTransferService, times(1)).saveTransfer(transactionCase);
		verify(savePaymybuddyUserDetailsService, times(1)).savePaymybuddyUserDetails(max);

	}

}
