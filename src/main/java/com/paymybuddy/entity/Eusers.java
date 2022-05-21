package com.paymybuddy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Eusers implements Cloneable{

	@Id
	String username;
	
	String password;
	
	Integer balance;
	
	public Object clone() {
		Eusers copy = null;
		
		try {
			copy = (Eusers) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		return copy;
	}
}
