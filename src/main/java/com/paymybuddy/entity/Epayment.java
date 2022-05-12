package com.paymybuddy.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Epayment {

	@Id
	Integer id;
	
	@Column(name="fk_connection_id")
	Integer idConnection;
	
	Float amount;
	
	Calendar paymentDate;
	
	public Object clone() {
		Epayment copy = null;
		
		try {
			copy = (Epayment) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}	
		copy.setPaymentDate((Calendar)paymentDate.clone());
		return copy;
	}
	
}
