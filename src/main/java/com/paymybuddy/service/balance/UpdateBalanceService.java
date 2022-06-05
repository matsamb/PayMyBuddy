package com.paymybuddy.service.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.dao.UpdateBalanceDAO;
import com.paymybuddy.entity.Balance;

@Service
public class UpdateBalanceService {

	@Autowired
	UpdateBalanceDAO updateBalanceDAO;
	
	UpdateBalanceService(UpdateBalanceDAO updateBalanceDAO){
		this.updateBalanceDAO = updateBalanceDAO;
	}

	public void updateBalance(Balance balance) {
		updateBalanceDAO.updateBalance(balance.getUsername(), balance.getFirstName(), balance.getLastName(), balance.getBalance());
	}
	
}
