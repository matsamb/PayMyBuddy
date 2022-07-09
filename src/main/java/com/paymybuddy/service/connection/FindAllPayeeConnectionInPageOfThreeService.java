package com.paymybuddy.service.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.Econnection;
import com.paymybuddy.repository.EconnectionRepository;

/*@Service
public class FindAllPayeeConnectionInPageOfThreeService {

	@Autowired
	EconnectionRepository connectionRepository;
	
	FindAllPayeeConnectionInPageOfThreeService(EconnectionRepository connectionRepository){
		this.connectionRepository = connectionRepository;
	}
	
	public Page<Econnection> findConnectionInPagesOfThree(String username){
		Pageable pageable = PageRequest.of(0, 3);
		return connectionRepository.findAll(pageable);	
		
	}
	
}*/
