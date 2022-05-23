package com.paymybuddy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Econnection implements Cloneable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	

	@Column(name="fk_payer_username")
	public String fkPayerUserName;
	

	@Column(name="fk_payee_username")
	public String fkPayeeUserName;
	
	public Object clone() {
		Econnection copy = null;
		
		try {
			copy = (Econnection) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		return copy;
	}
}
