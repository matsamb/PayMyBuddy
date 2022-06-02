package com.paymybuddy.service.authority;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.Authorities;
import com.paymybuddy.repository.AuthoritiesRepository;

@Service
public class AuthoritiesSaveAuthorityService {

	public static Logger authoritiesSaveAuthorityServiceLogger = LogManager.getLogger("AuthoritiesSaveAuthorityService");
	
	@Autowired
	AuthoritiesRepository authoritiesRepositoryAtAuthoritiesSaveAuthorityService;

	AuthoritiesSaveAuthorityService(AuthoritiesRepository authoritiesRepositoryAtAuthoritiesSaveAuthorityService){
		this.authoritiesRepositoryAtAuthoritiesSaveAuthorityService = authoritiesRepositoryAtAuthoritiesSaveAuthorityService;
	}

	public void saveAuthorities(Authorities newAuthority) {

		authoritiesRepositoryAtAuthoritiesSaveAuthorityService.save(newAuthority);
		authoritiesSaveAuthorityServiceLogger.info(newAuthority.getAuthority()+" "+newAuthority.getUsername()+" registered");
		
	}

	
}
