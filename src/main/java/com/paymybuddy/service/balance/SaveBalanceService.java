package com.paymybuddy.service.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.entity.Balance;
import com.paymybuddy.repository.BalanceRepository;

@Service
public class SaveBalanceService {

	@Autowired
	BalanceRepository balanceRepository;
	
	SaveBalanceService(BalanceRepository balanceRepository){
		this.balanceRepository = balanceRepository;
	}

	public void saveBalance(Balance balance) {
		balanceRepository.delete(balance);
		balanceRepository.save(balance);		
	}
	
}
