package com.paymybuddy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Econnection implements Cloneable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="fk_payer_email")
	String payerEmail;
	
	@Column(name="fk_payee_email")
	String payeeEmail;
	
	public Object clone() {
		Econnection copy = null;
		
		try {
			copy = (Econnection) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		return copy;
	}
}
