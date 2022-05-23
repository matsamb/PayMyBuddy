package com.paymybuddy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.paymybuddy.entity.Econnection;

public interface EconnectionRepository extends JpaRepository<Econnection, Integer> {

	Econnection findByFkPayeeUserName(String payeeUserName);
	
	//@Query(value="SELECT * FROM econnection WHERE fk_payer_username = :payer AND fk_payee_username = :payee", nativeQuery=true)
	Econnection findByFkPayerUserNameAndFkPayeeUserName(@Param(value="payer") String payerUserName, @Param(value="payee") String payeeUserName);
	
	Page<Econnection> findByFkPayerUserName(String payerUserName, Pageable pageable);
	
}
