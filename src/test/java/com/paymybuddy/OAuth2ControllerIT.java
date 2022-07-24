package com.paymybuddy;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.utils.WithMockCustomUser;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc
public class OAuth2ControllerIT {
//TODO
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PaymybuddyPasswordEncoder mockPaymybuddyPasswordEncoder;

	@MockBean
	private SavePaymybuddyUserDetailsService mockSavePaymybuddyUserDetailsService;

	@MockBean
	private FindPaymybuddyUserDetailsService mockFindPaymybuddyUserDetailsService;

	@MockBean
	private PasswordEncoder mockPasswordEncoder;
	
	Authentication authentication;

	@BeforeEach
	public void setUp(WebApplicationContext context) {
		
	/*	mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.build();*/
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/signin"))
					.apply(springSecurity())	//.defaultRequest(get("/signin"))
					.build();
		
	}
	
	@Test
	@WithMockCustomUser()
	public void test() throws Exception{
		
		PaymybuddyUserDetails userTosave = new PaymybuddyUserDetails();
		
		mockMvc
				.perform(get("/oauth2"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("/oauth2"));
		
		verify(mockSavePaymybuddyUserDetailsService, times(2)).savePaymybuddyUserDetails(userTosave);
		
	}
}
