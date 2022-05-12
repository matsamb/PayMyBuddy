package com.paymybuddy.model;

import javax.persistence.Column;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Component
@Data
public class BankAccount implements Cloneable {

	Integer iban;
	
	Float balance;
	
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	Utilisateur owner;

	public BankAccount(Integer iban, Float balance, Utilisateur owner) {
		super();
		this.iban = iban;
		this.balance = balance;
		this.owner = (Utilisateur)owner.clone();
	}

	public Utilisateur getOwner() {
		return (Utilisateur)this.owner.clone();
	}

	public void setOwner(Utilisateur owner) {
		this.owner = (Utilisateur)owner.clone();
	}
	
	public Object clone() {
		BankAccount copy = null;
		
		try {
			
			copy = (BankAccount)super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		copy.setOwner((Utilisateur)this.owner.clone());
		
		return copy;
	}
	
	
}
