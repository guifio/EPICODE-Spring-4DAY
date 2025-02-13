package com.example.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.dto.UserDTO;
import com.example.model.User;
import com.example.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService service;

	@PostMapping(value="/register", produces = "application/json" )
	public ResponseEntity<?> saveUser(@RequestBody @Validated UserDTO user, BindingResult validazione) throws BadRequestException {
		
		if(validazione.hasErrors()) {
			// come visualizzare gli errori al client
			String messaggioErrore ="";
			
			for(ObjectError errore : validazione.getAllErrors()){
				messaggioErrore += errore.getDefaultMessage() +"\n";
			}
		
			return new ResponseEntity<>(messaggioErrore,HttpStatus.BAD_REQUEST);
		}else {
			User utente = service.create(user);
			return new ResponseEntity<>(utente,HttpStatus.OK);
		}
		
	}
	
	
}
