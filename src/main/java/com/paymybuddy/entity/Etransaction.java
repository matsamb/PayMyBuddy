package com.paymybuddy.entity;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Etransaction implements Cloneable{

	@Id
	Integer id;
	
	@ManyToOne
	EbankAccount bankAccount;
	
	@Column(name="transaction_date")
	Timestamp date;
	
	Float amount;
	
	@Column(name="from_bank")
	Boolean fromBank;
	
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
