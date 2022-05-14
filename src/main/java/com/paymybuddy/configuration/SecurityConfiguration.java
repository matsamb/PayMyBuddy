package com.paymybuddy.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity
		.authorizeRequests()
		.antMatchers("/home").permitAll()
		.antMatchers("/resources/**","/**","/*").permitAll()
		.antMatchers("/admin").hasRole("ADMIN")
		.antMatchers("/user").hasRole("USER")
		.anyRequest().authenticated()
		.and()
		.sessionManagement()
		.invalidSessionUrl("/")
		.and()
		.formLogin()/*form ->form
				.loginPage("/login")
				.permitAll());*/
		.loginPage("/login")
		//.permitAll()
		.loginProcessingUrl("/perform_login")
		.defaultSuccessUrl("/**")
		.and()
		.rememberMe()
		.and()
		.logout()
		.permitAll()
		//.logoutSuccessUrl("/login")
		.deleteCookies("JSESSIONID")
		
		;
		//.permitAll();
		//.logout();*/
		
	}
	
	public JdbcUserDetailsManager jdbcUserDetailsManager(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{

		JdbcUserDetailsManager jdbcUserDetailsManager = authenticationManagerBuilder.jdbcAuthentication()
			.passwordEncoder(passwordEncoder())
			.dataSource(dataSource)
			.usersByUsernameQuery("select email, password, true from users where email = ?")
			.authoritiesByUsernameQuery("select fk_email, authority from authorities where fk_email = ? ")
			.getUserDetailsService();
		
		jdbcUserDetailsManager.setUserExistsSql("select email from users where email = ?");
		jdbcUserDetailsManager.setCreateUserSql("insert ignore into users (email, password, enabled) values (?,?,?)");
		jdbcUserDetailsManager.setUpdateUserSql("update users set password = ?, enabled = ? where email = ?");
		jdbcUserDetailsManager.setDeleteUserSql("delete from users where email = ?");
		jdbcUserDetailsManager.setChangePasswordSql("update users set password = ? where email = ?");
		
		jdbcUserDetailsManager.setCreateAuthoritySql("insert ignore into authorities (fk_email, authority) values (?, ?))");
		jdbcUserDetailsManager.setDeleteUserAuthoritiesSql("delete from authorities where email = ?");
		
		return jdbcUserDetailsManager;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
