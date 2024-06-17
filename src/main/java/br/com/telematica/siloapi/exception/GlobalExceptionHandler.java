package br.com.telematica.siloapi.exception;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.telematica.siloapi.utils.Utils;
import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// Retorno para 200 sem dados ou não encontrado
	@ExceptionHandler(EntityNotFoundException.class)
	public static ResponseEntity<Object> handleResponse200(EntityNotFoundException ex) {
		log.info("EntityNotFoundException: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	// Retorno para 400 erro
	@ExceptionHandler(IOException.class)
	public static ResponseEntity<ResponseGlobalModel> handleError400(IOException ex) {
		log.error("IOException: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.responseMessageError(ex.getMessage()));
	}

	// Retorno para 400 erro
	@ExceptionHandler(NoSuchAlgorithmException.class)
	public static ResponseEntity<ResponseGlobalModel> handleError400(NoSuchAlgorithmException ex) {
		log.error("IOException: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.responseMessageError(ex.getMessage()));
	}

	// Retorno para 400 erro
	@ExceptionHandler(NullPointerException.class)
	public static ResponseEntity<ResponseGlobalModel> handleError400(NullPointerException ex) {
		log.error("IOException: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utils.responseMessageError(ex.getMessage()));
	}

	// Retorno para 401 erro na autenticação ou falha no token
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<ResponseGlobalModel> handleTokenExpiredException(TokenExpiredException ex) {
		log.error("TokenExpiredException: ", ex);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Utils.responseMessageError(ex.getMessage()));
	}

	// Retorno para 401 erro na autenticação ou falha no token
	@ExceptionHandler(JWTVerificationException.class)
	public ResponseEntity<ResponseGlobalModel> handleJWTVerificationException(JWTVerificationException ex) {
		log.error("JWTVerificationException: ", ex);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Utils.responseMessageError(ex.getMessage()));
	}

	// Retorno para 401 erro na autenticação ou falha no token
	@ExceptionHandler(JWTCreationException.class)
	public ResponseEntity<ResponseGlobalModel> handleJWTCreationException(JWTCreationException ex) {
		log.error("JWTCreationException: ", ex);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Utils.responseMessageError(ex.getMessage()));
	}

	// Retorno para 401 erro na autenticação ou falha no token
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ResponseGlobalModel> handleAccessDeniedException(AccessDeniedException ex) {
		log.error("AccessDeniedException: ", ex);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Utils.responseMessageError(ex.getMessage()));
	}

	// Retorno para 403 mensagem de abrangencia
	@ExceptionHandler(RuntimeException.class)
	public static ResponseEntity<Object> handleError403(RuntimeException ex) {
		log.error("RuntimeException: " + ex.getMessage());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Utils.responseMessageError(ex.getMessage()));
	}

//	// Retorno para 500 mensagem de erro
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ResponseGlobalModel> handleGenericException(Exception ex) {
//		log.error("Exception: ", ex);
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Utils.responseMessageError("Internal server error"));
//	}

	@ExceptionHandler(IllegalArgumentException.class)
	public void handleIllegalArgumentException(IllegalArgumentException ex) {
		log.error("IllegalArgumentException: " + ex.getMessage());
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public void handleUsernameNotFoundException(UsernameNotFoundException ex) {
		log.error("UsernameNotFoundException: " + ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public void handleSecurityException(Exception ex) {
		log.error("Exception: ", ex);
	}

	@ExceptionHandler(SignatureException.class)
	public void handleSignatureException(SignatureException ex) {
		log.error("SignatureException: " + ex.getMessage());
	}

	@ExceptionHandler(AssertionError.class)
	public void handleAssertionError(AssertionError ex) {
		log.error("AssertionError: " + ex.getMessage());
	}

	@ExceptionHandler(ParseException.class)
	public void handleParseException(ParseException ex) {
		log.error("ParseException: " + ex.getMessage());
	}

	@ExceptionHandler(JsonMappingException.class)
	public void handleJsonMappingException(JsonMappingException ex) {
		log.error("JsonMappingException: " + ex.getMessage());
	}

	@ExceptionHandler(JsonProcessingException.class)
	public void handleJsonProcessingException(JsonProcessingException ex) {
		log.error("JsonProcessingException: " + ex.getMessage());
	}

}