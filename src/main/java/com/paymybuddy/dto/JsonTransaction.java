package com.paymybuddy.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class JsonTransaction implements Cloneable {

	private Integer transactionId;

	private String description;

	private Float amount;

	private JsonBankAccount bankAccount;

	public JsonBankAccount getBankAccount() {
		if (bankAccount != null) {
			return (JsonBankAccount) this.bankAccount.clone();
		} else {
			return null;
		}

	}

	public void setBankAccount(JsonBankAccount bankAccount) {

		this.bankAccount = (JsonBankAccount) bankAccount.clone();

	}

	public Object clone() {
		JsonTransaction copy = null;

		try {

			copy = (JsonTransaction) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		copy.bankAccount = (JsonBankAccount) bankAccount.clone();

		return copy;
	}

}
