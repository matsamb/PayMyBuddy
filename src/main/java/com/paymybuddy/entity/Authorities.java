package com.paymybuddy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Authorities implements Cloneable {

	@Id
	@Column(name="fk_username")
	String username;
	
	String authority;
	
	public Object clone() {
		Authorities copy = null;	
		try {
			copy = (Authorities) super.clone();
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}	
		return copy;		
	}
	
}
