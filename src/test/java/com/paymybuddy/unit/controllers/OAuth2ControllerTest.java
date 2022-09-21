package com.paymybuddy.unit.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.controller.OAuth2Controller;

@SpringBootTest
@AutoConfigureMockMvc
public class OAuth2ControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private OAuth2Controller oAuth2Controller;
	
	@BeforeEach
	public void setUp(WebApplicationContext context) {
		
		mockMvc = MockMvcBuilders
					.standaloneSetup(oAuth2Controller)
					.build();
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/oauth2"))
					.apply(springSecurity())
					.build();
		
	}

	@Test
	public void test() throws Exception{
		
		mockMvc
			.perform(get("/oauth2").with(oauth2Login())).andExpect(status().isOk());
		
	}
	
}
