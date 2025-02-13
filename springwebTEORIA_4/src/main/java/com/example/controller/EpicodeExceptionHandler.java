package com.example.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.exception.EpicodeException;
import com.example.model.ApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class EpicodeExceptionHandler extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	@ExceptionHandler(EpicodeException.class)
	protected ResponseEntity<Object> gestioneErrori(EpicodeException ex){
		ApiError error = new ApiError(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		// chiamiamo il metodo private che genere la ResponseEntity di ritorno
		return new ResponseEntity<>(error, error.getStato());
	}
	
}
