package com.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.PaymybuddyUserDetails;

public interface PaymybuddyUserDetailsRepository extends JpaRepository<PaymybuddyUserDetails, String>{

	//PaymybuddyUserDetails findByEmail(String email);
	Optional<PaymybuddyUserDetails> findByEmail(String email);
	
}
