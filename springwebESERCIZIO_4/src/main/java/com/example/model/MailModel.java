package com.example.model;

public class MailModel {

	private String mailModelFrom;
	private String mailModelTo;
	private String subject;
	private String content;
	
	public MailModel(String mailModelFrom, String mailModelTo, String subject, String content) {
		super();
		this.mailModelFrom = mailModelFrom;
		this.mailModelTo = mailModelTo;
		this.subject = subject;
		this.content = content;
	}
	
	
	public MailModel() {}
	
	public String getmailModelFrom() {
		return mailModelFrom;
	}
	public void setmailModelFrom(String mailModelFrom) {
		this.mailModelFrom = mailModelFrom;
	}
	public String getmailModelTo() {
		return mailModelTo;
	}
	public void setmailModelTo(String mailModelTo) {
		this.mailModelTo = mailModelTo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
	
}
