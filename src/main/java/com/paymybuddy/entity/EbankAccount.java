package com.paymybuddy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class EbankAccount {

	@Id
	String iban;
	
	/*Float balance;*/

	//@Column(name="fk_username")
	@ManyToOne
	PaymybuddyUserDetails user;
	
	public Object clone() {
		EbankAccount copy = null;
		
		try {
			copy = (EbankAccount) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		return copy;
	}
	
}
