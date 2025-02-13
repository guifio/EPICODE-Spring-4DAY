package com.example.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.dto.ProdottoDTO;
import com.example.service.ProdottoService;

@RestController
@RequestMapping("/prodotto")
public class ProdottoController {

	@Autowired
	ProdottoService service;
	
	@Autowired
	Cloudinary cloudinaryConfig;
	
	@PostMapping("/new")
	public ResponseEntity<String> nuovoProdottoTestuale(@RequestBody @Validated ProdottoDTO dto, BindingResult validazione) {
	
		// c'è stato qualche problema nella validazione?
		if(validazione.hasErrors()) {

			String messaggioErrori="ERRORE DI VALIDAZIONE \n";
			
			// INVIARE I MESSAGGI DI ERRORE AL CLIENT
			for(ObjectError errore : validazione.getAllErrors()) {
				messaggioErrori +=errore.getDefaultMessage()+"\n";
			}

			return new ResponseEntity<>(messaggioErrori, HttpStatus.BAD_REQUEST);
			
		}
		
		Long idgenerato = service.nuovoProdotto(dto);
		return new ResponseEntity<>("Prodotto generato con successo. Id generato : " +idgenerato, HttpStatus.CREATED);
		
	}
	
	
	@PostMapping("/newImage")
	public ResponseEntity<String> nuovoProdottoImmagine(@RequestPart("immagineProdotto") MultipartFile immagineProdotto, @RequestPart @Validated ProdottoDTO dto, BindingResult validazione) {
	
		// c'è stato qualche problema nella validazione?
		if(validazione.hasErrors()) {

			String messaggioErrori="ERRORE DI VALIDAZIONE \n";
			
			// INVIARE I MESSAGGI DI ERRORE AL CLIENT
			for(ObjectError errore : validazione.getAllErrors()) {
				messaggioErrori +=errore.getDefaultMessage()+"\n";
			}

			return new ResponseEntity<>(messaggioErrori, HttpStatus.BAD_REQUEST);
			
		}
		
		
		// Inviamo il file al cloud --> ci ritorna un link all'immagine
		try {
			
			// tramite l'oggetto Cloudinary e successivamente Uploader richiamo il metodo upload
			// il quale invia il mio file al servizio esterno.
			// Il metodo ritorna una mappa con diverse informazioni.
			Map mappa = cloudinaryConfig.uploader().upload(immagineProdotto.getBytes(), ObjectUtils.emptyMap());
			
			// Recupero l'info utile per il DATABASE --> url (String)
			String urlImage=mappa.get("secure_url").toString();
			
			// Inserisco l'url nell'oggetto DTO
			dto.setImmagineProdotto(urlImage);
			
			// Effettuo la chiamata a service per l'inserimento finale
			Long idgenerato = service.nuovoProdotto(dto);
			return new ResponseEntity<>("Prodotto generato con successo. Id generato : " +idgenerato, HttpStatus.CREATED);
		} catch (IOException e) {
			throw new RuntimeException("");
		}
		
		
		
	}
	
	
	
	
	
}
