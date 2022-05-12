package com.paymybuddy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class EbankAccount {

	@Id
	Integer iban;
	
	Float balance;

	@Column(name="fk_utilisateur_email")
	String ownerEmail;
	
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
