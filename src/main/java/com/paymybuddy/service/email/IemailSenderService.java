package com.paymybuddy.service.email;

import org.springframework.mail.MailSender;

public interface IemailSenderService /*extends MailSender */{

	public void send(String recipient, String email);
	
}
