package com.paymybuddy.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ActivationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "user_email")
	//@Column(nullable = false)
	private PaymybuddyUserDetails user;

	@Column(nullable = false)
	private String token;
	@Column(nullable = false)
	private Timestamp startTime;
	private Timestamp expirationTime;
	
	public ActivationToken(String token, PaymybuddyUserDetails user, Timestamp startTime) {
		
		this.token = token;
		this.user = (PaymybuddyUserDetails) user.clone();
		this.startTime = (Timestamp) startTime.clone();
	}

	public PaymybuddyUserDetails getUser() {
		return (PaymybuddyUserDetails) user.clone();
	}

	public void setUser(PaymybuddyUserDetails user) {
		this.user = (PaymybuddyUserDetails) user.clone();
	}

	public Timestamp getStartTime() {
		return (Timestamp) startTime.clone();
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = (Timestamp) startTime.clone();
	}

	public Timestamp getExpirationTime() {
		return (Timestamp) expirationTime.clone();
	}

	public void setExpirationTime(Timestamp expirationTime) {
		this.expirationTime = (Timestamp) expirationTime.clone();
	}

	@Override
	public int hashCode() {
		return Objects.hash(token, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActivationToken other = (ActivationToken) obj;
		return Objects.equals(token, other.token) && Objects.equals(user, other.user);
	}

}
