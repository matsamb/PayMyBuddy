package com.paymybuddy.services.IT;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.paymybuddy.service.email.EmailSenderService;

@SpringBootTest
public class EmailSenderServiceIT {

	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private JavaMailSenderImpl javaMailSender;
		
	@Test
	public void test() throws Exception{

		emailSenderService.send("max@max.max", "Hi mate");
				
		assertThat(emailSenderService.getSent()).isEqualTo("max@max.max_Hi mate");
		
	}
	
}
