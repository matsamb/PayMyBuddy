package com.paymybuddy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.transaction.annotation.Transactional;

import lombok.Data;

@Entity
@Data
public class Balance implements Cloneable {

	@Id
	@Column(name="fk_username")
	String username;
	
	String firstName;
	
	String lastName;
	
	Float balance;
	
	public Object clone() {
		Balance copy = null;
		
		try {
			copy = (Balance)super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}
	
}
