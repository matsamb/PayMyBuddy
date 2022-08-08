package com.paymybuddy.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

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
public class Etransaction implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;

	private Integer bankTransactionId;

	private Float amount;

	private String description;

	private Float fee;

	@ManyToOne
	private EbankAccount bankAccount;

	@Column(name = "from_bank")
	private Boolean fromBank;

	@Column(name = "transaction_date")
	private Timestamp date;

	public Etransaction(EbankAccount account) {
		this.bankAccount = (EbankAccount) account.clone();
	}

	public Etransaction(Integer bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}

	public EbankAccount getBankAccount() {
		return (EbankAccount) bankAccount.clone();
	}

	public void setBankAccount(EbankAccount bankAccount) {
		this.bankAccount = (EbankAccount) bankAccount.clone();
	}

	public Timestamp getDate() {
		if (date != null) {
			return (Timestamp) date.clone();
		} else {
			return null;
		}
	}

	public void setDate(Timestamp date) {
		this.date = (Timestamp) date.clone();
	}

	public Object clone() {
		Etransaction copy = null;

		try {

			copy = (Etransaction) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		copy.date = (Timestamp) date.clone();
		copy.bankAccount = (EbankAccount) bankAccount.clone();

		return copy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, bankAccount, bankTransactionId, description, fee, fromBank, transactionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Etransaction other = (Etransaction) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(bankAccount, other.bankAccount)
				&& Objects.equals(bankTransactionId, other.bankTransactionId)
				&& Objects.equals(description, other.description) && Objects.equals(fee, other.fee)
				&& Objects.equals(fromBank, other.fromBank) && Objects.equals(transactionId, other.transactionId);
	}

}
