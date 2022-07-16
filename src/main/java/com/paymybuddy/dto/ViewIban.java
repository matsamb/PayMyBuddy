package com.paymybuddy.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ViewIban implements Cloneable {

	String country;

	String controlkey;
	String bankcode;
	String branch;
	String accountnumberA;
	String accountnumberB;
	String accountnumberC;
	String accountkey;

	public Object clone() {
		ViewIban copy = null;

		try {
			copy = (ViewIban) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}
}
