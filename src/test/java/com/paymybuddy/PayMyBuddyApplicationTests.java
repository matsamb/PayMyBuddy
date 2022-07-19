package com.paymybuddy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.paymybuddy.dto.ViewUser;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.PaymybuddyPasswordEncoder;
import com.paymybuddy.service.users.PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;
import com.paymybuddy.service.users.UserRole;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(SignInController.class)
class PayMyBuddyApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	/*@Autowired
	private SignInController signInController;*/

	@MockBean
	private SavePaymybuddyUserDetailsService mockSavePaymybuddyUserDetailsService;

	@MockBean
	private PaymybuddyUserDetailsService mockPaymybuddyUserDetailsService;
	
	@MockBean
	private PaymybuddyPasswordEncoder mockPaymybuddyPasswordEncoder;
	
	@MockBean
	private PasswordEncoder mockPasswordEncoder;
	
	
	@Test
	void contextLoads() {
	}
	
	@Disabled
	@Test
	@WithMockUser(username="max"
	, password = /*{Bcrypt}*/ "$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q"
	, authorities ="USER")//"USER","ADMIN"})
	public void GivenNewUserMaxWithPassWordm456_WhenPostingSignIn_ThenUserDetailsShouldBeSavedOnce() throws Exception {

		BindingResult result = mock(BindingResult.class);
		when(result.getFieldValue("balance")).thenReturn(0.0f);
		when(result.getFieldValue("name")).thenReturn("max");
		when(result.getFieldValue("confirmpassword")).thenReturn("m456");

		ViewUser neweuser = new ViewUser("max", "m456");

		when(result.getFieldValue("username")).thenReturn(neweuser.getUsername());
		when(result.getFieldValue("password")).thenReturn(neweuser.getPassword());
		
		UserDetails max = new PaymybuddyUserDetails();
		//((PaymybuddyUserDetails) max).setEmail("max");
		//((PaymybuddyUserDetails) max).setUsername("max");
		//((PaymybuddyUserDetails) max).setPassword("m456");
		//((PaymybuddyUserDetails) max).setBalance(0.0f);
		((PaymybuddyUserDetails) max).setUserRole(UserRole.USER);
		((PaymybuddyUserDetails) max).setEnabled(true);
				
		when(mockPaymybuddyUserDetailsService
				.loadUserByUsername("max"))
			.thenReturn(max);
		
		// password : m456
		when(mockPaymybuddyPasswordEncoder.getPasswordEncoder())
		.thenReturn(mockPasswordEncoder);
		
		when(mockPasswordEncoder.encode("m456"))
			.thenReturn("$2a$10$NXBSSouHIS/yq0NQCrFADuInO6IqS0XYNVmu7kfl.zTDrzH93gI4q");

		mockMvc
			.perform(post("/signin"))
			.andExpect(status().is3xxRedirection())//;
			.andExpect(view().name("redirect:/signinconfirm"));

		verify(mockSavePaymybuddyUserDetailsService, times(1)).savePaymybuddyUserDetails((PaymybuddyUserDetails)max);

	}

}
