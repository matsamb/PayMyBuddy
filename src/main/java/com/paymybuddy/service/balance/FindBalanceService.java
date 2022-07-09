package com.paymybuddy.service.balance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entity.Balance;
import com.paymybuddy.repository.BalanceRepository;

/*@Service
public class FindBalanceService {

	static final Logger findBalanceServiceLogger = LogManager.getLogger("FindBalanceService");
	
	@Autowired
	BalanceRepository balanceRepositoryAtFindBalanceService;
	
	FindBalanceService(BalanceRepository balanceRepositoryAtFindBalanceService
			){
		this.balanceRepositoryAtFindBalanceService = balanceRepositoryAtFindBalanceService;
	}

	public Balance findBalanceByUsername(String username) {
		Balance result = new Balance();
		result = balanceRepositoryAtFindBalanceService.getById(username);
		return result;
	}
	
}*/
