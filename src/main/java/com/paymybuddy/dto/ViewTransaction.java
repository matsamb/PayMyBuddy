package com.paymybuddy.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ViewTransaction implements Cloneable {

	private String iban;
	
	private String description;
	
	private Float amount;
	
	public Object clone() {
		ViewTransaction copy = null;
		
		try {
			copy = (ViewTransaction)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
		
	}
	
}
