package com.paymybuddy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.Epayment;

public interface PaymentRepository extends JpaRepository<Epayment, Integer> {

	Optional<List<Epayment>> findByPayeeEmail(String payerEmail);

	Optional<List<Epayment>> findByPayerEmail(String payerEmail);

}
