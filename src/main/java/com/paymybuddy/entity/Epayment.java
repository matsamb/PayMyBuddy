package com.paymybuddy.entity;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Epayment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="fk_econnection_id")
	Integer idConnection;
	
	String description;
	
	Float amount;
	
	Timestamp paymentDate;
	
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
