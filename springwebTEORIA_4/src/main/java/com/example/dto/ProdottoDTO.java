package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProdottoDTO {

	@NotBlank(message = "Il campo descrizione risulta vuoto") 	
	@NotNull(message = "Il campo descrizione è obbligatorio")
	private String descrizione;

	private String immagineprodotto;
	
	private String taglia;
	private String marca;
	
	@NotNull(message = "Il prezzo è un valore obbligatorio")
	private double prezzo;

	
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImmagineProdotto() {
		return immagineprodotto;
	}

	public void setImmagineProdotto(String immagineProdotto) {
		this.immagineprodotto = immagineProdotto;
	}

	public String getTaglia() {
		return taglia;
	}

	public void setTaglia(String taglia) {
		this.taglia = taglia;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
	public ProdottoDTO() {}
	
}
