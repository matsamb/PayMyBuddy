package com.paymybuddy.configuration;

import java.security.spec.EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.paymybuddy.service.PaymybuddyUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	PaymybuddyUserDetailsService paymybuddyUserDetailsService;
	
	JdbcDaoImpl jdbcDaoImpl;
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity
		.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/admin").hasAnyRole("ADMIN")
		.antMatchers("/user").hasAnyRole("ADMIN","USER")
		.antMatchers("/login").permitAll()
		//.antMatchers("/home").hasRole("ADMIN")
		//.antMatchers("/**").permitAll()
		//.antMatchers("/home").hasRole("USER")
		//.antMatchers("/user").hasRole("USER")
		//.antMatchers("/resources/**").permitAll()
		//.antMatchers("/login").permitAll()
		.anyRequest().authenticated()
		.and()
	//	.sessionManagement()
	//	.invalidSessionUrl("/login")
	//	.and()
		.formLogin()
		.loginPage("/login")
		//.loginProcessingUrl("/perform_login")
		.defaultSuccessUrl("/home")
		.failureUrl("/login?error=true")
		.and()
		.rememberMe()
		.and()  

		.logout()
		.logoutSuccessUrl("/login?logout=true")
		.invalidateHttpSession(false)
		.permitAll()

		;		
	}
	
/*	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
	.withUser("user").password(passwordEncoder().encode("u123")).roles("USER")
		.and()
	.withUser("admin").password(passwordEncoder().encode("a123")).roles("ADMIN","USER");
	}
	*************************
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(paymybuddyUserDetailsService)	
	;
	}*/
/************************************/	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder())
			.usersByUsernameQuery("select username, password, true from users where username = ?")
			.authoritiesByUsernameQuery("select fk_username, authority from authorities where fk_username = ? ")
			;
	}/********************************/
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		String curEncode = "Bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<String, PasswordEncoder>();
		
		encoders.put(curEncode, new BCryptPasswordEncoder());
		encoders.put("noop",  NoOpPasswordEncoder.getInstance());
		
		//return new DelegatingPasswordEncoder(curEncode, encoders);
		return encoders.get(curEncode);
		
	}
	
}
