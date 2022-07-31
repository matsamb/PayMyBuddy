package com.paymybuddy.services.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.BankAccountRepository;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.bankaccount.FindBankAccountByUserEmailService;
import com.paymybuddy.service.users.PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

//@Disabled
@SpringBootTest
//@DataJpaTest
//@WithUserDetails("dax@dax.dax")
public class FindBankAccountServiceIT {

	@Autowired
	private FindBankAccountByUserEmailService findBankAccountByUserEmailService;
	
	@Autowired
	private PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository;
	
	@Autowired
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
//	@Qualifier("testDataSource")
	//private javax.sql.DataSource testDataSource;

	@BeforeEach
	public void init() {

		jdbcTemplate.execute("use testpaymybuddy;");
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		( max).setEmail("nax@nax.nax");
		( max).setUsername("nax");
		PaymybuddyPasswordEncoder p = new PaymybuddyPasswordEncoder();
		max.setPassword(p.getPasswordEncoder().encode("nax"));
		( max).setBalance(20f);
		( max).setUserRole(UserRole.USER);
		
		paymybuddyUserDetailsRepository.save( max);
		
		EbankAccount bankAccount = new EbankAccount();
		bankAccount.setIban("TE3554631986511117541111115");
		bankAccount.setUser(max);
	
		bankAccountRepository.save(bankAccount);

		
		List<EbankAccount> foundAccountList = new ArrayList<>();
		foundAccountList.add(bankAccount);
		bankAccount.setIban("TE3554631986511117541111116");
		foundAccountList.add(bankAccount);

		bankAccountRepository.save(bankAccount);

	}

	//@Disabled
	@Test
	@WithMockUser(roles="USER")
	public void givenMaxWithTwoBankAccount_whenFindBankAccountServiceIsCalled_thenItSHouldReturnMaxAsUser() throws Exception {

		jdbcTemplate.execute("use testpaymybuddy;");

		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		( max).setEmail("nax@nax.nax");
		( max).setUsername("nax");
		PaymybuddyPasswordEncoder p = new PaymybuddyPasswordEncoder();
		max.setPassword(p.getPasswordEncoder().encode("nax"));
		( max).setBalance(20f);
		( max).setUserRole(UserRole.USER);
		
		paymybuddyUserDetailsRepository.save( max);
				
	//	List<EbankAccount> found = findBankAccountByUserEmailService.findBankAccountByUserEmail("max@max.max");
/*
		paymybuddyUserDetailsRepository.delete( max);
		bankAccountRepository.delete(found.get(0));
		bankAccountRepository.delete(found.get(1));
		*/
	//	assertThat(found.get(0).getUser()).isEqualTo(max);
		
	}

	@Disabled
	@Test
	@WithMockUser(roles="USER")
	public void givenMaxWithTwoBankAccount_whenFindBankAccountServiceIsCalled_thenItSHouldReturnAsizeTwoList() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		( max).setEmail("nax@nax.nax");
		( max).setUsername("max");
		( max).setBalance(20f);
		( max).setUserRole(UserRole.USER);
		
		List<EbankAccount> found = findBankAccountByUserEmailService.findBankAccountByUserEmail("max@max.max");

/*		paymybuddyUserDetailsRepository.delete( max);
		bankAccountRepository.delete(found.get(0));
		bankAccountRepository.delete(found.get(1));
*/
		assertThat(found.size()).isEqualTo(2);

	}

}
