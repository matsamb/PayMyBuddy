package com.paymybuddy.service.connection;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.paymybuddy.dao.ConnectionDAO;
import com.paymybuddy.repository.EconnectionRepository;

@Service
public class AddConnectionService {

	final static Logger addConnectionServiceLogger = LogManager.getLogger("AddConnectionService");
	
	@Autowired
	EconnectionRepository connectionRepositoryAtAddConnection;
	
	@Autowired
	ConnectionDAO connectionDAOAtAddConnectionService;
	
	
	
	AddConnectionService(EconnectionRepository connectionRepositoryAtAddConnection
						,ConnectionDAO connectionDAOAtAddConnectionService){
		this.connectionRepositoryAtAddConnection = connectionRepositoryAtAddConnection;
		this.connectionDAOAtAddConnectionService = connectionDAOAtAddConnectionService;
	}
	
	public void addConnection(int id, String payerUserName, String payeeUserName) {
		
		if(Objects.isNull(connectionRepositoryAtAddConnection.findByFkPayerUserNameAndFkPayeeUserName(payerUserName, payeeUserName))) {
			connectionDAOAtAddConnectionService.saveConnection(id, payerUserName, payeeUserName);
			addConnectionServiceLogger.info("Connection between "+payerUserName+" and "+payeeUserName+" created");
		}else {
			addConnectionServiceLogger.info("Connection between "+payerUserName+" and "+payeeUserName+" all ready exists");
		}
	}
}
