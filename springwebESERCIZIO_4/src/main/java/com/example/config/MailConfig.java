package com.example.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.model.MailModel;
import com.example.service.MailModelService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;

@Configuration
public class MailConfig {

	@Autowired
	MailModelService service;
	
	public String sendMail(MailModel email) {
		
		Mail mailCreata = service.createMail(email);
		
		// Impostazione API KEY di sendgrid
		SendGrid sg = new SendGrid("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");
		
		// Request da sendgrid
		Request request =  new Request();
		
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mailCreata.build());
			
			Response response = sg.api(request);
			return "Mail inviata correttamente";
			
		}catch(IOException ex) {
			return "Impossibile inviare la mail";
		}
		
	}
	
	
}
