/*package com.paymybuddy.unit.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.bankaccount.FindBankAccountByIbanService;
import com.paymybuddy.service.transfer.FindTransactionByBankAccountService;
import com.paymybuddy.service.transfer.SaveTransferService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;
import com.paymybuddy.utils.WithMockPayMyBuddyUser;

@SpringBootTest
@AutoConfigureMockMvc
public class PostBankTransactionRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	FindBankAccountByIbanService findBankAccountByIbanService;

	@MockBean
	SaveTransferService saveTransferService;

	@MockBean
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@MockBean
	FindTransactionByBankAccountService findTransactionByBankAccountService;

	@BeforeEach
	public void setUp(WebApplicationContext context) {
				
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(post("/transactions"))
					.apply(springSecurity())
					.build();
		
	}
	
	@Test
	@WithMockPayMyBuddyUser( email="max", username = "max", password = "max")
	public void postTest() throws Exception{
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);

		
		EbankAccount ebank = new EbankAccount();
		ebank.setIban("OK1154631111123651111965411");
		ebank.setUser(max);
		
		Etransaction eTransaction = new Etransaction();
		eTransaction.setBankAccount(ebank);
		eTransaction.setAmount(16f);
		eTransaction.setBankTransactionId(3);
		eTransaction.setFromBank(false);
		
		List<Etransaction> transactionList = new ArrayList<>();
		transactionList.add(eTransaction);
		
		when(findBankAccountByIbanService.findBankAccountByIban("OK1154631111123651111965411")).thenReturn(ebank);		
		when(findTransactionByBankAccountService.findTransactionByBankAccount(ebank)).thenReturn(transactionList);
		
		mockMvc
			.perform(post("/transactions"/*?iban=OK1154631111123651111965411*//*).with(httpBasic("max", "max"))
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"transactionId\":43,\"description\":\"sturdy\",\"amount\":24.0,\"bankAccount\":{\"iban\":\"OK1154631111123651111965411\",\"userEmail\":\"max\"}}")
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		verify(savePaymybuddyUserDetailsService, times(1))
			.savePaymybuddyUserDetails(new PaymybuddyUserDetails());
	}
	
}
*/