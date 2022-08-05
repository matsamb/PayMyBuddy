package com.paymybuddy.controller;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.ViewConnection;
import com.paymybuddy.dto.ViewIban;
import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.bankaccount.SaveBankAccountService;
import com.paymybuddy.service.users.FindOauth2PaymybuddyUserDetailsService;
import com.paymybuddy.service.users.FindPaymybuddyUserDetailsService;
import com.paymybuddy.service.users.SavePaymybuddyUserDetailsService;

import lombok.AllArgsConstructor;

@RolesAllowed("USER")
@Controller
//@AllArgsConstructor
public class AddBankAccountController {

	final static Logger LOGGER = LogManager.getLogger("AddBankAccount");

	@Autowired
	SaveBankAccountService saveBankAccountService;
	
	@Autowired
	FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService;

	@Autowired
	SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService;

	@Autowired
	FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService;
	
	AddBankAccountController(FindPaymybuddyUserDetailsService findPaymybuddyUserDetailsService 
			 ,SavePaymybuddyUserDetailsService savePaymybuddyUserDetailsService	
			 ,SaveBankAccountService saveBankAccountService
			 ,FindOauth2PaymybuddyUserDetailsService findOauth2PaymybuddyUserDetailsService
			 ){ 
		 this.findPaymybuddyUserDetailsService = findPaymybuddyUserDetailsService;
		 this.savePaymybuddyUserDetailsService = savePaymybuddyUserDetailsService;
		 this.findOauth2PaymybuddyUserDetailsService = findOauth2PaymybuddyUserDetailsService;
		 this.saveBankAccountService = saveBankAccountService;
	 }

	@GetMapping("/addbankaccount")
	public String getAddConnection(Authentication auth, ViewIban viewIban, BindingResult binding) {
		LOGGER.info("addbankaccount page displayed");
		return "addbankaccount";
	}

	@PostMapping("/addbankaccount")
	public ModelAndView createConnection(Authentication auth, ViewIban viewIban, BindingResult binding) {

		ModelAndView result;
		
		String loggedUserEmail;
		
		StringBuilder ibanBuilder = new StringBuilder();
		

		LOGGER.debug(viewIban);
		LOGGER.info("addbankaccount page displayed and bank account posted");
//		LOGGER.debug(findPaymybuddyUserDetailsService.findByEmail(viewConnection.getConnection()));
				
		if (auth instanceof UsernamePasswordAuthenticationToken) {
			LOGGER.info(auth.getName() + " is instance of UsernamePasswordAuthenticationToken");
			loggedUserEmail = auth.getName();

		} else {
			LOGGER.info(auth.getName() + " is instance of OAuth2AuthenticationToken");
			String nameNumber = auth.getName();
			PaymybuddyUserDetails foundOauth2 = findOauth2PaymybuddyUserDetailsService.findByName(nameNumber);
			loggedUserEmail = foundOauth2.getEmail();

		}
		
//		LOGGER.trace(findPaymybuddyUserDetailsService.findByEmail(viewConnection.getConnection()));

		ibanBuilder.append(viewIban.getCountry());
		ibanBuilder.append(viewIban.getControlkey());
		ibanBuilder.append(viewIban.getBankcode());
		ibanBuilder.append(viewIban.getBranch());
		ibanBuilder.append(viewIban.getAccountnumberA());
		ibanBuilder.append(viewIban.getAccountnumberB());
		ibanBuilder.append(viewIban.getAccountnumberC());
		ibanBuilder.append(viewIban.getAccountkey());
		LOGGER.info("builder bank account "+ibanBuilder);
		
		String iban = ibanBuilder.toString();
		LOGGER.info("iban "+iban);
		
		EbankAccount currentUserBankAccount = new EbankAccount();
		Set<EbankAccount> currentUserBankAccountSet = new HashSet<>();
		PaymybuddyUserDetails currentUser = findPaymybuddyUserDetailsService.findByEmail(loggedUserEmail);
		LOGGER.info(currentUser);
		
		currentUserBankAccount.setIban(iban);
		currentUserBankAccount.setUser(currentUser);	
		LOGGER.info(iban);
		saveBankAccountService.saveBankAccount(currentUserBankAccount);
		
		currentUserBankAccountSet.add(currentUserBankAccount);
		currentUser.setMybankAccount(currentUserBankAccountSet);
		LOGGER.info("loading into database "+currentUser);
		
		savePaymybuddyUserDetailsService.savePaymybuddyUserDetails(currentUser);
		result = new ModelAndView("redirect:/addbankaccount?success=true");

		return result;

	}

}
