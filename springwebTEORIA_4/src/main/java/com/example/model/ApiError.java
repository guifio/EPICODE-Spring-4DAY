package com.example.model;

import org.springframework.http.HttpStatus;

public class ApiError {

	private String messaggio;
	private HttpStatus stato;
	
	
	public ApiError(String mes, HttpStatus stato) {
		this.messaggio = mes;
		this.stato = stato;
	}
	
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	public HttpStatus getStato() {
		return stato;
	}
	public void setStato(HttpStatus stato) {
		this.stato = stato;
	}
	
	
}
