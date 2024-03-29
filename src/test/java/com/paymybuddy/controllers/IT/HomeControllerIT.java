package com.paymybuddy.controllers.IT;

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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
	private FindFconnectionByPayerUsernameService findFconnectionByPayerUsernameService;

	@MockBean
	private FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

	@MockBean
	private FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@MockBean
	private FindPaymentByPayerService findPaymentByPayerService;

	@MockBean
	private FindPaymentByPayeeService findPaymentByPayeeService;

	@MockBean
	private FindBankAccountByUserEmailService findBankAccountByUserEmailService;

	@MockBean
	private FindTransactionByBankAccountService findTransactionByBankAccountService;

	@BeforeEach
	public void setUp(WebApplicationContext context) {

		mockMvc = MockMvcBuilders.webAppContextSetup(context).defaultRequest(get("/home")).apply(springSecurity()) // .defaultRequest(get("/signin"))
				.build();

	}

	@Test
	@WithMockUser(username="max"
	, password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" //{Bcrypt}
	, authorities ={"USER","ADMIN"})
	public void getHome() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("max");
		max.setUsername("max");
		max.setEnabled(true);
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);

		List<PaymybuddyUserDetails> findConnectionList = new ArrayList<>();
		findConnectionList.add(max);
		findConnectionList.add(max);

		when(findPaymybuddyUserDetailsService.findByEmail("max")).thenReturn(max);
		when(findFconnectionByPayerUsernameService.findByPayerUsername("max")).thenReturn(findConnectionList);

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

		Etransaction etransaction = new Etransaction();
		etransaction.setBankAccount(bankAccount);
		etransaction.setFromBank(false);
		etransaction.setAmount(5f);
		List<Etransaction> foundTransactionList = new ArrayList<>();
		foundTransactionList.add(etransaction);

		Etransaction etransaction2 = new Etransaction();
		etransaction2.setBankAccount(bankAccount);
		etransaction2.setFromBank(true);
		etransaction2.setAmount(5f);
		foundTransactionList.add(etransaction2);

		when(findTransactionByBankAccountService.findTransactionByBankAccount(bankAccount))
				.thenReturn(foundTransactionList);

		mockMvc.perform(get("/home").param("page", "1")).andExpect(status().isOk());
	}

}
