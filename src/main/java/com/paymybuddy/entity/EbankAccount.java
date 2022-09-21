package com.paymybuddy.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
public class EbankAccount implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Pattern(regexp="[A-Z]{2}\\w{2} ?\\w{5} ?\\w{4} ?\\w{4} ?\\w{4} ?\\w{4} ?[\\w]{0,2}",//real with digit "/[A-Z]{2}\\d{2} ?\\d{4} ?\\d{4} ?\\d{4} ?\\d{4} ?[\\d]{0,2}"
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
		if(user!=null) {
		return (PaymybuddyUserDetails) this.user.clone();
		}else {
			return null;
		}
	}

	public void setUser(PaymybuddyUserDetails user) {
		this.user = (PaymybuddyUserDetails) user.clone();
	}

	@Override
	public int hashCode() {
		return Objects.hash(iban);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EbankAccount other = (EbankAccount) obj;
		return Objects.equals(iban, other.iban);
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
