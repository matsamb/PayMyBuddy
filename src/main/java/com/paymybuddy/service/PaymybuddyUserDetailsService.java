package com.paymybuddy.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.entity.Users;
import com.paymybuddy.repository.UsersRepository;

@Service
public class PaymybuddyUserDetailsService implements UserDetailsService {

	@Autowired
	UsersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users eUsers = usersRepository.findEusersByUsername(username);
		if(Objects.isNull(eUsers)) {
			throw new UsernameNotFoundException(username+" not found");
		}
	
		return new PaymybuddyUserDetails(eUsers);
	}

}
