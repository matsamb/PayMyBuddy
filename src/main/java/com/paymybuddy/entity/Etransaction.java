package com.paymybuddy.entity;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Etransaction implements Cloneable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer transactionId;
	
	Float amount;
	
	String description;
	
	Float fee;
	
	@ManyToOne
	EbankAccount bankAccount;

	@Column(name="from_bank")
	Boolean fromBank;
	
	@Column(name="transaction_date")
	Timestamp date;

	public Etransaction(EbankAccount account) {
		this.bankAccount = (EbankAccount)account.clone();
	}

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
