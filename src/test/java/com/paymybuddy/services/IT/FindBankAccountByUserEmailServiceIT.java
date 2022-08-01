package com.paymybuddy.services.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;

import org.junit.jupiter.api.BeforeAll;
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
public class FindBankAccountByUserEmailServiceIT {

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

		PaymybuddyPasswordEncoder p = new PaymybuddyPasswordEncoder();
	
		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("nax@nax.nax");
		max.setUsername("nax");
		max.setPassword(p.getPasswordEncoder().encode("nax"));
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		max.setEnabled(true);
		
		paymybuddyUserDetailsRepository.save( max);
		
		EbankAccount bankAccount = new EbankAccount();
		bankAccount.setIban("TE3554631986511117541111115");
		bankAccount.setUser(max);
		EbankAccount bankAccount2 = new EbankAccount();
	
//		bankAccountRepository.save(bankAccount);

		
		List<EbankAccount> foundAccountList = new ArrayList<>();
		foundAccountList.add(bankAccount);
		bankAccount2.setIban("OP3554631986511117541111116");
		bankAccount2.setUser(max);
		foundAccountList.add(bankAccount2);

		bankAccountRepository.saveAll(foundAccountList);

	}

	@Test
	@WithMockUser(roles="USER")
	public void givenMaxWithTwoBankAccount_whenFindBankAccountServiceIsCalled_thenItSHouldReturnMaxAsUser() throws Exception {

		jdbcTemplate.execute("use testpaymybuddy;");

		PaymybuddyPasswordEncoder p = new PaymybuddyPasswordEncoder();

		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("nax@nax.nax");
		max.setUsername("nax");
		max.setPassword(p.getPasswordEncoder().encode("nax"));
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		max.setEnabled(true);
		
		paymybuddyUserDetailsRepository.save( max);
				
		List<EbankAccount> found = findBankAccountByUserEmailService.findBankAccountByUserEmail("nax@nax.nax");

		bankAccountRepository.delete(found.get(0));
		bankAccountRepository.delete(found.get(1));

		paymybuddyUserDetailsRepository.delete( max);
		
		assertThat(found.get(0).getUser()).isEqualTo(max);
		
	}

	@Test
	@WithMockUser(roles="USER")
	public void givenMaxWithTwoBankAccount_whenFindBankAccountServiceIsCalled_thenItSHouldReturnAsizeTwoList() throws Exception {

		jdbcTemplate.execute("use testpaymybuddy;");
		
		PaymybuddyPasswordEncoder p = new PaymybuddyPasswordEncoder();
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("nax@nax.nax");
		max.setUsername("nax");
		max.setPassword(p.getPasswordEncoder().encode("nax"));
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		max.setEnabled(true);

		
		List<EbankAccount> found = findBankAccountByUserEmailService.findBankAccountByUserEmail("nax@nax.nax");

		bankAccountRepository.delete(found.get(0));
		bankAccountRepository.delete(found.get(1));

		paymybuddyUserDetailsRepository.delete( max);

		assertThat(found.size()).isEqualTo(2);

	}

}
