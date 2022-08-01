package com.paymybuddy.services.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.service.bankaccount.FindBankAccountByUserEmailService;
import com.paymybuddy.service.users.UserRole;

@ExtendWith(MockitoExtension.class)
@WithMockUser(username = "max", password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" // {Bcrypt}
		, authorities = { "USER", "ADMIN" })
public class FindBankAccountByUserEmailServiceTest {

	//todo: case empty list and list with other users

	
	@Mock
	BankAccountRepository bankAccountRepository;

	@InjectMocks
	private FindBankAccountByUserEmailService findBankAccountByUserEmailService;

	@Test
	public void givenMaxWithTwoBankAccount_whenFindBankAccountServiceIsCalled_thenItSHouldReturnMaxAsUser() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		
		EbankAccount bankAccount = new EbankAccount();
		bankAccount.setIban("TE3554631986511117541111116");
		bankAccount.setUser(max);
		List<EbankAccount> foundAccountList = new ArrayList<>();
		EbankAccount bankAccount2 = new EbankAccount();
		foundAccountList.add((EbankAccount) bankAccount.clone());
		bankAccount2.setIban("OP3554631986511117541111116");
		bankAccount2.setUser(max);
		foundAccountList.add(bankAccount2);

		when(bankAccountRepository.findAll()).thenReturn(foundAccountList);

		List<EbankAccount> found = findBankAccountByUserEmailService.findBankAccountByUserEmail("max");

		assertThat(found.get(0).getUser()).isEqualTo(max);

	}

	
	@Test
	public void givenMaxWithTwoBankAccount_whenFindBankAccountByUserEmailServiceIsCalled_thenItSHouldReturnAsizeTwoList() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		
		EbankAccount bankAccount = new EbankAccount();
		bankAccount.setIban("TE3554631986511117541111116");
		bankAccount.setUser(max);
		List<EbankAccount> foundAccountList = new ArrayList<>();
		EbankAccount bankAccount2 = new EbankAccount();
		foundAccountList.add(bankAccount); 
		bankAccount2.setIban("OP3554631986511117541111116");
		bankAccount2.setUser(max);
		foundAccountList.add(bankAccount2);

		when(bankAccountRepository.findAll()).thenReturn(foundAccountList);

		List<EbankAccount> found = findBankAccountByUserEmailService.findBankAccountByUserEmail("max");

		assertThat(found.size()).isEqualTo(2);

	}

}
