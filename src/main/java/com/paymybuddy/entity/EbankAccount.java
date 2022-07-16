package com.paymybuddy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

import lombok.Data;

@Entity
//@Component
@Data
public class EbankAccount {

	@Pattern(regexp="[A-Z]{2}\\w{2} ?\\w{5} ?\\w{4} ?\\w{4} ?\\w{4} ?\\w{4} ?[\\w]{0,2}",//real with digit "/[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}/gm"
	        message="{invalid.iban}")  
	@Id
	String iban;

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
