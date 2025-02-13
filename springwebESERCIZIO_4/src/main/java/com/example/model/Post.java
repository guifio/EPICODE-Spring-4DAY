package com.example.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String categoria;
	
	@Column(nullable = false)
	private String titolo;
	private String cover;
	
	@Column(nullable = false)
	private String contenuto;
	private int tempoLettura;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Author autore;
	
	
	public Author getAutore() {
		return autore;
	}
	public void setAutore(Author autore) {
		this.autore = autore;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
		return "Post [id=" + id + ", categoria=" + categoria + ", titolo=" + titolo + ", cover=" + cover
				+ ", contenuto=" + contenuto + ", tempoLettura=" + tempoLettura + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		return id == other.id;
	}
	
	
	
	
	
}
