package com.paymybuddy.service.connection;

import java.sql.SQLException;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.dao.AddConnectionTransactionDAO;
import com.paymybuddy.dao.ConnectionDAO;
import com.paymybuddy.entity.Econnection;
import com.paymybuddy.repository.EconnectionRepository;

/*@Service
public class AddConnectionService {

	final static Logger addConnectionServiceLogger = LogManager.getLogger("AddConnectionService");
	
	@Autowired
	EconnectionRepository connectionRepositoryAtAddConnection;
	
	@Autowired
	ConnectionDAO connectionDAOAtAddConnectionService;
	
	@Autowired
	AddConnectionTransactionDAO addConnectionTransactionDao;
	
	
	AddConnectionService(EconnectionRepository connectionRepositoryAtAddConnection
						,ConnectionDAO connectionDAOAtAddConnectionService
						,AddConnectionTransactionDAO addConnectionTransactionDao
			){
		this.connectionRepositoryAtAddConnection = connectionRepositoryAtAddConnection;
		this.connectionDAOAtAddConnectionService = connectionDAOAtAddConnectionService;
		this.addConnectionTransactionDao = addConnectionTransactionDao;
	}
	

	public void addConnection(Econnection newConnection) throws SQLException {
		
		if(Objects.isNull(connectionRepositoryAtAddConnection.findByFkPayerUserNameAndFkPayeeUserName(newConnection.fkPayerUserName, newConnection.fkPayeeUserName))){
			addConnectionServiceLogger.info("Connection between "+newConnection.fkPayerUserName+" and "+newConnection.fkPayeeUserName+" created");
			//connectionRepositoryAtAddConnection.save(newConnection);
			addConnectionTransactionDao.saveEconnectionByPrepareStatement(newConnection);
		}else {
			addConnectionServiceLogger.info("Connection between "+newConnection.fkPayeeUserName+" and "+newConnection.fkPayerUserName+" all ready exists");
		System.out.println(connectionRepositoryAtAddConnection.findByFkPayerUserNameAndFkPayeeUserName(newConnection.fkPayerUserName, newConnection.fkPayeeUserName));
		}
		
			
	}
}*/
