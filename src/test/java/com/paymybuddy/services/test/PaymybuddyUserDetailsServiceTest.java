package com.paymybuddy.services.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

@ExtendWith(MockitoExtension.class)
@WithMockUser(roles = "USER")
public class PaymybuddyUserDetailsServiceTest {

	@Mock
	private PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository;

	@InjectMocks
	private PaymybuddyUserDetailsService paymybuddyUserDetailsService;

	@Test
	public void givenMaxAuser_whenPaymybuddyUserDetailsServiceCalled_thenItShouldReturnMaxUsername()
			throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max");
		HashMap<String, Object> attributes = new HashMap<>();
		max.setName("max");
		max.setPassword("max");
		max.setUsername("max");
		max.setEnabled(true);
		attributes.put("email", "max");
		max.setAttributes(attributes);
		
		Optional<PaymybuddyUserDetails> oMax = Optional.of(max);

		PaymybuddyUserDetails nax = new PaymybuddyUserDetails();
		nax.setEmail("nax");
		nax.setName("nax");
		nax.setUsername("nax");
		nax.setBalance(20f);
		nax.setUserRole(UserRole.USER);

		List<PaymybuddyUserDetails> userList = new ArrayList<>();
		userList.add(max);
		userList.add(nax);

		when(paymybuddyUserDetailsRepository.findByEmail("max")).thenReturn(oMax);

		UserDetails result = paymybuddyUserDetailsService.loadUserByUsername("max");

		assertThat(result.getUsername()).isEqualTo(max.getUsername());

	}
	
	@Test
	public void givenMaxAuserNotEnabled_whenPaymybuddyUserDetailsServiceCalled_thenItShouldReturnNAasUsername()
			throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max");
		HashMap<String, Object> attributes = new HashMap<>();
		max.setName("max");
		max.setPassword("max");
		max.setUsername("max");
		max.setEnabled(false);
		attributes.put("email", "max");
		max.setAttributes(attributes);
		
		Optional<PaymybuddyUserDetails> oMax = Optional.of(max);

		PaymybuddyUserDetails nax = new PaymybuddyUserDetails("N_A");

		List<PaymybuddyUserDetails> userList = new ArrayList<>();
		userList.add(max);

		when(paymybuddyUserDetailsRepository.findByEmail("max")).thenReturn(oMax);

		UserDetails result = paymybuddyUserDetailsService.loadUserByUsername("max");

		assertThat(result.getUsername()).isEqualTo(nax.getUsername());

	}

	@Test
	public void notFoundUserShouldThrowUsernameNotFoundException() throws Exception{

		PaymybuddyUserDetails nax = new PaymybuddyUserDetails();
		nax.setEmail("nax");
		nax.setName("nax");
		nax.setUsername("nax");
		nax.setBalance(20f);
		nax.setUserRole(UserRole.USER);

		List<PaymybuddyUserDetails> userList = new ArrayList<>();
		userList.add(nax);
		
		UsernameNotFoundException e = assertThrows(UsernameNotFoundException.class,() -> {
					UserDetails result = paymybuddyUserDetailsService.loadUserByUsername("max");
				});

		assertThat("max" + " not found").isEqualTo(e.getMessage());
		
	}

}
