package com.paymybuddy.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Set;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContext;

import com.paymybuddy.entity.EbankAccount;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.users.UserRole;


@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockPayMyBuddyUserSecurityContextFactory.class)
public @interface WithMockPayMyBuddyUser{
	
	String username() default "max";

	String name() default "max";
	
	String email() default "max";
	
	String password() default "password";

	float balance() default 34f; 
	
	UserRole userRole() default UserRole.USER;
	
	boolean enabled() default true;
	
	boolean locked() default false;
	
	//HashSet<PaymybuddyUserDetails> myconnection() default f;
	
	//HashSet<EbankAccount> mybankAccount() default d;
	
	//HashMap<String, Object> attributes() default a;

}
