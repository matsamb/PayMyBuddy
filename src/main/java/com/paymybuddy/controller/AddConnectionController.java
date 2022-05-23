package com.paymybuddy.controller;

import javax.annotation.security.RolesAllowed;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.paymybuddy.service.connection.AddConnectionService;

@RolesAllowed("USER")
@Controller
public class AddConnectionController {

	final static Logger addConnectionControllerLogger = LogManager.getLogger("AddConnectionController");
	
	@Autowired
	AddConnectionService addConnectionServiceAtAddConectionController;
	
	AddConnectionController(AddConnectionService addConnectionServiceAtAddConectionController){
		this.addConnectionServiceAtAddConectionController = addConnectionServiceAtAddConectionController;
	}
	
	
	@GetMapping("/addconnection")
	public String getAddConnection() {
		
		addConnectionServiceAtAddConectionController.addConnection(1,"bowl", "ywa");
		addConnectionControllerLogger.info("Connection requested");
		
		return "addconnection";
		
	}
	
}
