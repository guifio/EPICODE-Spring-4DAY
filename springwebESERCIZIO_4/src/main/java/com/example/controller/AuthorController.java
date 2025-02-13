package com.example.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.validation.Valid;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.dto.AuthorDTO;
import com.example.model.EpicodeException;
import com.example.service.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	@Autowired						
	AuthorService service;
	
	@Autowired
	Cloudinary configurazioneCloud;
	
	@PostMapping(value="/nuovoAutore", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = "application/json")
	public ResponseEntity<?> inserisciAutore(@RequestPart("avatar") MultipartFile avatar, @RequestPart @Validated AuthorDTO autoreNuovo, BindingResult validation) {

		if(validation.hasErrors()) {
			// come visualizzare gli errori al client
			String messaggioErrore ="";

			for(ObjectError errore : validation.getAllErrors()){
				messaggioErrore += errore.getDefaultMessage() +"\n";
			}

			return new ResponseEntity<>(messaggioErrore,HttpStatus.BAD_REQUEST);
		}else {

			String messaggio;
			HttpStatus stato;
			try {

				Map mappa = configurazioneCloud.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap());
				String urlImage=mappa.get("secure_url").toString();
				autoreNuovo.setAvatar(urlImage);			
				messaggio = service.insertAuthor(autoreNuovo);
				stato = HttpStatus.OK;
			} catch (Exception e) {
				if(e.getMessage().contains("key")){
					messaggio="Mail già presente nel sistema";
					stato = HttpStatus.BAD_REQUEST;
				}else {
					messaggio="Errore nel sistema";
					stato = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}

			return  new ResponseEntity<>(messaggio, stato);
		}


	}

	@PostMapping(value="/popolaAutori", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = "application/json")
	public ResponseEntity<?> popolaAutori(@RequestPart("avatars") List<MultipartFile> avatars, @RequestPart @Valid List<AuthorDTO> autori, BindingResult validation) {

		System.out.println("Sono nel metodo di popolamento autori");

		if(validation.hasErrors()) {
			// come visualizzare gli errori al client
			String messaggioErrore ="";

			for(ObjectError errore : validation.getAllErrors()){
				messaggioErrore += errore.getDefaultMessage() +"\n";
			}

			return new ResponseEntity<>(messaggioErrore,HttpStatus.BAD_REQUEST);
		}else {

			// Impostazione delle credenziali Cloudinary 
			Dotenv dotenv = Dotenv.load();
			Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));

			for(int i=0, j=0;i<avatars.size() && j<autori.size(); i++,j++) {

				try {
					AuthorDTO autoreDto = autori.get(i);
					MultipartFile foto = avatars.get(i);
					Map mappa = cloudinary.uploader().upload(foto.getBytes(), ObjectUtils.emptyMap());
					String urlImage=mappa.get("secure_url").toString();
					autoreDto.setAvatar(urlImage);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}

			try {
				return new ResponseEntity<>(service.popolaAuthors(autori), HttpStatus.CREATED);
			}catch(Exception ex) {
				if(ex.getMessage().contains("key")) {
					return new ResponseEntity<>("Email duplicata" , HttpStatus.FORBIDDEN);
				}else {
					return new ResponseEntity<>("Errore nel sistema" , HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}

		}

	}

	@GetMapping(value="/vediAutori", produces="application/json")
	public List<AuthorDTO> vediAutori(){
		return service.getAllAuthors();
	}

	@GetMapping("/vediAutore/{id}")
	public AuthorDTO recuperaAutore(@PathVariable Long id) {
		Optional<AuthorDTO> autoreRecuperato = service.getAuthor(id);
		if(autoreRecuperato.isEmpty()) {
			throw new EpicodeException("Autore non presente nel sistema");
		}else {
			return autoreRecuperato.get();
		}

	}

	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<?> modificaAutore(@RequestPart("avatar") MultipartFile avatar, @RequestPart @Validated AuthorDTO autoreModificato, BindingResult validation, @PathVariable Long id) {

		if(validation.hasErrors()) {
			// come visualizzare gli errori al client
			String messaggioErrore ="";

			for(ObjectError errore : validation.getAllErrors()){
				messaggioErrore += errore.getDefaultMessage() +"\n";
			}

			return new ResponseEntity<>(messaggioErrore,HttpStatus.BAD_REQUEST);
		}else {

			// Impostazione delle credenziali Cloudinary 
			Dotenv dotenv = Dotenv.load();
			Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));


			String messaggio ="";
			HttpStatus stato;
			try {

				Map mappa = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.emptyMap());

				String urlImage=mappa.get("secure_url").toString();
				autoreModificato.setAvatar(urlImage);			

				boolean esito = service.updateAuthor(autoreModificato, id);
				if(esito) {
					return new ResponseEntity<>("L'autore " +autoreModificato.getCognome()+ " è stato modificato", HttpStatus.BAD_REQUEST);
				}else {
					throw new EpicodeException("Autore da modificare non presente nel sistema");
				}
			} catch (Exception e) {
				messaggio="Errore nel sistema";
				stato = HttpStatus.INTERNAL_SERVER_ERROR;
				return new ResponseEntity<>(messaggio, stato);
			}
		}

	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String cancellaAutore(@PathVariable Long id) {
		try{
			service.deleteAuthor(id);
			return "Autore cancellato in modo corretto";
		}catch(EpicodeException ex) {
			return ex.getMessage();
		}

	}



}
