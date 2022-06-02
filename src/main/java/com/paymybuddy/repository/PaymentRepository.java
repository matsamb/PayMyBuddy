package com.paymybuddy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.Epayment;

public interface PaymentRepository extends JpaRepository<Epayment, Integer> {

	List<Epayment>  findByIdConnection(int id);
	
}
