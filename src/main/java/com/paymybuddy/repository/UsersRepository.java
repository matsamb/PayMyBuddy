package com.paymybuddy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.paymybuddy.entity.Users;

public interface UsersRepository extends JpaRepository<Users, String> {

	public Users findEusersByUsername(String username);
	
	public Page<Users> findEusersByUsername(String username, Pageable pageable);
	
	public Page<Users> findAll(Pageable pageable);
	
}
