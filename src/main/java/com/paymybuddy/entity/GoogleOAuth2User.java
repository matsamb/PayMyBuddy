/*package com.paymybuddy.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoogleOAuth2User implements OAuth2User {
	
	@Id
	private String email;
	private String name;
	private ArrayList<GrantedAuthority> authorities =
			(ArrayList<GrantedAuthority>) AuthorityUtils.createAuthorityList("ROLE_USER");
	private HashMap<String, Object> attributes;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public HashMap<String, Object> getAttributes() {
		if (this.attributes == null) {
			this.attributes = new HashMap<>();
			this.attributes.put("email", this.getEmail());
			this.attributes.put("name", this.getName());

		}
		return attributes;
	}

/*	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}*//*

	@Override
	public String getName() {
		return this.name;
	}

/*	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}*//*
}
*/