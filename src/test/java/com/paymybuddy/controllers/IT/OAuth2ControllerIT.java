package com.paymybuddy.controllers.IT;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;

@SpringBootTest
@AutoConfigureMockMvc
public class OAuth2ControllerIT {

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

		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/signin"))
					.apply(springSecurity())
					.build();
		
	}
	
	@Test
	public void registeredOAuth2UserAuthentication() throws Exception{
				
		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max");
		HashMap<String,Object> attributes = new HashMap<>();
		max.setName("max"); 
		attributes.put("email", "max");
		attributes.put("name", "max");
		attributes.put("username", "max");
		max.setAttributes(attributes);	
		
		when(mockFindPaymybuddyUserDetailsService.findByEmail("max")).thenReturn(max);
		
		mockMvc
				.perform(get("/oauth2").with(oauth2Login().oauth2User(max)))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/home?size=3&page=1"));
				
	}

	@Test
	public void newOAuth2UserAuthentication() throws Exception{
			 	
		PaymybuddyUserDetails max = new PaymybuddyUserDetails("N_A");
		HashMap<String,Object> attributes = new HashMap<>();
		max.setName("N_A"); 
		attributes.put("email", "N_A");
		attributes.put("name", "N_A");
		attributes.put("username", "N_A");

		max.setAttributes(attributes);	
		
		when(mockFindPaymybuddyUserDetailsService.findByEmail("N_A")).thenReturn(max);
		when(mockPaymybuddyPasswordEncoder.getPasswordEncoder()).thenReturn(mockPasswordEncoder);

		mockMvc
				.perform(get("/oauth2").with(oauth2Login().oauth2User(max)))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("/oauth2"));
		
		verify(mockSavePaymybuddyUserDetailsService, times(1)).savePaymybuddyUserDetails(max);
		
	}
}
