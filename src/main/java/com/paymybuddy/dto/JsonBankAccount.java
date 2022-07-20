package com.paymybuddy.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class JsonBankAccount implements Cloneable {

	private String iban;
	
	private String userEmail;
	
	public Object clone() {
		JsonBankAccount copy = null;
		
		try {
			
			copy = (JsonBankAccount )super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return copy;
	}
	
}
