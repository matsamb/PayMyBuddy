package com.paymybuddy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.activation.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paymybuddy.entity.Econnection;

@Component
public class AddConnectionTransaction {

	static final Logger addConnectionTransactionLogger = LogManager.getLogger("AddConnectionTransaction");
	
	@Autowired
	javax.sql.DataSource data;
	//@Autowired
	public void transaction(Econnection econnection) throws SQLException {
	//Connection nection;	
		
	Connection nection = data.getConnection();
	PreparedStatement psIn = nection.prepareStatement(
	"insert ignore into econnection (fk_payer_username, fk_payee_username) values(?,?)");		
	addConnectionTransactionLogger.info("transaction started");
	try{
		
		nection.setAutoCommit(false);
		
		psIn.setString(1, econnection.fkPayerUserName);
		psIn.setString(2, econnection.fkPayeeUserName);
		psIn.execute();
		addConnectionTransactionLogger.info("ready to commit");
		nection.commit();
		
			
	}catch(Exception e){
		nection.rollback();
		addConnectionTransactionLogger.info("rollback");
	}finally {
		nection.setAutoCommit(true);
		nection.close();
	}
	
}
}
