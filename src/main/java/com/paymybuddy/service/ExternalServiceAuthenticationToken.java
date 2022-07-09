package com.paymybuddy.service;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ExternalServiceAuthenticationToken {

	private String email;
	private String name;
	private boolean authenticated;
	
}
