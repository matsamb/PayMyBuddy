package com.paymybuddy.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateBalanceDAO {

	final static Logger updateBalanceDAOLogger = LogManager.getLogger("UpdateBalanceDAO");
	
	@Autowired
	JdbcTemplate jdbcTemplateAtUpdateBalanceDAO;

	UpdateBalanceDAO(JdbcTemplate jdbcTemplateAtConnectionDAO) {
		this.jdbcTemplateAtUpdateBalanceDAO = jdbcTemplateAtConnectionDAO;
	}
	
	public Boolean updateBalance(final String username, final String firstName, final String lastName, Float balance) {
	
	String query = "UPDATE balance SET first_name= ? , last_name= ? , balance= ?  WHERE fk_username= ? "; //values(?,?,?,?) WHERE fk_username= username 
	return jdbcTemplateAtUpdateBalanceDAO.execute(query, new PreparedStatementCallback<Boolean>() {

		@Override
		public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setFloat(3, balance);
			ps.setString(4, username);
			
			updateBalanceDAOLogger.info(balance+" updated");
			return ps.execute();
		}
	
	});
	}
	
}