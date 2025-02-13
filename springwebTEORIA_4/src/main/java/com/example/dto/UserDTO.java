package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {
	
	@NotNull(message = "Il nome è obbligatorio")
	@Size(min=3, max=30, message="Il nome deve avere minimo 3 caratteri, massimo 30")
	private String name;
	
	@NotNull(message = "Il cognome è obbligatorio")
	private String surname;
	
	@NotNull(message = "L'email è obbligatoria")
	@Email(message="L'email inserita non è un indirizzo valido")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
