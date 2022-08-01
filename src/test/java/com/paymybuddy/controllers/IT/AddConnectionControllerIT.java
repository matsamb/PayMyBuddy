package com.paymybuddy.controllers.IT;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AddConnectionControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
//	@MockBean
//	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@MockBean
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@MockBean
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

	@BeforeEach
	public void setUp(WebApplicationContext context) {
		
/*		mockMvc = MockMvcBuilders
					.standaloneSetup(homeController)
					.build();*/
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/addconnection"))
					.apply(springSecurity())	//.defaultRequest(get("/signin"))
					.build();
		
	}
	
	@Test
	@WithMockUser(username="max"
	, password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" //{Bcrypt}
	, authorities ={"USER","ADMIN"})
	public void getAddconnection() throws Exception {
		
		mockMvc
			.perform(get("/addconnection"))
			.andExpect(status().isOk())
			.andExpect(view().name("addconnection"));
	
	}
	
	@Test
	@WithMockUser(username="max"
	, password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" //{Bcrypt}
	, authorities ={"USER","ADMIN"})
	public void postAddconnection() throws Exception {
		
		mockMvc
			.perform(post("/addconnection"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/addconnection?error=true"));
	
	}
	
	
}
