package com.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String> {

	
}
