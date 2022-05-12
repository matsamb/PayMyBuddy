package com.paymybuddy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Eutilisateur implements Cloneable{

	@Id
	String email;
	
	String password;
	
	Integer balance;
	
	public Object clone() {
		Eutilisateur copy = null;
		
		try {
			copy = (Eutilisateur) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		return copy;
	}
}
