package com.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.EbankAccount;

public interface BankAccountRepository extends JpaRepository<EbankAccount, Integer> {

}
