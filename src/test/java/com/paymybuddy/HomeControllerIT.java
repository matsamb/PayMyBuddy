package com.paymybuddy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.Epayment;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.bankaccount.FindBankAccountByUserEmailService;
import com.paymybuddy.service.connection.FindFconnectionByPayerUsernameService;
import com.paymybuddy.service.payment.FindPaymentByPayeeService;
import com.paymybuddy.service.payment.FindPaymentByPayerService;
import com.paymybuddy.service.transfer.FindTransactionByBankAccountService;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	FindFconnectionByPayerUsernameService findFconnectionByPayerUsernameService;

	@MockBean
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

	@MockBean
	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@MockBean
	FindPaymentByPayerService findPaymentByPayerService;

	@MockBean
	FindPaymentByPayeeService findPaymentByPayeeService;

	@MockBean
	FindBankAccountByUserEmailService findBankAccountByUserEmailService;

	@MockBean
	FindTransactionByBankAccountService findTransactionByBankAccountService;
	
	@BeforeEach
	public void setUp(WebApplicationContext context) {
		
/*		mockMvc = MockMvcBuilders
					.standaloneSetup(homeController)
					.build();*/
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/home"))
					.apply(springSecurity())	//.defaultRequest(get("/signin"))
					.build();
		
	}
	
	@Test
	@WithMockUser(username="max"
	, password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" //{Bcrypt}
	, authorities ={"USER","ADMIN"})
	public void getHome() throws Exception{
		
		BindingResult activePage = mock(BindingResult.class);
		when(activePage.getFieldValue("page")).thenReturn(1);
		
		UserDetails max = new PaymybuddyUserDetails();
			((PaymybuddyUserDetails) max).setEmail("max");
			((PaymybuddyUserDetails) max).setUsername("max");
			/*	((PaymybuddyUserDetails) max).setPassword("m456");
			((PaymybuddyUserDetails) max).setBalance(0.0f);*/
			((PaymybuddyUserDetails) max).setUserRole(UserRole.USER);
			//((PaymybuddyUserDetails) max).setEnabled(true);

		List<PaymybuddyUserDetails> findConnectionList = new ArrayList<>();
		findConnectionList.add((PaymybuddyUserDetails)max);
		findConnectionList.add((PaymybuddyUserDetails)max);

		when(findPaymybuddyUserDetailsService.findByEmail("max")).thenReturn((PaymybuddyUserDetails)max);
		when(findFconnectionByPayerUsernameService.findByPayerUsername("max")).thenReturn((List<PaymybuddyUserDetails>)findConnectionList);
		
		Epayment payment = new Epayment();
		payment.setAmount(5f);
		List<Epayment> payPaymentList = new ArrayList<>();
		payPaymentList.add(payment);
		payPaymentList.add(payment);
		
		when(findPaymentByPayerService.findByPayer("max")).thenReturn(payPaymentList);
		when(findPaymentByPayeeService.findByPayee("max")).thenReturn(payPaymentList);

		EbankAccount bankAccount = new EbankAccount();
		bankAccount.setIban("man");
		List<EbankAccount> foundAccountList = new ArrayList<>();
		foundAccountList.add(bankAccount);
		foundAccountList.add(bankAccount);

		when(findBankAccountByUserEmailService.findBankAccountByUserEmail("max")).thenReturn(foundAccountList);
	//	when(findPaymentByPayeeService.findByPayee("max")).thenReturn(payPaymentList);
		
		Etransaction etransaction = new Etransaction();
		etransaction.setBankAccount(bankAccount);
		etransaction.setFromBank(false);
		etransaction.setAmount(5f);
		List<Etransaction> foundTransactionList = new ArrayList<>();
		foundTransactionList.add(etransaction);
		foundTransactionList.add(etransaction);
		
		when(findTransactionByBankAccountService.findTransactionByBankAccount(bankAccount)).thenReturn(foundTransactionList);
		
		
		mockMvc
			.perform(get("/home"))
			.andExpect(status().isOk())
			;
	}

}
