package com.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PostDTO {

	@NotNull
	@Size(min = 3, max=50, message = "Il titolo deve avere un minimo di 3 caratteri fino ad un massimo di 50")
	private String titolo;
	
	@NotNull(message = "La categoria del post è obbligatoria")
	private String categoria;
	private String cover;
	
	@NotNull(message = "Il contenuto del post è obbligatorio")
	@NotBlank(message = "Il contenuto risulta vuoto")
	private String contenuto;
	
	@Positive(message = "La durata deve essere di almeno un minuto")
	@Max(value = 15, message="La durata supera gli standard del blog (15min)")
	private int tempoLettura;
	
	@NotNull(message = "Specificare l'autore del post")
	private Long fk_autore;
	
	
	
	public Long getFk_autore() {
		return fk_autore;
	}
	public void setFk_autore(Long fk_autore) {
		this.fk_autore = fk_autore;
	}

	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getContenuto() {
		return contenuto;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	public int getTempoLettura() {
		return tempoLettura;
	}
	public void setTempoLettura(int tempoLettura) {
		this.tempoLettura = tempoLettura;
	}
	@Override
	public String toString() {
		return "Post [categoria=" + categoria + ", titolo=" + titolo + ", cover=" + cover
				+ ", contenuto=" + contenuto + ", tempoLettura=" + tempoLettura + "]";
	}
	

	
	
	
	
	
}
