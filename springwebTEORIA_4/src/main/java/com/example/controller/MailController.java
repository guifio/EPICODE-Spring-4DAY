package com.example.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.MailModel;
import com.example.service.MailModelService;
import com.example.service.WelcomeService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;


@RestController
@RequestMapping("/mail")
public class MailController {

	@Autowired
	MailModelService service;
	
	@PostMapping("/sendMail")
	public String sendMail(@RequestBody MailModel email) {
		
		Mail mailCreata = service.createMail(email);
		
		// Impostazione API KEY di sendgrid
		SendGrid sg = new SendGrid("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");
		
		// Request da sendgrid
		Request request =  new Request();
		
		try {
			// Impostazione dati della richiesta : metodo Post, end point, costruzione body 
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mailCreata.build());
			
			// Impostazione response
			Response response = sg.api(request);
			
			// Test sull'invio
//			System.out.println("Status code : " +response.getStatusCode());
//			System.out.println("Body : " +response.getBody());
//			System.out.println("Headers : " +response.getHeaders());
			return "Mail inviata correttamente";
			
		}catch(IOException ex) {
			return "Impossibile inviare la mail";
		}
		
	}
	
	
	
}
