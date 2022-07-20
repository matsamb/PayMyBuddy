package com.paymybuddy.dto;

import org.springframework.stereotype.Component;

import com.paymybuddy.entity.EbankAccount;

import lombok.Data;

@Component
@Data
public class JsonTransaction implements Cloneable {

	private Integer transactionId;
	
	private String description;
	
	private Float amount;
	
	private JsonBankAccount bankAccount;

	public Object clone() {
		JsonTransaction copy = null;
		
		try {
			
			copy = (JsonTransaction)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		
		copy.bankAccount = (JsonBankAccount)bankAccount.clone();
		
		return copy;
	}
	
}
