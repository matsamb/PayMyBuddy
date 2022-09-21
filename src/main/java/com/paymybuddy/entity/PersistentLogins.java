package com.paymybuddy.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PersistentLogins {

	@Id
	@NotNull
    private  String series;
	
	@NotNull
    private String username; 
	
	@NotNull
	private String token; 
    
	@NotNull
	private Timestamp lastUsed;

	public Timestamp getLastUsed() {
		if(lastUsed != null) {
		return (Timestamp) lastUsed.clone();
		}else {
			return null;
		}
	}

	public void setLastUsed(Timestamp lastUsed) {
		this.lastUsed = (Timestamp) lastUsed.clone();
	} 

}
