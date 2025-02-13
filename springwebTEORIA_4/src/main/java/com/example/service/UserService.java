package com.example.service;

import org.springframework.stereotype.Service;

import com.example.dto.UserDTO;
import com.example.model.User;



@Service
public class UserService {
	
	public static Long id= 1L;

	public User create(UserDTO user) {
		
		User utente= new User();
		utente.setId(id);
		utente.setEmail(user.getEmail());
		utente.setName(user.getName());
		utente.setSurname(user.getSurname());
		System.out.println("Utente creato correttamente");
		id++;
		return utente;
	}
	
}
