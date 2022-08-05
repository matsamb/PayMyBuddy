package com.paymybuddy.controllers.IT;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import com.paymybuddy.service.transfer.FindAllTransactionsService;
import com.paymybuddy.service.transfer.FindTransactionByBankAccountService;
import com.paymybuddy.service.transfer.SaveTransferService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;
import com.paymybuddy.utils.WithMockPayMyBuddyUser;

@SpringBootTest
@AutoConfigureMockMvc
public class GetBankTransactionRestControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	FindAllTransactionsService findAllTransactionsService;
	
	@MockBean
	SaveTransferService saveTransferService;
	
	@BeforeEach
	public void setUp(WebApplicationContext context) {
				
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/transactions"))
					.apply(springSecurity())
					.build();
		
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void givenATransaction_whenGetCalled_thenAListOfPendingTransactionShouldBeReturned() throws Exception{
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max@max.max");
		max.setName("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);

		
		EbankAccount ebank = new EbankAccount();
		ebank.setIban("OK1154631111123651111965411");
		ebank.setUser(max);
		
		EbankAccount ebank2 = new EbankAccount();
		ebank.setIban("AP1154631111123651111965411");
		ebank.setUser(max);
		
		Etransaction eTransaction = new Etransaction();
		eTransaction.setTransactionId(1);
		eTransaction.setBankAccount(ebank);
		eTransaction.setAmount(16f);
		eTransaction.setBankTransactionId(3);//received 
		eTransaction.setFromBank(true);
		
		Etransaction eTransaction2 = new Etransaction();
		eTransaction2.setTransactionId(2);
		eTransaction2.setBankAccount(ebank);
		eTransaction2.setAmount(16f);
		eTransaction2.setFromBank(false);
		eTransaction2.setBankTransactionId(-3);


		Etransaction eTransaction3 = new Etransaction();
		eTransaction3.setTransactionId(3);
		eTransaction3.setBankAccount(ebank);
		eTransaction3.setAmount(16f);
		eTransaction3.setFromBank(false);
		eTransaction3.setBankTransactionId(-3);//pending
		
		List<Etransaction> transactionList = new ArrayList<>();
		transactionList.add(eTransaction);
		transactionList.add(eTransaction2);
		transactionList.add(eTransaction3);
		
		Etransaction eTransaction5 = new Etransaction();
		eTransaction5.setTransactionId(5);
		eTransaction5.setBankAccount(ebank2);
		eTransaction5.setAmount(16f);
		eTransaction5.setBankTransactionId(-3);
		eTransaction5.setFromBank(false);
		
		Etransaction eTransaction6 = new Etransaction();
		eTransaction6.setTransactionId(6);
		eTransaction6.setBankAccount(ebank2);
		eTransaction6.setAmount(16f);
		eTransaction6.setBankTransactionId(-1);//sent
		eTransaction6.setFromBank(false);

		
		List<Etransaction> transactionList2 = new ArrayList<>();
		transactionList2.add(eTransaction5);
		transactionList2.add(eTransaction6);

		List<Etransaction> AllTransactionList = new ArrayList<>();
		AllTransactionList.addAll(transactionList2);
		AllTransactionList.addAll(transactionList);
		
		when(findAllTransactionsService.findAllTransactions()).thenReturn(AllTransactionList);

		mockMvc
			.perform(get("/transactions"))
			.andExpect(status().isOk());
		
		verify(saveTransferService, times(1)).saveTransfer(eTransaction3);
	}
	
	/*		when(findBankAccountByIbanService.findBankAccountByIban("OK1154631111123651111965411")).thenReturn(ebank);		
	when(findBankAccountByIbanService.findBankAccountByIban("AP1154631111123651111965411")).thenReturn(ebank2);		

	when(findTransactionByBankAccountService.findTransactionByBankAccount(ebank)).thenReturn(transactionList);
	when(findTransactionByBankAccountService.findTransactionByBankAccount(ebank2)).thenReturn(transactionList2);

*/
	
}
