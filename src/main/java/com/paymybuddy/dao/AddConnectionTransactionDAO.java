package com.paymybuddy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.activation.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import com.paymybuddy.entity.Econnection;

@Component
public class AddConnectionTransactionDAO {

	static final Logger addConnectionTransactionLogger = LogManager.getLogger("AddConnectionTransaction");
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void saveEconnectionByPrepareStatement(Econnection econnection) throws SQLException {
				
		String query = "INSERT IGNORE INTO econnection (fk_payer_username, fk_payee_username) VALUES (?,?); ";
			
		jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {

			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				
				ps.setString(1, econnection.fkPayerUserName);
				ps.setString(2, econnection.fkPayeeUserName);
				
				addConnectionTransactionLogger.info(
					"save statement executed for "+ econnection.getFkPayerUserName()+" "+econnection.getFkPayeeUserName()+" connection");

				return ps.execute();
			}
		
		});
	
}
}
