package com.example.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.config.MailConfig;
import com.example.dto.AuthorDTO;
import com.example.model.Author;
import com.example.model.EpicodeException;
import com.example.model.MailModel;
import com.example.repository.AuthorRepository;


@Service
@Transactional
public class AuthorService {

	@Autowired
	AuthorRepository repo;
	
	@Autowired
	MailConfig mailConfig;
	
	public String insertAuthor(AuthorDTO nuovoAutoreDTO) throws Exception{
		Author nuovoAutore =fromDTOtoEntity(nuovoAutoreDTO);
			repo.save(nuovoAutore);
			
			// effettuata la registrazione sul database invio la mail
			MailModel mail = new MailModel("fiorenza.guido@gmail.com", "fiorenza.guido@gmail.com" ,"Registrazione completata", 
									   "Benvenuto nel blog. La tua registrazione è stata completata con successo");
			mailConfig.sendMail(mail);
			
			return "L'autore " +nuovoAutore.getCognome()+ " inserito correttamente";
	}
	
	public String popolaAuthors(List<AuthorDTO> autoriDTO) {
		for(AuthorDTO singoloAutoreDTO : autoriDTO) {
			Author singoloAutore =fromDTOtoEntity(singoloAutoreDTO);
			repo.save(singoloAutore);
		}
		return "La lista degli autori è stata inserita correttamente nel sistema";
	}
	
	public List<AuthorDTO> getAllAuthors() {
		List<Author> lista = repo.findAll();
		List<AuthorDTO> listaDTO = new ArrayList<>();
		
		for(Author singoloAutore : lista) {
			AuthorDTO autoreDto = fromEntityTodto(singoloAutore);
			listaDTO.add(autoreDto);
		}
		
		return listaDTO;
	}
	
	public Optional<AuthorDTO> getAuthor(Long id) {
		Optional<Author> autore = repo.findById(id);
		if(autore.isEmpty()) {
			return Optional.empty();
		}else {
			return Optional.of(fromEntityTodto(autore.get()));
		}
	}
	
	public boolean updateAuthor(AuthorDTO autoreDTO, Long id) {
		Optional<Author> autoreTrovato = repo.findById(id);
		
		if(autoreTrovato.isPresent()) {
			Author autore= fromDTOtoEntity(autoreDTO);
			autoreTrovato.get().setNome(autore.getNome());
			autoreTrovato.get().setCognome(autore.getCognome());
			autoreTrovato.get().setEmail(autore.getEmail());
			autoreTrovato.get().setDataNascita(autore.getDataNascita());
			autoreTrovato.get().setAvatar(autore.getAvatar());
			return true;
		}else {
			return false;
		}
	}
	
	public void deleteAuthor(Long id) throws EpicodeException {
		Optional<Author> autoreTrovato = repo.findById(id);
	
		if(autoreTrovato.isPresent()) {
			repo.delete(autoreTrovato.get());
		}else {
			throw new EpicodeException("Autore da modificare non presente nel sistema");
		}
	}
	
	
	// Metodo di utility DTO -> ENTITY
	public Author fromDTOtoEntity(AuthorDTO dto) {
		Author autore = new Author();
		autore.setAvatar(dto.getAvatar());
		autore.setCognome(dto.getCognome());
		autore.setDataNascita(dto.getDataNascita());
		autore.setEmail(dto.getEmail());
		autore.setNome(dto.getNome());
		return autore;
		
	}
	
//  Metodo di utility ENTITY -> DTO
	public AuthorDTO fromEntityTodto(Author ent) {
		AuthorDTO autoreDto = new AuthorDTO();
		autoreDto.setAvatar(ent.getAvatar());
		autoreDto.setCognome(ent.getCognome());
		autoreDto.setDataNascita(ent.getDataNascita());
		autoreDto.setEmail(ent.getEmail());
		autoreDto.setNome(ent.getNome());
		return autoreDto;
		
	}
	
	
	
	
}
