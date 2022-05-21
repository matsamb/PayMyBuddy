package com.paymybuddy.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import com.paymybuddy.configuration.SecurityConfiguration;

@Service
public class JdbcDAOImpl {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	PaymybuddyPasswordEncoder paymybuddyPasswordEncoder;
	
	JdbcDAOImpl(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	public JdbcUserDetailsManager jdbcUserDetailsManager(AuthenticationManagerBuilder authenticationManagerBuilder, DataSource dataSource) throws Exception{

		JdbcUserDetailsManager jdbcUserDetailsManager = authenticationManagerBuilder.jdbcAuthentication()
			
			.passwordEncoder(paymybuddyPasswordEncoder.getPasswordEncoder())
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
				
		jdbcUserDetailsManager.getUsersByUsernameQuery();
		
		return jdbcUserDetailsManager;
	}

}
