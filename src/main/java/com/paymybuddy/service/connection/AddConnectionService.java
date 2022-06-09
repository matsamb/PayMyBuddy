package com.paymybuddy.service.connection;

import java.sql.SQLException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.paymybuddy.dao.AddConnectionTransaction;
import com.paymybuddy.dao.ConnectionDAO;
import com.paymybuddy.entity.Econnection;
import com.paymybuddy.repository.EconnectionRepository;

@Service
public class AddConnectionService {

	final static Logger addConnectionServiceLogger = LogManager.getLogger("AddConnectionService");
	
	@Autowired
	EconnectionRepository connectionRepositoryAtAddConnection;
	
	@Autowired
	ConnectionDAO connectionDAOAtAddConnectionService;
	
	@Autowired
	AddConnectionTransaction addConnectionTransaction;
	
	
	AddConnectionService(EconnectionRepository connectionRepositoryAtAddConnection
						,ConnectionDAO connectionDAOAtAddConnectionService
						,AddConnectionTransaction addConnectionTransaction
			){
		this.connectionRepositoryAtAddConnection = connectionRepositoryAtAddConnection;
		this.connectionDAOAtAddConnectionService = connectionDAOAtAddConnectionService;
		this.addConnectionTransaction = addConnectionTransaction;
	}
	
/*	public void addConnectionByAttributes(int id, String payerUserName, String payeeUserName) {
		
		if(Objects.isNull(connectionRepositoryAtAddConnection.findByFkPayerUserNameAndFkPayeeUserName(payerUserName, payeeUserName))) {
			connectionDAOAtAddConnectionService.saveConnection(id, payerUserName, payeeUserName);
			addConnectionServiceLogger.info("Connection between "+payerUserName+" and "+payeeUserName+" created");
		}else {
			addConnectionServiceLogger.info("Connection between "+payerUserName+" and "+payeeUserName+" all ready exists");
		}
	}*/

	public void addConnection(Econnection newConnection) throws SQLException {
		
		if(Objects.isNull(connectionRepositoryAtAddConnection.findByFkPayerUserNameAndFkPayeeUserName(newConnection.fkPayerUserName, newConnection.fkPayeeUserName))){
			addConnectionServiceLogger.info("Connection between "+newConnection.fkPayerUserName+" and "+newConnection.fkPayeeUserName+" created");
			//connectionRepositoryAtAddConnection.save(newConnection);
			addConnectionTransaction.transaction(newConnection);
		}else {
			addConnectionServiceLogger.info("Connection between "+newConnection.fkPayeeUserName+" and "+newConnection.fkPayerUserName+" all ready exists");
		System.out.println(connectionRepositoryAtAddConnection.findByFkPayerUserNameAndFkPayeeUserName(newConnection.fkPayerUserName, newConnection.fkPayeeUserName));
		}
		
			
	}
}
