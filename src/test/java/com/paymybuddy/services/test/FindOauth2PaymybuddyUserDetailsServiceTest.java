package com.paymybuddy.services.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

@ExtendWith(MockitoExtension.class)
@WithMockUser(roles = "USER")
public class FindOauth2PaymybuddyUserDetailsServiceTest {

	@Mock
	private PaymybuddyUserDetailsRepository paymybuddyUserDetailsRepository;

	@InjectMocks
	private FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

	@Test
	public void givenMaxAuser_whenFindOauth2PaymybuddyUserDetailsServiceCalled_thenItShouldReturnMax() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max");
		HashMap<String, Object> attributes = new HashMap<>();
		max.setName("max");
		attributes.put("email", "max");
		max.setAttributes(attributes);

		PaymybuddyUserDetails nax = new PaymybuddyUserDetails();
		nax.setEmail("nax");
		nax.setName("nax");
		nax.setUsername("nax");
		nax.setBalance(20f);
		nax.setUserRole(UserRole.USER);

		List<PaymybuddyUserDetails> userList = new ArrayList<>();
		userList.add(max);
		userList.add(nax);

		when(paymybuddyUserDetailsRepository.findAll()).thenReturn(userList);

		PaymybuddyUserDetails result = findOauth2PaymybuddyUserDetailsService.findByName("max");

		assertThat(result).isEqualTo(max);

	}

	@Test
	public void emptyListShouldReturnDefaultUser() throws Exception {

		PaymybuddyUserDetails nax = new PaymybuddyUserDetails("N_A");

		List<PaymybuddyUserDetails> userList = new ArrayList<>();

		when(paymybuddyUserDetailsRepository.findAll()).thenReturn(userList);

		PaymybuddyUserDetails result = findOauth2PaymybuddyUserDetailsService.findByName("max");

		assertThat(result).isEqualTo(nax);

	}

}
