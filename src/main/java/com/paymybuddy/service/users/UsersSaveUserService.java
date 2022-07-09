package com.paymybuddy.service.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paymybuddy.entity.Users;
import com.paymybuddy.repository.UsersRepository;

/*@Service
public class UsersSaveUserService {

	private static Logger eusersSaveEuserServiceLogger = LogManager.getLogger("EusersSaveEuserService");
	
	@Autowired
	UsersRepository usersRepositoryAtUsersSaveUserService;
	
	UsersSaveUserService(UsersRepository usersRepositoryAtUsersSaveUserService){
		this.usersRepositoryAtUsersSaveUserService = usersRepositoryAtUsersSaveUserService;
	}

	public void saveUser(Users neweuser) {
		usersRepositoryAtUsersSaveUserService.save(neweuser);
		eusersSaveEuserServiceLogger.info("new user: "+neweuser.getUsername()+" registered");
	}
		
}*/
