package com.paymybuddy.dto;

import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Cloneable{

	@Id
	String username;
	
	String password;
	
	Boolean enabled;
	
	public Object clone() {
		Users copy = null;
		
		try {
			copy = (Users) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		return copy;
	}
}
