package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.PostDTO;
import com.example.model.Author;
import com.example.model.Post;
import com.example.repository.AuthorRepository;
import com.example.repository.PostRepository;

@Service
@Transactional
public class PostService {

	@Autowired
	PostRepository repo;
	
	@Autowired
	AuthorRepository autRepo;
	
	/**
	 * Metodo che riceve il DTO dal controller, ricerca l'autore, effettua il travaso dei dati da DTO a Entity.
	 * Affida l'autore legato al post e invia al repository l'entity Post finale.
	 * @param newPostDTO
	 * @return
	 */
	public String nuovoPost(PostDTO newPostDTO) {

		// recupero l'eventuale autore
		Optional<Author> autore = autRepo.findById(newPostDTO.getFk_autore());
		
		if(autore.isPresent()) {
		
			// travaso i dati dal DTO -> ENTITY
			Post postEntity = dto_entity(newPostDTO);
			
			// INSERISCO NELL'ENTITY ANCHE L'AUTORE RECUPERATO 
			postEntity.setAutore(autore.get());
			
			// Inserisco nel database l'Entity Post
			repo.save(postEntity);
			return "Nuovo post inserito correttamente";
		}else {
			throw new RuntimeException("L'autore del post non è stato inserito correttamente");
		}
	}
		
	public void popolaBLog(List<PostDTO> listaNuovaPost) {
		for(PostDTO singoloPost : listaNuovaPost) {
			Optional<Author> autore = autRepo.findById(singoloPost.getFk_autore());
			
			if(autore.isPresent()) {
				// Non consento la modifica della chiave primaria
				Post post = dto_entity(singoloPost);
				post.setAutore(autore.get());
				repo.save(post);
			}else {
				throw new RuntimeException();
			}

		}
	}
		
	
	/**
	 * Recupera tutti i post presenti nel sistema
	 * @param page : rende la lista di ritorno disponibile alla paginazione
	 * @return
	 */
	public Page<PostDTO> getAllPosts(Pageable page){
		
		Page<Post> listaPost = repo.findAll(page);
		
		// creo la lista DTO da inviare al cotroller per il client
		List<PostDTO> listaDTO = new ArrayList<>();
		
		// effettuo una scansione della lista di entity
		for(Post singoloPost : listaPost.getContent()) {
			
			// travaso entity -> dto per ciascun elemento
			PostDTO dto = entity_dto(singoloPost);
			
			// aggiungo l'elemento dto alla lista
			listaDTO.add(dto);
		}
		
		// Trasformo la lista DTO in una lista paginata
		Page<PostDTO> listaPage = new PageImpl<>(listaDTO);
		return listaPage;
	}
	
	/**
	 * Metodo che ritorna un oggetto di tipo PostDTO
	 * @param id
	 * @return
	 */
	public PostDTO vediPost(Long id) {
		
		Optional<Post> post = repo.findById(id);
		
		// Se il post richiesto è presente nel sistema
		if(post.isPresent()) {
			
			Post postRecuperato = post.get();
			
			PostDTO postDtoRecuperato = entity_dto(postRecuperato);
			
			return postDtoRecuperato;
		}else {
			throw new RuntimeException("Il post non è presente nel sistema");
		}
	}

	public void sostituisciPost(PostDTO postModificato, Long id) {
		Optional<Post> postTrovato = repo.findById(id);
		
		if(postTrovato.isPresent()) {
			postTrovato.get().setCategoria(postModificato.getCategoria());
			postTrovato.get().setContenuto(postModificato.getContenuto());
			postTrovato.get().setTitolo(postModificato.getTitolo());
			postTrovato.get().setTempoLettura(postModificato.getTempoLettura());
			postTrovato.get().setCover(postModificato.getCover());
		}else {
			throw new RuntimeException();
		}
	}

	public String cancellaPost(Long id) {
		repo.deleteById(id);
		return "Post cancellato corretamente";
	}

	
	
	// METODI DI UTILITIES : ENTITY -> DTO
	public PostDTO entity_dto(Post post) {
		if(post !=null) {
			PostDTO postDto = new PostDTO();
			postDto.setCategoria(post.getCategoria());
			postDto.setContenuto(post.getContenuto());
			postDto.setCover(post.getCover());
			postDto.setTempoLettura(post.getTempoLettura());
			postDto.setTitolo(post.getTitolo());
//			postDto.setFk_autore(post.getAutore().getId());
			return postDto;
		}
		
		throw new RuntimeException("Oggetto non identificato");
	}
	
	
	// METODI DI UTILITIES : DTO -> ENTITY
	public Post dto_entity(PostDTO postDto) {
		if(postDto !=null) {
			Post post = new Post();
			post.setCategoria(postDto.getCategoria());
			post.setContenuto(postDto.getContenuto());
			post.setCover(postDto.getCover());
			post.setTempoLettura(postDto.getTempoLettura());
			post.setTitolo(postDto.getTitolo());
			return post;
		}
		
		throw new RuntimeException("Oggetto non identificato");
	}
	
	
	
	

}
