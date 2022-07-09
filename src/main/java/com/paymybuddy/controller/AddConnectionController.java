package com.paymybuddy.controller;

import java.sql.SQLException;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.paymybuddy.dto.ViewConnection;
import com.paymybuddy.entity.Econnection;
import com.paymybuddy.entity.PaymybuddyUserDetails;
import com.paymybuddy.service.connection.AddConnectionService;
import com.paymybuddy.service.users.CheckUsersService;

@RolesAllowed("USER")
@Controller
public class AddConnectionController {

	final static Logger addConnectionControllerLogger = LogManager.getLogger("AddConnectionController");
	
	//@Autowired
	//PaymybuddyUserDetails paymybuddyUserDetailsAtAddConnectionController;
	
	@Autowired
	AddConnectionService addConnectionServiceAtAddConectionController;
	
	@Autowired
	CheckUsersService checkUsersServiceAtAddConectionController;
	
	AddConnectionController(AddConnectionService addConnectionServiceAtAddConectionController
							,CheckUsersService checkUsersServiceAtAddConectionController
							//,PaymybuddyUserDetails paymybuddyUserDetailsAtAddConnectionController
			){
		this.addConnectionServiceAtAddConectionController = addConnectionServiceAtAddConectionController;
		this.checkUsersServiceAtAddConectionController = checkUsersServiceAtAddConectionController;
		//this.paymybuddyUserDetailsAtAddConnectionController = paymybuddyUserDetailsAtAddConnectionController;
	}
	
	
	@GetMapping("/addconnection")
	public String getAddConnection(Authentication auth, ViewConnection vConnec, BindingResult binding) {	
		
		return "addconnection";	
	}
	
	@PostMapping("/addconnection")
	public ModelAndView createConnection(Authentication auth, ViewConnection vConnec, BindingResult binding) {
	
		ModelAndView result;
		
		UserDetails user = new PaymybuddyUserDetails();
		user  = (UserDetails) auth.getPrincipal();
		addConnectionControllerLogger.debug(user.getUsername()+" "+vConnec.getConnection());
		if(checkUsersServiceAtAddConectionController.checkUsersByUsername(vConnec.getConnection())==true) {
			addConnectionControllerLogger.debug("user "+vConnec.getConnection()+" registered");
			
			Econnection newConnection = new Econnection();
			newConnection.setFkPayeeUserName(vConnec.getConnection());
			newConnection.setFkPayerUserName(user.getUsername());
			
			addConnectionControllerLogger.debug(newConnection);
			
			try {
				addConnectionServiceAtAddConectionController.addConnection(newConnection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			result = new ModelAndView("redirect:/addconnection?success=true");
			addConnectionControllerLogger.info("Connection between "+vConnec.getConnection()+" and "+user.getUsername()+" created");
			
		}else {
			addConnectionControllerLogger.info("fail to Connect "+vConnec.getConnection()+" and "+user.getUsername());
			result = new ModelAndView("redirect:/addconnection?error=true");
		}
		addConnectionControllerLogger.info("Connection requested");		
		return result;
		
	}
	
}
