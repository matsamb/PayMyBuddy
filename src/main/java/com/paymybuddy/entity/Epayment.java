package com.paymybuddy.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

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
@NoArgsConstructor
public class Epayment implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "payer_email")
	private String payerEmail;

	@Column(name = "payee_email")
	private String payeeEmail;

	private String description;

	private Float amount;

	private Float fee;

	private Timestamp paymentDate;

	public Epayment(String payerEmail) {
		this.payerEmail = payerEmail;
	}

	public Epayment(String payeeEmail, String description, Float amount) {
		this.payeeEmail = payeeEmail;
		this.description = description;
		this.amount = amount;
	}

	public Timestamp getPaymentDate() {
		if (paymentDate != null) {
			return (Timestamp) paymentDate.clone();
		} else {
			return null;
		}
	}

	public void setPaymentDate(Timestamp paymentDate) {
		this.paymentDate = (Timestamp) paymentDate.clone();
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, description, fee, id, payeeEmail, payerEmail);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Epayment other = (Epayment) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(description, other.description)
				&& Objects.equals(fee, other.fee) && Objects.equals(id, other.id)
				&& Objects.equals(payeeEmail, other.payeeEmail) && Objects.equals(payerEmail, other.payerEmail);
	}

	public Object clone() {
		Epayment copy = null;

		try {
			copy = (Epayment) super.clone();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		copy.paymentDate = (Timestamp) paymentDate.clone();
		return copy;
	}

}
