package com.example.service;

import java.security.ProtectionDomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.ProdottoDTO;
import com.example.model.Prodotto;
import com.example.repository.ProdottoRepository;

@Service
public class ProdottoService {

	@Autowired
	ProdottoRepository repo;
	
	/**
	 * Metodo di inserimento di un nuovo prodotto
	 * @param prodottoDto
	 * @return
	 */
	public Long nuovoProdotto(ProdottoDTO prodottoDto) {
		Prodotto prodotto= dto_entity(prodottoDto);
		return repo.save(prodotto).getIdProdotto();
	}
	
	
	// DTO -> ENTITY
	public Prodotto dto_entity(ProdottoDTO prodottoDto) {
		Prodotto prodotto= new Prodotto();
		prodotto.setCodice(generaCodice(prodottoDto.getDescrizione()));
		prodotto.setDescrizione(prodottoDto.getDescrizione());
		prodotto.setMarca(prodottoDto.getMarca());
		prodotto.setPrezzo(prodottoDto.getPrezzo());
		prodotto.setTaglia(prodottoDto.getTaglia());
		prodotto.setImmagineProdotto(prodottoDto.getImmagineProdotto());
		
		
		return prodotto;
	}
	
	// ENTITY -> DTO
	public ProdottoDTO entity_dto(Prodotto prodotto) {
		ProdottoDTO prodottoDto= new ProdottoDTO();
				
		prodottoDto.setDescrizione(prodotto.getDescrizione());
		prodottoDto.setMarca(prodotto.getMarca());
		prodottoDto.setPrezzo(prodotto.getPrezzo());
		prodottoDto.setTaglia(prodotto.getTaglia());
		prodottoDto.setImmagineProdotto(prodotto.getImmagineProdotto());
		
		return prodottoDto;
	}
	
	
	private String generaCodice(String descrizione) {
		// elaborazione e mi genera un codice
		int num= (int)(Math.random()*1000)+1;
		
		return "descrizione" +num;
	}
}
