package com.paymybuddy.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Etransaction implements Cloneable{

	@Id
	Integer id;
	
	@Column(name="transaction_date")
	Calendar date;
	
	Float amount;
	
	@Column(name="from_bank")
	Boolean fromBank;
	
	Integer iban;
	
	public Object clone() {
		Etransaction copy = null;
		
		try {
			
			copy = (Etransaction)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return copy;
	}
	
}
