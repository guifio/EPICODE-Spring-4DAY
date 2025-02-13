package com.example.model;

import jakarta.persistence.*;



@Entity
public class Prodotto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProdotto;
	
	@Column(nullable = false)
	private String descrizione;
	
	private String taglia;
	private String marca;
	
	@Column(nullable = false)
	private double prezzo;
	
	@Column(unique = true, nullable = false)
	private String codice;
	
	private String immagineProdotto;

	public Long getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(Long idProdotto) {
		this.idProdotto = idProdotto;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getImmagineProdotto() {
		return immagineProdotto;
	}

	public void setImmagineProdotto(String immagineProdotto) {
		this.immagineProdotto = immagineProdotto;
	}
		
	public Prodotto() {}
	
}
