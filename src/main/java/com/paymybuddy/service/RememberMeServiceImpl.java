/*package com.paymybuddy.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.paymybuddy.service.users.PaymybuddyUserDetailsService;

//@Service
public class RememberMeServiceImpl implements IrememberMeService {
	
	private static final Logger LOGGER = LogManager.getLogger("RememberMeServiceImpl");
	
	@Autowired
	PaymybuddyUserDetailsService paymybuddyUserDetails;
	
	@Override
	public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info(request.getCookies());

		request.getCookies();
		
		LOGGER.info("autoLogin request" +request);
		LOGGER.info("autoLogin response" +response);
		return null;
	}

	@Override
	public void loginFail(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info(request.getCookies());
		LOGGER.info("loginFail request" +request);
		LOGGER.info("loginFail response" +response);

	}

	@Override
	public void loginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {
		
		LOGGER.info(request.getCookies());
		paymybuddyUserDetails.loadUserByUsername(successfulAuthentication.getName());
		
		LOGGER.info("loginSuccess request" +request);
		LOGGER.info("loginSuccess response" +response);
		LOGGER.info("loginSuccess successfulAuthentication "+successfulAuthentication);
		
	}

}*/
