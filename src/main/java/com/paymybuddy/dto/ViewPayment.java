package com.paymybuddy.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ViewPayment implements Cloneable {

	private String connection;
	
	private String description;
	
	private Float amount;
	
	public Object clone() {
		ViewPayment copy = null;
		
		try {
			copy = (ViewPayment)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		
		return copy;
		
	}
	
}
