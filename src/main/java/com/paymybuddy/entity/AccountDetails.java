package com.paymybuddy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AccountDetails implements Cloneable {

	@Id
	String username;
	
	String firstname;
	
	String lastname;
	
	int Balance;
	
	public Object clone() {
		AccountDetails copy = null;
		
		try {
			copy = (AccountDetails)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}
	
}
