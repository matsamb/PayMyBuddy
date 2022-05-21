package com.paymybuddy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.Eusers;

public interface EusersRepository extends JpaRepository<Eusers, String> {

	public Eusers findEusersByUsername(String username);
	
	public Page<Eusers> findEusersByUsername(String username, Pageable pageable);
	
}
