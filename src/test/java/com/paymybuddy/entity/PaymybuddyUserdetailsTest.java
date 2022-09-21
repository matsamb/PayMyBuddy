package com.paymybuddy.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import com.paymybuddy.service.users.UserRole;

public class PaymybuddyUserdetailsTest {

	@Test
	public void cloneTest() {
		
		PaymybuddyUserDetails max = new PaymybuddyUserDetails();
		max.setEmail("max@max.max");
		max.setUsername("max");
		max.setName("max");
		max.setEnabled(true);
		max.setBalance(20f);
		max.setUserRole(UserRole.USER);
		
		PaymybuddyUserDetails nax = new PaymybuddyUserDetails();
		nax.setEmail("nax@nax.nax");
		nax.setUsername("nax");
		nax.setEnabled(true);
		nax.setBalance(20f);
		nax.setUserRole(UserRole.USER);

		List<PaymybuddyUserDetails> foundConnectionList = new ArrayList<>();
		foundConnectionList.add(nax);
		max.setMyconnection(foundConnectionList.stream().collect(Collectors.toSet()));
		
		EbankAccount bankAccount = new EbankAccount();
		bankAccount.setIban("man");
		bankAccount.setUser(max);
		List<EbankAccount> foundAccountList = new ArrayList<>();
		foundAccountList.add(bankAccount);
		max.setMybankAccount(foundAccountList.stream().collect(Collectors.toSet()));
		
		HashMap<String,Object> attributes = new HashMap<>();
		attributes.put("email", "max@max.max");
		attributes.put("name", "max");
		attributes.put("username", "max");
		max.setAttributes(attributes);
		
		PaymybuddyUserDetails result = (PaymybuddyUserDetails) max.clone();
		
		assertThat(result).isEqualTo(max);
		
	}
	
}
