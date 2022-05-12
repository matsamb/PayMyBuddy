package com.paymybuddy.model;

import java.util.Calendar;

import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Component
@Data
public class Transaction {

	@Id
	Integer id;
	
	Calendar date;
	
	Float amount;
	
	Boolean fromBank;
	
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	BankAccount account;
	
	public Transaction(Integer id, Calendar date, Float amount, Boolean fromBank, BankAccount account) {
		super();
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.fromBank = fromBank;
		this.account = (BankAccount)account.clone();
	}
	
	public BankAccount getAccount() {
		return (BankAccount)account.clone();
	}

	public void setAccount(BankAccount account) {
		this.account = (BankAccount)account.clone();
	}

	public Object clone() {
		Transaction copy = null;
		
		try {
			
			copy = (Transaction)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		copy.setAccount((BankAccount)account.clone());
		
		return copy;
	}
	
}
