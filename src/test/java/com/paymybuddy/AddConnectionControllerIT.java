package com.paymybuddy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.utils.WithMockCustomUser;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockCustomUser//(username="max", email="max"
//, password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" //{Bcrypt}
//, authorities ={"USER","ADMIN"})

public class AddConnectionControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@MockBean
	private SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@MockBean
	private FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;
	
	@MockBean
	private PaymybuddyUserDetails paymybuddyUserDetails;

	
	@BeforeEach
	public void setUp(WebApplicationContext context) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/addconnection"))
					.apply(springSecurity())
					.build();
		
	}
	
	@Test
	public void getAddconnection() throws Exception {
		
		mockMvc
			.perform(get("/addconnection"))
			.andExpect(status().isOk());
			//.andExpect(view().name(null));
		
	}
	
	//@Disabled
	@Test
	public void postAddconnection() throws Exception {
		
		BindingResult viewConnection = mock(BindingResult.class);
		when(viewConnection.getFieldValue("connection")).thenReturn("max");

		
/*		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("max");
		max.setUsername("max");*/

		when(findPaymybuddyUserDetailsService.findByEmail("max")).thenReturn(paymybuddyUserDetails);
		when(paymybuddyUserDetails.getEmail()).thenReturn("max");
		
		mockMvc
			.perform(post("/addconnection"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/addconnection?success=true"));
		
	}
	
	
}
