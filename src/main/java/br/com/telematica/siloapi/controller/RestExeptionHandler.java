package br.com.telematica.siloapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.utils.message.MessageResponse;

@RestControllerAdvice
public class RestExeptionHandler {

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<GenericResponseModel> handleUnauthorizedException(AccessDeniedException ex) {
		return MessageResponse.notAutorize();
	}

}
