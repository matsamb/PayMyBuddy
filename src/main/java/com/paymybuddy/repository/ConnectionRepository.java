package com.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.Econnection;

public interface ConnectionRepository extends JpaRepository<Econnection, Integer> {

}
