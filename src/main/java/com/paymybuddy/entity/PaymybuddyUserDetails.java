package com.paymybuddy.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import org.thymeleaf.expression.Maps;

import com.paymybuddy.service.users.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
//@Data
@AllArgsConstructor
public class PaymybuddyUserDetails implements UserDetails, OAuth2User, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4692176737626072920L;	
	
	//@Pattern(regexp="[\\w-\\\\.]+@([\\\\w-]+\\\\.)+[\\\\w-]{2,4}",
	//        message="{invalid.email}")          
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
/*		if(userRole.name()!=null) {
			return Collections.singletonList( new SimpleGrantedAuthority(userRole.name()));
		}else {*/
			return Collections.singletonList( new SimpleGrantedAuthority("USER"));
		//}
		
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
			this.attributes.put("username", this.getUsername());

		}
		return Map.copyOf(attributes);
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
/*		if(myconnection.isEmpty()==false) {
		Set<PaymybuddyUserDetails> t = new HashSet<>();
		t.addAll(Set.copyOf(myconnection));		
		return t;
		}else {*/
			return myconnection;
		//}
	}

	public void setMyconnection(Set<PaymybuddyUserDetails> myconnection) {
		Set<PaymybuddyUserDetails> t = new HashSet<>();
		t.addAll(Set.copyOf(myconnection));		
		this.myconnection = t;
	}

	public Set<EbankAccount> getMybankAccount() {
/*		if(mybankAccount.isEmpty()==false) {
			Set<EbankAccount> t = new HashSet<>();
			t.addAll(Set.copyOf(mybankAccount));	
			return t;
		}else {*/
			return mybankAccount;
			//return (HashSet<EbankAccount>)Set.copyOf(mybankAccount);
		//}
		
	}

	public void setMybankAccount(Set<EbankAccount> mybankAccount) {
		Set<EbankAccount> t = new HashSet<>();
		t.addAll(Set.copyOf(mybankAccount));
		this.mybankAccount = t;
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
		this.attributes = /*(HashMap<String, Object>) *//*Map.copyOf(*/attributes/*)*/;
	}

	@Override
	public String toString() {
		return "PaymybuddyUserDetails [email=" + email + ", name=" + name + ", username=" + username + "]";
	}

	public Object clone() {
		PaymybuddyUserDetails copy = null;
		
		try {
			copy = (PaymybuddyUserDetails) super.clone();
			
		}catch(CloneNotSupportedException e) {
			e.printStackTrace();
		}		
		
		if(attributes!=null) {
			copy.attributes.put("email", attributes.get("email"));
			copy.attributes.put("name", attributes.get("name"));
			copy.attributes.put("username", attributes.get("username"));

		}

			copy.mybankAccount=mybankAccount;

			copy.myconnection=myconnection;
		
		return copy;
	}

}
