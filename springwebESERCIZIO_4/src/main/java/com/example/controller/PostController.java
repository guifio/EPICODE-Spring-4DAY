package com.example.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.example.dto.PostDTO;
import com.example.model.EpicodeException;
import com.example.model.Post;
import com.example.service.PostService;
import io.github.cdimascio.dotenv.Dotenv;

@RestController
@RequestMapping("/posts")
public class PostController {

	@Autowired
	PostService service;
	
	@Autowired
	Cloudinary cloud;

	@PostMapping("/nuovoPostCover")
	public ResponseEntity<String> newPostCover(@RequestPart("copertina") MultipartFile copertina, @RequestPart @Validated PostDTO postDto, BindingResult validation) {
		
		// La validazione contiene errori?
		if(validation.hasErrors()) {
			// come visualizzare gli errori al client
			String messaggioErrore ="";

			// Costruzione di un'unica stringa con tutti gli errori citati
			for(ObjectError errore : validation.getAllErrors()){
				messaggioErrore += errore.getDefaultMessage() +"\n";
			}

			return new ResponseEntity<>(messaggioErrore,HttpStatus.BAD_REQUEST);
		}else {
		
			String messaggio ="";
			HttpStatus stato;
			try {

				// Effettuo l'upload verso Cloudinary
				Uploader up= cloud.uploader();
				Map mappa = up.upload(copertina.getBytes(), ObjectUtils.emptyMap());

				// Recupero dalla mappa l'url di riferimento del file uploadato
				String urlImage=mappa.get("secure_url").toString();
				
				// setto l'url nel DTO destinato al service
				postDto.setCover(urlImage);	
				
				// Invio al service il DTO recuperato dal client
				service.nuovoPost(postDto);
				
				return new ResponseEntity<>("Nuovo post creato correttamente", HttpStatus.CREATED);
			} catch (Exception e) {
				return new ResponseEntity<>("Errore nel sistema", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

	
	@PostMapping("/nuovoPost")
	public ResponseEntity<String> newPost(@RequestBody @Validated PostDTO post, BindingResult validation) {
	
		if(validation.hasErrors()) {
			// come visualizzare gli errori al client
			String messaggioErrore ="ERRORI DI VALIDAZIONE :\n";

			for(ObjectError errore : validation.getAllErrors()){
				messaggioErrore += errore.getDefaultMessage() +"\n";
			}

			return new ResponseEntity<>(messaggioErrore,HttpStatus.BAD_REQUEST);
		}else {
			// Impostazione delle credenziali Cloudi			String messaggio ="";
			String messaggio = service.nuovoPost(post);
			return new ResponseEntity<>(messaggio, HttpStatus.CREATED);
		}
	}
	
	//	POST /blogPosts/popolaBlog => popola il blog con una prima lista di post
	@PostMapping("/popolaBlog")
	public ResponseEntity<?> addPosts(@RequestPart("copertine") List<MultipartFile> copertine, @RequestPart @Validated List<PostDTO> listaPostDto, BindingResult validation) {
		if(validation.hasErrors()) {
			// come visualizzare gli errori al client
			String messaggioErrore ="";

			for(ObjectError errore : validation.getAllErrors()){
				messaggioErrore += errore.getDefaultMessage() +"\n";
			}

			return new ResponseEntity<>(messaggioErrore,HttpStatus.BAD_REQUEST);
		}else {
			
			// Impostazione connessione con Cloudinary
			Dotenv dotenv = Dotenv.load();
			Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
			
			for(int i=0,j=0; i<listaPostDto.size() && j<copertine.size(); i++,j++) {
				try {
					PostDTO post = listaPostDto.get(i);
					MultipartFile cover = copertine.get(j);
					Map mappa = cloudinary.uploader().upload(cover.getBytes(), ObjectUtils.emptyMap());
					String urlImage=mappa.get("secure_url").toString();
					post.setCover(urlImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			service.popolaBLog(listaPostDto);	
			return new ResponseEntity<>("I post sono stati inseriti correttamente nel sistema",HttpStatus.ACCEPTED);
		}
	}


	@GetMapping(value="/vediPosts", produces = "application/json")
	public ResponseEntity<Page<PostDTO>> allPosts(Pageable page) {
		Page<PostDTO> posts = service.getAllPosts(page);
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}

	//	GET /blogPosts /123 => ritorna un singolo blog post
	@GetMapping("/vediPost/{id}")
	public PostDTO vediPost(@PathVariable Long id) {
		PostDTO postRecuperato = service.vediPost(id);
		return postRecuperato;
	}

	//	PUT /blogPosts /123 => modifica lo specifico blog post
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<?> modificaPost(@RequestPart("cover") MultipartFile cover, @RequestPart @Validated PostDTO postModificato, BindingResult validation, @PathVariable Long id) {
		if(validation.hasErrors()) {
			// come visualizzare gli errori al client
			String messaggioErrore ="";

			for(ObjectError errore : validation.getAllErrors()){
				messaggioErrore += errore.getDefaultMessage() +"\n";
			}
			return new ResponseEntity<>(messaggioErrore,HttpStatus.BAD_REQUEST);
		}else {
			try {
				// Impostazione connessione con Cloudinary
				Dotenv dotenv = Dotenv.load();
				Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
				
				Map mappa = cloudinary.uploader().upload(cover.getBytes(), ObjectUtils.emptyMap());
				String urlImage=mappa.get("secure_url").toString();
				postModificato.setCover(urlImage);
				
				service.sostituisciPost(postModificato, id);
				return new ResponseEntity<>("Il post è stato modificato correttamente", HttpStatus.OK);
			} catch (IOException e) {
				throw new EpicodeException("Il non è stato modificato correttamente.."); 
			}
		}

	}



	//  DELETE /blogPosts /123 => cancella lo specifico blog post
	@DeleteMapping("/delete/{id}")
	public String cancellaPost(@PathVariable Long id) {
		return service.cancellaPost(id);
	}




}
