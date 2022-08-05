package com.paymybuddy.services.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;
import com.paymybuddy.service.bankaccount.FindBankAccountByUserEmailService;
import com.paymybuddy.service.connection.FindFconnectionByPayerUsernameService;
import com.paymybuddy.service.users.UserRole;

@ExtendWith(MockitoExtension.class)
@WithMockUser(roles = "USER")
public class FindFconnectionByPayerUsernameServiceTest {

	//todo: case empty list and list with other users

	
	@Mock
	PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository;

	@InjectMocks
	private FindFconnectionByPayerUsernameService findFconnectionByPayerUsernameService;

	@Test
	public void givenMaxWithTwoConnection_whenFindFconnectionByPayerUsernameServiceIsCalled_thenItSHouldReturnAListOfTwoUsers() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails();;
		max.setEmail("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		
		PaymybuddyUserDetails nax = new PaymybuddyUserDetails();
		nax.setEmail("nax");
		nax.setUsername("nax");
		nax.setBalance(20f);
		nax.setUserRole(UserRole.USER);

		PaymybuddyUserDetails sax = new PaymybuddyUserDetails();
		sax.setEmail("sax");
		sax.setUsername("sax");
		sax.setBalance(20f);
		sax.setUserRole(UserRole.USER);

		List<PaymybuddyUserDetails> connectionList = new ArrayList<>();
		connectionList.add(nax);
		connectionList.add(sax);
		
		max.setMyconnection(connectionList.stream().collect(Collectors.toSet()));

		Optional<PaymybuddyUserDetails> Omax = Optional.ofNullable(max);
		
		when(paymybuddyUserDetailsRepository.findByEmail("max")).thenReturn(Omax);

		List<PaymybuddyUserDetails> found = findFconnectionByPayerUsernameService.findByPayerUsername("max");

		assertThat(found).isEqualTo(connectionList);

	}

	
	@Test
	public void givenMaxWithoutConnection_whenFindFconnectionByPayerUsernameServiceIsCalled_thenItSHouldReturnAListWithNADefaultConnection() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails();;
		max.setEmail("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);

		Set<PaymybuddyUserDetails> connectionSet = new HashSet<>();

		max.setMyconnection(connectionSet);
		
		List<PaymybuddyUserDetails> connectionList = new ArrayList<>();
		connectionList.add(new PaymybuddyUserDetails("N_A"));

		Optional<PaymybuddyUserDetails> Omax = Optional.ofNullable(max);
		
		when(paymybuddyUserDetailsRepository.findByEmail("max")).thenReturn(Omax);

		List<PaymybuddyUserDetails> found = findFconnectionByPayerUsernameService.findByPayerUsername("max");

		assertThat(found).isEqualTo(connectionList);

	}

}
