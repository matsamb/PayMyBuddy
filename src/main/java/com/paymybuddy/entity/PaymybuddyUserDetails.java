package com.paymybuddy.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.paymybuddy.service.users.UserRole;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class PaymybuddyUserDetails implements UserDetails, OAuth2User, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String email;

	private String username;

	private String name;

	private String password;

	private Float balance;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "myconnection", joinColumns = {
			@JoinColumn(name = "payer_email", referencedColumnName = "email") }
	)
	private Set<PaymybuddyUserDetails> myconnection;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private Set<EbankAccount> mybankAccount;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	private Boolean enabled;

	private Boolean locked;

	private HashMap<String, Object> attributes;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Collections.singletonList(new SimpleGrantedAuthority("USER"));

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
		if(myconnection != null) {
		return Set.copyOf(myconnection);
		}else {
			return null;
		}
	}

	public void setMyconnection(Set<PaymybuddyUserDetails> myconnection) {
		Set<PaymybuddyUserDetails> t = new HashSet<>();
		t.addAll(Set.copyOf(myconnection));
		this.myconnection = t;
	}

	public Set<EbankAccount> getMybankAccount() {

		if(mybankAccount != null) {
		return Set.copyOf(mybankAccount);
		}else {
			return null;
		}
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
		if (this.attributes == null) {
			this.attributes = new HashMap<>();
			this.attributes.put("email", attributes.get("email"));// = attributes;
			this.attributes.put("name", attributes.get("name"));
			this.attributes.put("username", attributes.get("username"));
		}
	}

	@Override
	public String toString() {
		return "PaymybuddyUserDetails [email=" + email + ", name=" + name + ", username=" + username + "]";
	}

	public Object clone() {
		PaymybuddyUserDetails copy = null;

		try {
			copy = (PaymybuddyUserDetails) super.clone();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		if (attributes != null) {
			copy.attributes.put("email", attributes.get("email"));
			copy.attributes.put("name", attributes.get("name"));
			copy.attributes.put("username", attributes.get("username"));

		}

		if (mybankAccount != null) {
			copy.mybankAccount = Set.copyOf(mybankAccount);
		}
		
		if (myconnection != null) {
			copy.myconnection = Set.copyOf(myconnection);
		}

		return copy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, name, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymybuddyUserDetails other = (PaymybuddyUserDetails) obj;
		return Objects.equals(email, other.email) && Objects.equals(name, other.name)
				&& Objects.equals(username, other.username);
	}

}
