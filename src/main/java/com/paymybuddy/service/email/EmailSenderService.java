package com.paymybuddy.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailSenderService implements IemailSenderService{

	private static Logger logger = LogManager.getLogger("EmailSenderService");
	
	@Autowired
	JavaMailSenderImpl javaMailSender;
	
	@Override
	@Async
	public void send(String to, String email) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setFrom("buddy@test.com");
			mimeMessageHelper.setSubject("Paymybuddy account activation");
			mimeMessageHelper.setText(email, true);
			
			javaMailSender.send(mimeMessage);
		
		} catch (MessagingException m) {
			logger.info("email not sent",m);
		}

		
		/*
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			//JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
					
			simpleMailMessage.setTo(to);
			simpleMailMessage.setFrom("buddy@test.com");
			simpleMailMessage.setSubject("Paymybuddy account activation");
			simpleMailMessage.setText(email);
			
			javaMailSender.send(simpleMailMessage);
		}catch(MailException m) {
			logger.info("email not sent",m);
		}		*/
	}
	
	//@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
	
		try {
			javaMailSender.send(simpleMessage);
		}catch(MailException m) {
			logger.info("email not sent",m);
		}
		
		
	}
	
	
	public void sendMimeMessage(MimeMessage mimeMessage) throws MailException {
		
		try {
			javaMailSender.send(mimeMessage);
		}catch(MailException m) {
			logger.info("email not sent",m);
		}
		
		
	}

	//@Override
	public void send(SimpleMailMessage... simpleMessages) throws MailException {
		try {
			javaMailSender.send(simpleMessages);
		}catch(MailException m) {
			logger.info("emails not sent",m);
		}
	}


}
