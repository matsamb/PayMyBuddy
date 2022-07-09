package com.paymybuddy.entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.paymybuddy.model.BankAccount;
import com.paymybuddy.service.ValidatedEmail;
import com.paymybuddy.service.users.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class PaymybuddyUserDetails implements UserDetails, OAuth2User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4692176737626072920L;	
	
	/*@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
	        +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
	        +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
	        message="{invalid.email}")   */       
	@ValidatedEmail
	@Id
	private String email;
	
	private String username;
	
	private String name;
	
	private String password;

	private Float balance;
	
	@OneToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "fconnection"
			,joinColumns = {@JoinColumn(name ="payer_email",referencedColumnName = "email")
					,@JoinColumn(name ="payee_email",referencedColumnName = "email")}
			)
	private Set<PaymybuddyUserDetails> econnection;
	
	@OneToMany (fetch = FetchType.EAGER, mappedBy = "user")
	private Set<EbankAccount> ebankAccount;
	
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	private Boolean enabled;
	
	private Boolean locked;

	private HashMap<String, Object> attributes;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(userRole.name()));
	}
	


	public PaymybuddyUserDetails(String email) {
		this.email = email;
	}
	
	@Override
	public String getPassword() {
		return password;

	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	@Override
	public Map<String, Object> getAttributes() {
		if (this.attributes == null) {
			this.attributes = new HashMap<>();
			this.attributes.put("email", this.getEmail());
			this.attributes.put("name", this.getName());

		}
		return attributes;
	}


	@Override
	public String getName() {
		return this.name;
	}

}
