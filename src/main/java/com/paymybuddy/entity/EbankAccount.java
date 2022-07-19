package com.paymybuddy.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@Component
@Data
@NoArgsConstructor
public class EbankAccount implements Cloneable {

	@Pattern(regexp="[A-Z]{2}\\w{2} ?\\w{5} ?\\w{4} ?\\w{4} ?\\w{4} ?\\w{4} ?[\\w]{0,2}",//real with digit "/[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}/gm"
	        message="{invalid.iban}")  
	@Id
	String iban;

	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	@ManyToOne
	PaymybuddyUserDetails user;
	
	public EbankAccount(String iban) {
		this.iban = iban;
	}
	
	public PaymybuddyUserDetails getUser() {
		//return (PaymybuddyUserDetails) user.clone();
		return this.user;
	}

	public void setUser(PaymybuddyUserDetails user) {
		this.user = (PaymybuddyUserDetails) user.clone();
	}
	
	public Object clone() {
		EbankAccount copy = null;
		
		try {
			copy = (EbankAccount) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		
		if(Objects.nonNull(user)) {
			copy.user=(PaymybuddyUserDetails)user.clone();
		}
		
		return copy;
	}
	
}
