package com.paymybuddy.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.paymybuddy.service.CustomAuthenticationProvider;
import com.paymybuddy.service.users.PaymybuddyUserDetailsService;
 
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	PaymybuddyUserDetailsService paymybuddyUserDetailsService;

	JdbcDaoImpl jdbcDaoImpl;

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/admin").hasAnyRole("ADMIN").antMatchers("/user")
				.hasAnyRole("ADMIN", "USER")
				.antMatchers("/transactions","/tokenexpired", "/accountactivation", "/signinconfirm","*/icon_google", "/signin", "/login").permitAll()
				.anyRequest().authenticated().and().sessionManagement()
				.and() 
				.formLogin()
					.loginPage("/login") 
					.defaultSuccessUrl("/home?size=3&page=1")
					.failureUrl("/login?error=true")
				.and()
				.oauth2Login()
					.loginPage("/login")
					.defaultSuccessUrl("/oauth2")
					.failureUrl("/login?error=true")
				.and() 
				.logout()//.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true")
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(false).permitAll()
				.and()
				.rememberMe().tokenRepository(persistentTokenRepository())//.tokenValiditySeconds(60)

		;
		
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		jdbcTokenRepositoryImpl.setDataSource(dataSource);
		return jdbcTokenRepositoryImpl;
	}


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(authProvider());
		
		auth.userDetailsService(paymybuddyUserDetailsService).passwordEncoder(passwordEncoder());
		
		auth.jdbcAuthentication().dataSource(dataSource)
		    	.passwordEncoder(passwordEncoder()) 
		        .usersByUsernameQuery("select username, password, true from users where username = ?")
		        .authoritiesByUsernameQuery("select fk_username, authority from authorities where fk_username = ? ") ;
		
		/*auth.inMemoryAuthentication()
		  	.withUser("user").password(passwordEncoder().encode("u123")).roles("USER")
		  	.and()
		  	.withUser("admin").password(passwordEncoder().encode("a123")).roles("ADMIN","USER"); 
		 */
	
	}

	@Bean
	public CustomAuthenticationProvider authProvider() {
		return new CustomAuthenticationProvider();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		String curEncode = "Bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<String, PasswordEncoder>();

		encoders.put(curEncode, new BCryptPasswordEncoder());

		return encoders.get(curEncode);

	}

}
