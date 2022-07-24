package com.paymybuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.PersistentLogins;

public interface PersistentLoginsRepository extends JpaRepository<PersistentLogins, String> {

}
