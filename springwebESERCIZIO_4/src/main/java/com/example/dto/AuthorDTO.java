package com.example.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AuthorDTO {
	
    @Size(min = 3, message = "Nome troppo corto")
    @Size(max = 20, message = "Nome troppo lungo")
	private String nome;
	
	@NotNull(message = "Il cognome è un campo obbligatorio")
	@NotBlank(message = "Il cognome risulta vuoto")
	@Size(min = 3, message = "Cognome troppo corto")
    @Size(max = 25, message = "Cognome  troppo lungo")
	private String cognome;
	
	@Email(message = "L'indirizzo mail non è valido")
	private String email;
	
	@Past(message = "La data di nascita deve essere nel passato")
	private LocalDate dataNascita;
	
	@URL(protocol ="http", message="Avatar non disponibile" )
	private String avatar;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	
}
