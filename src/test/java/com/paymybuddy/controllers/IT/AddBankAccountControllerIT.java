package com.paymybuddy.controllers.IT;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.bankaccount.SaveBankAccountService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.utils.WithMockPayMyBuddyUser;

@SpringBootTest
@AutoConfigureMockMvc
public class AddBankAccountControllerIT {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SaveBankAccountService saveBankAccountService;
	
	@MockBean
	private SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@BeforeEach
	public void setUp(WebApplicationContext context) {
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/addbankaccount"))
					.apply(springSecurity())	//.defaultRequest(get("/signin"))
					.build();
		
	}
	
	@Test
	@WithMockUser(username="max"
	, password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" //{Bcrypt}
	, authorities ={"USER","ADMIN"})
	public void getAddbankaccount() throws Exception {
		
		mockMvc
			.perform(get("/addbankaccount"))
			.andExpect(status().isOk())
			.andExpect(view().name("addbankaccount"));
	
	}
	
	@Test
	@WithMockPayMyBuddyUser(email="max",username="max"
	, password = "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q" //{Bcrypt}
	)
	public void postAddBankAccountAndVerifySavePaymybuddyUserServiceUsedOnce() throws Exception {

		PaymybuddyUserDetails max = new PaymybuddyUserDetails("N_A");
		//max.setEmail("max");
		//max.setUsername("max");
		
		mockMvc
			.perform(post("/addbankaccount")
/*					.param("country", "OP")
					.param("controlkey", "45")
					.param("bankcode", "31654")
					.param("branch", "5478")
					.param("accountnumberA", "6325")
					.param("accountnumberB", "9731")
					.param("accountnumberC", "5726")
					.param("accountkey", "31")*/)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/addbankaccount?success=true"));
		
		verify(savePaymybuddyUserDetailsService, times(1)).savePaymybuddyUserDetails(max);
		
	}	
	
}
