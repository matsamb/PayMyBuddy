package com.paymybuddy.service.connection;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entity.Econnection;
import com.paymybuddy.repository.EconnectionRepository;

/*@Service
public class FindEconnectionByPayeeUsernameService {

	static final Logger findEconnectionByPayeeUsernameServiceLogger = LogManager.getLogger("FindEconnectionByPayeeUsernameService");

	@Autowired
	EconnectionRepository econnectionRepositoryAtFindEconnectionByPayeeUsernameService;
	
	FindEconnectionByPayeeUsernameService(EconnectionRepository econnectionRepositoryAtFindEconnectionByPayeeUsernameService
						){
		this.econnectionRepositoryAtFindEconnectionByPayeeUsernameService = econnectionRepositoryAtFindEconnectionByPayeeUsernameService;
	}

	public List<Econnection> findByFkPayeeUsername(String fkPayeeUsername) {
		if(Objects.isNull(econnectionRepositoryAtFindEconnectionByPayeeUsernameService.findByFkPayeeUserName(fkPayeeUsername))) {
			findEconnectionByPayeeUsernameServiceLogger.info("No connection for "+fkPayeeUsername);
			return econnectionRepositoryAtFindEconnectionByPayeeUsernameService.findByFkPayeeUserName(fkPayeeUsername);
		}else {
			findEconnectionByPayeeUsernameServiceLogger.info("Connections found for "+fkPayeeUsername);
			return econnectionRepositoryAtFindEconnectionByPayeeUsernameService.findByFkPayeeUserName(fkPayeeUsername);
		}
	}
	
}*/
