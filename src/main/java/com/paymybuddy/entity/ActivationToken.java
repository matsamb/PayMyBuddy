package com.paymybuddy.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ActivationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "user_email")
	private PaymybuddyUserDetails user;

	@Column(nullable = false)
	String token;
	@Column(nullable = false)
	private Timestamp startTime;
	private Timestamp expirationTime;
	
	public ActivationToken(String token, PaymybuddyUserDetails user, Timestamp startTime) {
		super();
		
		this.token = token;
		this.user = user;
		this.startTime = startTime;
	}
	
	

}