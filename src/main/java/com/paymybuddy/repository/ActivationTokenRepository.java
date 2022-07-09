package com.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.ActivationToken;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken,Integer> {

	ActivationToken findByToken(String token);
	
	
}
