package com.paymybuddy.unit.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.paymybuddy.controller.SignInController;

@SpringBootTest
@AutoConfigureMockMvc
public class SignInControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SignInController signInController;

	@BeforeEach
	public void setUp(WebApplicationContext context) {
		
		mockMvc = MockMvcBuilders
					.standaloneSetup(signInController)
					.build();
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/signin"))
					.apply(springSecurity())	//.defaultRequest(get("/signin"))
					.build();
		
	}
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void testGetSignin() throws Exception {
	
		mockMvc
		.perform(get("/signin")).andExpect(status().isOk());
				
	}
	
	@Test
	public void testPostSignin() throws Exception {
	
		mockMvc
		.perform(post("/signin")).andExpect(status().isOk());
				
	}
	
	@Test
	public void testGetSigninConfirm() throws Exception {
	
		mockMvc
		.perform(get("/signinconfirm")).andExpect(status().isOk());
				
	}

}
