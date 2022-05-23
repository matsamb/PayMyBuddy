package com.paymybuddy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.Econnection;

public interface ConnectionRepository extends JpaRepository<Econnection, Integer> {

	Econnection findByPayeeUserName(String payeeUserName);
	
	List<Econnection> findByPayerUserName(String payerUserName, Pageable pageable);
	
}
