package com.paymybuddy.controllers.IT;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AddConnectionControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@MockBean
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@MockBean
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;

	@BeforeEach
	public void setUp(WebApplicationContext context) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/addconnection"))
					.apply(springSecurity())
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
		
		PaymybuddyUserDetails nax = new PaymybuddyUserDetails("N_A");
		
		when(findPaymybuddyUserDetailsService.findByEmail("nax")).thenReturn(nax);
		
		mockMvc
			.perform(post("/addconnection")
					.param("connection", "nax"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/addconnection?error=true"));
	
	}
	
	@Test
	@WithMockUser(username="max"
	, password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" //{Bcrypt}
	, authorities ={"USER","ADMIN"})
	public void whenEntreredRegisteredNax_whenPostAddconnection_thenSaveUserShouldBeUsedOnce() throws Exception {
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("max");
		max.setName("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		
		PaymybuddyUserDetails nax = new PaymybuddyUserDetails();
		max.setEmail("nax");
		max.setName("nax");
		max.setUsername("nax");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		
		List<EbankAccount> bankset = new ArrayList<>();
		EbankAccount maxbank = new EbankAccount("AP1154631111123651111965411");
		maxbank.setUser(max);
		bankset.add(maxbank);
		max.setMybankAccount(bankset.stream().collect(Collectors.toSet()));
		
		when(findPaymybuddyUserDetailsService.findByEmail("max")).thenReturn(max);

		when(findPaymybuddyUserDetailsService.findByEmail("nax")).thenReturn(nax);
		
		mockMvc
		.perform(post("/addconnection")
				.param("connection", "nax"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/addconnection?success=true"));
		
		verify(savePaymybuddyUserDetailsService, times(1)).savePaymybuddyUserDetails(max);
	
	}
	
	
}
