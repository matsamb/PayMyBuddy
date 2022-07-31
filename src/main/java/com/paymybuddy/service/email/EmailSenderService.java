package com.paymybuddy.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.context.annotation.Bean;
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

	}

}
