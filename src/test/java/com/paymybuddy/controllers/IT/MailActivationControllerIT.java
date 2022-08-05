package com.paymybuddy.controllers.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.paymybuddy.entity.ActivationToken;
import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.Etransaction;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.activationtoken.FindActivationTokenByTokenService;
import com.paymybuddy.service.bankaccount.FindBankAccountByIbanService;
import com.paymybuddy.service.transfer.FindAllTransactionsService;
import com.paymybuddy.service.transfer.FindTransactionByBankAccountService;
import com.paymybuddy.service.transfer.SaveTransferService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;
import com.paymybuddy.utils.WithMockPayMyBuddyUser;

@SpringBootTest
@AutoConfigureMockMvc
public class MailActivationControllerIT {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	FindActivationTokenByTokenService findActivationTokenByTokenService;

	@MockBean
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;
	
	@BeforeEach
	public void setUp(WebApplicationContext context) {
				
		mockMvc = MockMvcBuilders
					.webAppContextSetup(context)
					.defaultRequest(get("/accountactivation"))
					.apply(springSecurity())
					.build();
		
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void givenAvalidToken_whenAccountactivationIsCalled_ThenItShouldSaveUpdatedUserOnce() throws Exception{
		
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		Timestamp expireTime = new Timestamp(System.currentTimeMillis()+60*1000);
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max@max.max");
		max.setName("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		max.setEnabled(false);
		
		ActivationToken activationToken = new ActivationToken();
		activationToken.setToken("ken");
		activationToken.setStartTime(startTime);
		activationToken.setExpirationTime(expireTime);
		activationToken.setId(1);
		activationToken.setUser(max);
		
		when(findActivationTokenByTokenService.findByToken("ken")).thenReturn(activationToken);
		
		mockMvc
			.perform(get("/accountactivation")
					.param("token", "ken"))
			.andExpect(status().isOk());
		
		verify(savePaymybuddyUserDetailsService, times(1)).savePaymybuddyUserDetails(max);
		
	}

	
	@Test
	@WithMockUser(roles = "USER")
	public void givenAnExpiredToken_whenAccountactivationIsCalled_ThenItShouldRedirectToTokenexpired() throws Exception{
		
		Timestamp startTime = new Timestamp(System.currentTimeMillis()-60*1000);
		Timestamp expireTime = new Timestamp(System.currentTimeMillis());
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max@max.max");
		max.setName("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		max.setEnabled(false);
		
		ActivationToken activationToken = new ActivationToken();
		activationToken.setToken("ken");
		activationToken.setStartTime(startTime);
		activationToken.setExpirationTime(expireTime);
		activationToken.setId(1);
		activationToken.setUser(max);
		
		when(findActivationTokenByTokenService.findByToken("ken")).thenReturn(activationToken);

	mockMvc
	.perform(get("/accountactivation")
			.param("token", "ken"))
	.andExpect(status().is3xxRedirection())
	.andExpect(view().name("redirect:/tokenexpired"));
	}
	
	@Test
	@WithMockUser(roles = "USER")
	public void givenAEnabledUser_whenAccountactivationIsCalled_ThenItShouldRedirectToTokenexpired() throws Exception{
		
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		Timestamp expireTime = new Timestamp(System.currentTimeMillis()+60*1000);
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails("max@max.max");
		max.setName("max");
		max.setUsername("max");
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		max.setEnabled(true);
		
		ActivationToken activationToken = new ActivationToken();
		activationToken.setToken("ken");
		activationToken.setStartTime(startTime);
		activationToken.setExpirationTime(expireTime);
		activationToken.setId(1);
		activationToken.setUser(max);
		
		when(findActivationTokenByTokenService.findByToken("ken")).thenReturn(activationToken);

	mockMvc
	.perform(get("/accountactivation")
			.param("token", "ken"))
	.andExpect(status().is3xxRedirection())
	.andExpect(view().name("redirect:/tokenexpired"));
	}
}
