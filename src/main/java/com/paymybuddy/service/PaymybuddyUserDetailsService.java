package com.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.repository.PaymybuddyUserDetailsRepository;

@Service
public class PaymybuddyUserDetailsService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	@Autowired
	PaymybuddyUserDetailsRepository usersRepository;

	// @Bean
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		PaymybuddyUserDetails loadedUser = usersRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
		if (loadedUser.getEnabled() == true) {
			UserDetails currentUser = User.withUsername(loadedUser.getEmail()).password(loadedUser.getPassword())
					.authorities(new SimpleGrantedAuthority("ROLE_USER")).build();

			return currentUser;
		}
		return new PaymybuddyUserDetails();
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
		String email = ((PaymybuddyUserDetails)currentAuth.getPrincipal()).getEmail();
		PaymybuddyUserDetails loadedUser = usersRepository
									.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
		if (loadedUser.getEnabled() == true) {
			OAuth2User currentUser = (OAuth2User) User.withUsername(loadedUser.getEmail()).password(loadedUser.getPassword())
					.authorities(new SimpleGrantedAuthority("ROLE_USER")).build();

			return currentUser;
		}
		return new PaymybuddyUserDetails();
	}

}
