package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.MailModel;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class MailModelService {

	public Mail createMail(MailModel email) {
		
		// Email da sendgrid : impostazione mittente
		Email mittente = new Email(email.getmailModelFrom());
		
		// Email da sendgrid : impostazione destinatario
		Email destinatario= new Email(email.getmailModelTo());
		
		// Content da sendgrid : impostazione contenuto
		Content contenuto = new Content("text/plain", email.getContent());
		
		// Mail da sendgrid : impostazione Mail finale
		Mail mail = new Mail(mittente, email.getSubject(), destinatario, contenuto);
		
		return mail;
		
	}
	
	
}
