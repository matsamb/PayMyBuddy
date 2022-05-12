package com.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.Epayment;

public interface PaymentRepository extends JpaRepository<Epayment, Integer> {

}
