package com.paymybuddy.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContext;

import com.paymybuddy.entity.PaymybuddyUserDetails;


@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser{
	
	String username() default "max";

	String name() default "max";
	
	String email() default "max";
	

}
