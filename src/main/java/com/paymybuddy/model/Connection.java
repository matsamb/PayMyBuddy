package com.paymybuddy.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Component
@Data
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	Utilisateur payer;
	
	@Getter(value=AccessLevel.NONE)
	@Setter(value=AccessLevel.NONE)
	Utilisateur payee;
	
	
	
	
	
}
