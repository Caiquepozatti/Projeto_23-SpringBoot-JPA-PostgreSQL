package com.Projeto.demo.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.Projeto.demo.services.exceptions.DatabaseException;
import com.Projeto.demo.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;


@ControllerAdvice //A classe se torna um componente global que pode interceptar execeções lançadas em controladores e trata-las
public class ResourceExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class) //Para tratar a excessão específica
	public ResponseEntity<StandError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandError err = new StandError(Instant.now(),status.value(), error, e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class) //Para tratar a excessão específica
	public ResponseEntity<StandError> database(DatabaseException e, HttpServletRequest request){
		String error = "Database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandError err = new StandError(Instant.now(),status.value(), error, e.getMessage(),request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
}
