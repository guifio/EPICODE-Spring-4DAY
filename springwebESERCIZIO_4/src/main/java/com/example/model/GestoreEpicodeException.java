package com.example.model;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GestoreEpicodeException extends ResponseEntityExceptionHandler{

	@ExceptionHandler(EpicodeException.class)
	protected ResponseEntity<Object> gestore(EpicodeException ex){
		
		BlogException error = new BlogException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		
		return costruisciResponse(error);
	}
	
	private ResponseEntity<Object> costruisciResponse(BlogException err){
		return new ResponseEntity<>(err,err.getStatoErrore());
	}
	
	
	
}
