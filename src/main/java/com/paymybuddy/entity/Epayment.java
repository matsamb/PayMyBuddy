package com.paymybuddy.entity;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Epayment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="payer_email")
	String payerEmail;
	
	@Column(name="payee_email")
	String payeeEmail;
	
	String description;
	
	Float amount;
	
	Timestamp paymentDate;
	
	public Epayment(String payerEmail) {
		this.payerEmail = payerEmail;
	}
	
	public Epayment(String payeeEmail, String description, Float amount) {
		this.payeeEmail = payeeEmail;
		this.description = description;
		this.amount = amount;
	}
	
	public Object clone() {
		Epayment copy = null;
		
		try {
			copy = (Epayment) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}	
		copy.setPaymentDate((Timestamp)paymentDate.clone());
		return copy;
	}

	
	
}
