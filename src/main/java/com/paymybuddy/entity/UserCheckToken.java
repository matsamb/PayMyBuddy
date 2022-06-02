package com.paymybuddy.entity;

import java.util.Calendar;

import javax.persistence.Id;

public class UserCheckToken {

	private final long init = System.currentTimeMillis();
	
	@Id
	private Integer tokenId; 
	
	private Users user;
	
	private Calendar endDate; 

	public void calculateEndDate() {
		long length = 24*60*60*1000;
		this.endDate.setTimeInMillis(init+length);
	}
}
