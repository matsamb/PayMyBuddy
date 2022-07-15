package com.paymybuddy.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.paymybuddy.service.users.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
//@Data
@AllArgsConstructor
public class PaymybuddyUserDetails implements UserDetails, OAuth2User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4692176737626072920L;	
	
	@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
	        +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
	        +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
	        message="{invalid.email}")          
	//@ValidatedEmail
	@Id
	private String email;
	
	private String username;
	
	private String name;
	
	private String password;

	private Float balance;
	
	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "myconnection"
			,joinColumns = {@JoinColumn(name ="payer_email",referencedColumnName = "email")}
			//,inverseJoinColumns = {@JoinColumn(name ="payee_email",referencedColumnName = "email")}
			)
	private Set<PaymybuddyUserDetails> myconnection;
	
	@OneToMany (fetch = FetchType.EAGER, mappedBy = "user")
	private Set<EbankAccount> mybankAccount;
	
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	private Boolean enabled;
	
	private Boolean locked;

	private HashMap<String, Object> attributes;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList( new SimpleGrantedAuthority(userRole.name()));
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

	
	
	@Override
	public boolean equals(Object obj) {
		if (this.hashCode() == obj.hashCode())
			return true;
	/*	if (obj== null)
			return false;*/
		if (getClass() != obj.getClass())
			return false;
		PaymybuddyUserDetails other = (PaymybuddyUserDetails) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public Set<PaymybuddyUserDetails> getMyconnection() {
		return myconnection;
	}

	public void setMyconnection(Set<PaymybuddyUserDetails> myconnection) {
		this.myconnection = myconnection;
	}

	public Set<EbankAccount> getMybankAccount() {
		return mybankAccount;
	}

	public void setMybankAccount(Set<EbankAccount> mybankAccount) {
		this.mybankAccount = mybankAccount;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "PaymybuddyUserDetails [email=" + email + ", name=" + name + ", username=" + username + "]";
	}


}
