package com.paymybuddy.unit.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.controller.HomeController;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private HomeController homeController;
	
	@BeforeEach
	public void setUp(WebApplicationContext context) {
		
		mockMvc = MockMvcBuilders
					.standaloneSetup(homeController)
					.build();
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/home"))
					.apply(springSecurity())	
					.build();
		
	}
	
	//@Disabled
	@Test
	public void getHome() throws Exception{
		
		mockMvc
			.perform(get("/home").with(user("max").password("pass").roles("USER")))
			.andExpect(status().isOk());
	}
	
	@Test
	public void errorGetHomeWithOutAuthentication() throws Exception{
		
		mockMvc
			.perform(get("/home"))
			.andExpect(status().isUnauthorized());
	}

	
}
