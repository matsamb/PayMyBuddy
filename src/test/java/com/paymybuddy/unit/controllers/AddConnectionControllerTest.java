package com.paymybuddy.unit.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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

import com.paymybuddy.controller.AddConnectionController;

@SpringBootTest
@AutoConfigureMockMvc
public class AddConnectionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AddConnectionController addConnectionController;
	
	@BeforeEach
	public void setUp(WebApplicationContext context) {
		
		mockMvc = MockMvcBuilders
					.standaloneSetup(addConnectionController)
					.build();
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/addconnection"))
					.apply(springSecurity())
					.build();
		
	}
	
	@Test
	public void getAddconnetion() throws Exception {
		
		mockMvc
			.perform(get("/addconnection").with(user("max").password("pass").roles("USER")))
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void errorGetAddconnetionWithOutAuthentication() throws Exception {
		
		mockMvc
			.perform(get("/addconnection"))
			.andExpect(status().isUnauthorized());
		
	}
	
}
