package com.paymybuddy.model;

import org.springframework.stereotype.Component;

import lombok.Data;

//@Component
@Data
public class Utilisateur implements Cloneable {

	String email;
	
	String password;
	
	Integer balance;
	
	public Object clone() {
		Utilisateur copy = null;
		
		try {
			copy = (Utilisateur) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		return copy;
	}
	
}
