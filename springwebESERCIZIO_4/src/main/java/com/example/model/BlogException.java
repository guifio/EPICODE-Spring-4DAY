package com.example.model;

import org.springframework.http.HttpStatus;

public class BlogException {

	private String messaggio;
	private HttpStatus statoErrore;
	
	
	public BlogException(String msg, HttpStatus stato) {
		messaggio=msg;
		statoErrore=stato;
	}
	
	
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public HttpStatus getStatoErrore() {
		return statoErrore;
	}
	public void setStatoErrore(HttpStatus statoErrore) {
		this.statoErrore = statoErrore;
	}
	
	
	
}

