package com.paymybuddy.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

@Component
public class ConnectionDAO {

	@Autowired
	JdbcTemplate jdbcTemplateAtConnectionDAO;

	ConnectionDAO(JdbcTemplate jdbcTemplateAtConnectionDAO) {
		this.jdbcTemplateAtConnectionDAO = jdbcTemplateAtConnectionDAO;
	}
	
	public Boolean saveConnection(final int id, final String fk_payer_username, final String fk_payee_username) {
	
	String query = "insert ignore into econnection (id, fk_payer_username, fk_payee_username) values(?,?,?)";
	return jdbcTemplateAtConnectionDAO.execute(query, new PreparedStatementCallback<Boolean>() {

		@Override
		public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			
			ps.setInt(1, id);
			ps.setString(2, fk_payer_username);
			ps.setString(3, fk_payee_username);
			
			return ps.execute();
		}
	
	});
	}
	
}
