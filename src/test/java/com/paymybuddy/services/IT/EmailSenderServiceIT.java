package com.paymybuddy.services.IT;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.paymybuddy.service.email.EmailSenderService;

@SpringBootTest
public class EmailSenderServiceIT {

	@Autowired
	private EmailSenderService emailSenderService;
		
	@Test
	public void test() throws Exception{

		emailSenderService.send("max@max.max", "Hi mate");
				
		assertThat(emailSenderService.getSent()).isEqualTo("max@max.max_Hi mate");
		
	}
	
}
