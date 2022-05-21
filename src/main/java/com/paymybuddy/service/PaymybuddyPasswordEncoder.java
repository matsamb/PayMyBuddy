package com.paymybuddy.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PaymybuddyPasswordEncoder {

	public PasswordEncoder getPasswordEncoder() {
		
	
	String curEncode = "Bcrypt";
	Map<String, PasswordEncoder> encoders = new HashMap<String, PasswordEncoder>();
	
	encoders.put(curEncode, new BCryptPasswordEncoder());
	encoders.put("noop",  NoOpPasswordEncoder.getInstance());
	
	DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(curEncode, encoders);
	return delegatingPasswordEncoder;
	
	}
	
}
