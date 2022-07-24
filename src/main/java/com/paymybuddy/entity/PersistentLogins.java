package com.paymybuddy.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import lombok.Data;

@Entity
@Data
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
	
}
