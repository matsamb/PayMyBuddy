package com.paymybuddy.services.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.bankaccount.FindBankAccountByIbanService;
import com.paymybuddy.service.bankaccount.FindBankAccountByUserEmailService;
import com.paymybuddy.service.users.UserRole;

@ExtendWith(MockitoExtension.class)
@WithMockUser(username = "max", password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" // {Bcrypt}
		, authorities = { "USER", "ADMIN" })
public class FindBankAccountByIbanServiceTest {

	//todo: case empty list and list with other users
	
	@Mock
	BankAccountRepository bankAccountRepository;

	@InjectMocks
	private FindBankAccountByIbanService findBankAccountByIbanService;
	
	@Test
	public void givenAnIbanOfMax_whenFindBankAccountByIbanServiceIsCalled_thenItSHouldReturnMaxAsUser() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("max");
		max.setUsername("max");
		PaymybuddyPasswordEncoder p = new PaymybuddyPasswordEncoder();
		max.setPassword(p.getPasswordEncoder().encode("nax"));
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		
		EbankAccount ebankAccount = new EbankAccount();
		ebankAccount.setIban("TE3554631986511117541111116");
		ebankAccount.setUser(max);
		Optional<EbankAccount> bankAccount = Optional.ofNullable(ebankAccount);
		
/*		List<EbankAccount> foundAccountList = new ArrayList<>();
		foundAccountList.add(bankAccount);
		bankAccount.setIban("OP3554631986511117541111116");
		foundAccountList.add(bankAccount);
*/
		when(bankAccountRepository.findById("TE3554631986511117541111116")).thenReturn(bankAccount);

		EbankAccount found = findBankAccountByIbanService.findBankAccountByIban("TE3554631986511117541111116");

		assertThat(found.getUser()).isEqualTo(max);

	}

}
