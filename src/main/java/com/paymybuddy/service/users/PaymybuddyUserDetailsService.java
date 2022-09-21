package com.paymybuddy.service.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;

@Service
public class PaymybuddyUserDetailsService implements UserDetailsService {

	@Autowired
	PaymybuddyUserDetailsRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		PaymybuddyUserDetails loadedUser = usersRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
		if (loadedUser.getEnabled() == true) {
			UserDetails currentUser = User.withUsername(loadedUser.getEmail()).password(loadedUser.getPassword())
					.authorities(new SimpleGrantedAuthority("ROLE_USER")).build();

			return currentUser;
		}
		return new PaymybuddyUserDetails("N_A");
	}

}
