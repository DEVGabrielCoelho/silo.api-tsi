package br.com.telematica.siloapi.utils.message;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.utils.Utils;

@Component
public class MessageResponse {

	// Método para páginas
	public static <T> ResponseEntity<Page<T>> page(Page<T> page) {
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	// Método para sucesso
	public static <T> ResponseEntity<T> success(T object) {
		return new ResponseEntity<>(object, HttpStatus.OK);
	}

	// Método para criação
	public static <T> ResponseEntity<T> create(T object) {
		return new ResponseEntity<>(object, HttpStatus.CREATED);
	}

	// Método para bad request
	public static <T> ResponseEntity<T> badRequest(T message) {
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	// Método para não autorizado
	public static <T> ResponseEntity<T> notAuthorize(T message) {
		return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
	}

	// Método para não encontrado
	public static <T> ResponseEntity<T> notFound(T message) {
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	public static ResponseGlobalModel responseGlobalModelSucess(String message) {
		return new ResponseGlobalModel(true, message, Utils.newDateString());
	}

	public static ResponseGlobalModel responseGlobalModelError(String message) {
		return new ResponseGlobalModel(true, message, Utils.newDateString());
	}

}
