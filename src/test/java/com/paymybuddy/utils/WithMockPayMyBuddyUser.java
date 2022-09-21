package com.paymybuddy.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

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

}
