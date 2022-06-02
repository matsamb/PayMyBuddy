package com.paymybuddy.service.connection;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.Econnection;
import com.paymybuddy.repository.EconnectionRepository;

@Service
public class FindEconnectionByPayerUsernameService {

	final static Logger findEconnectionByPayerUsernameServiceLogger = LogManager.getLogger("FindEconnectionByPayerUsernameService");
	
	@Autowired
	EconnectionRepository econnectionRepositoryAtFindEconnectionByPayerUsernameService;
	
	FindEconnectionByPayerUsernameService(EconnectionRepository econnectionRepositoryAtFindEconnectionByPayerUsernameService){
		this.econnectionRepositoryAtFindEconnectionByPayerUsernameService = econnectionRepositoryAtFindEconnectionByPayerUsernameService;
	}

	public List<Econnection> findByFkPayerUsername(String fkPayerUsername) {
		if(Objects.isNull(econnectionRepositoryAtFindEconnectionByPayerUsernameService.findByFkPayerUserName(fkPayerUsername))) {
			findEconnectionByPayerUsernameServiceLogger.info("No connection for "+fkPayerUsername);
			return econnectionRepositoryAtFindEconnectionByPayerUsernameService.findByFkPayerUserName(fkPayerUsername);
		}else {
			findEconnectionByPayerUsernameServiceLogger.info("Connections found for "+fkPayerUsername);
			return econnectionRepositoryAtFindEconnectionByPayerUsernameService.findByFkPayerUserName(fkPayerUsername);
		}
	}
	
	
	
}
