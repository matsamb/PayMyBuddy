package com.paymybuddy.service.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.repository.UsersRepository;

/*@Service
public class CheckUsersService {

	public final Logger findUsersServiceLogger = LogManager.getLogger("FindUsersService");
	
	@Autowired
	UsersRepository usersRepositoryAtFindUsersService;
	
	CheckUsersService(UsersRepository usersRepositoryAtFindUsersService){
		this.usersRepositoryAtFindUsersService = usersRepositoryAtFindUsersService;
	}

	public Boolean checkUsersByUsername(String connection) {
		
		if(usersRepositoryAtFindUsersService.existsById(connection)==true) {
			findUsersServiceLogger.info("user "+connection+" found");
			return usersRepositoryAtFindUsersService.existsById(connection);
		}else {
			findUsersServiceLogger.info("user "+connection+" not registered");
			return false;
		}
		
	}
	
	
	
}*/
