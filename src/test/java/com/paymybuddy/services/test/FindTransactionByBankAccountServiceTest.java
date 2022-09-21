package com.paymybuddy.services.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.TransactionRepository;
import com.paymybuddy.service.transfer.FindTransactionByBankAccountService;
import com.paymybuddy.service.users.UserRole;

@ExtendWith(MockitoExtension.class)
@WithMockUser(roles = "USER")
public class FindTransactionByBankAccountServiceTest {

	@Mock
	private TransactionRepository transactionRepository;
	
	@InjectMocks
	private FindTransactionByBankAccountService findTransactionByBankAccountService;
	
	@Test
	public void givenOneTransactionRegistered_whenFindTransactionByBankAccountServiceIsCalled_thenItShouldReturnTheTransaction() throws Exception{
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("max");
		max.setName("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);

		
		EbankAccount ebank = new EbankAccount();
		ebank.setIban("OK1154631111123651111965411");
		ebank.setUser(max);
		
		Etransaction eTransaction = new Etransaction();
		eTransaction.setTransactionId(1);
		eTransaction.setBankAccount(ebank);
		eTransaction.setAmount(16f);
		eTransaction.setBankTransactionId(3);
		eTransaction.setFromBank(false);
		
		List<Etransaction> transactionList = new ArrayList<>();
		transactionList.add(eTransaction);
	
		when(transactionRepository.findAll()).thenReturn(transactionList);
		
		List<Etransaction> resultList = findTransactionByBankAccountService.findTransactionByBankAccount(ebank);
		
		assertThat(resultList.get(0)).isEqualTo(eTransaction);
	
	}
	
	@Test
	public void givenNoRegisteredTransactionForGivenBankAccount_whenFindTransactionByBankAccountServiceIsCalled_thenItShouldReturnDefaultTransaction() throws Exception{
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("max");
		max.setName("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);

		
		EbankAccount ebank = new EbankAccount();
		ebank.setIban("OK1154631111123651111965411");
		ebank.setUser(max);
		
		EbankAccount ebank2 = new EbankAccount();
		ebank.setIban("KO1154631111123651111965411");
		ebank.setUser(max);
		
		EbankAccount ebank3 = new EbankAccount("N_A");

		Etransaction eTransaction = new Etransaction();
		eTransaction.setTransactionId(1);
		eTransaction.setBankAccount(ebank2);
		eTransaction.setAmount(16f);
		eTransaction.setBankTransactionId(3);
		eTransaction.setFromBank(false);
		
		Etransaction returnesdTransaction = new Etransaction();
		returnesdTransaction.setBankAccount(ebank3);
		
		List<Etransaction> transactionList = new ArrayList<>();
		transactionList.add(eTransaction);
	
		when(transactionRepository.findAll()).thenReturn(transactionList);
		
		List<Etransaction> resultList = findTransactionByBankAccountService.findTransactionByBankAccount(ebank);
		
		assertThat(resultList.get(0)).isEqualTo(returnesdTransaction);
	
	}
	
	@Test
	public void givenNoRegisteredTransaction_whenFindTransactionByBankAccountServiceIsCalled_thenItShouldReturnDefaultTransaction() throws Exception{

		EbankAccount ebank = new EbankAccount();
		ebank.setIban("OK1154631111123651111965411");

		EbankAccount ebank3 = new EbankAccount("N_A");
	
		Etransaction returnesdTransaction = new Etransaction();
		returnesdTransaction.setBankAccount(ebank3);
		
		List<Etransaction> transactionList = new ArrayList<>();
	
		when(transactionRepository.findAll()).thenReturn(transactionList);
		
		List<Etransaction> resultList = findTransactionByBankAccountService.findTransactionByBankAccount(ebank);
		
		assertThat(resultList.get(0)).isEqualTo(returnesdTransaction);
	
	}
	
}
