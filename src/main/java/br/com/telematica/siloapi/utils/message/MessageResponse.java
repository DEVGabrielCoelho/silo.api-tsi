package br.com.telematica.siloapi.utils.message;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.utils.Utils;

public class MessageResponse {

	public static <T> ResponseEntity<Page<T>> page(Page<T> page) {
		if (page == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	public static ResponseEntity<GenericResponseModel> sucess(Object object) {
		var response = new GenericResponseModel("Requsição realizada com Sucesso.", Utils.sdfBaseDateforString(), object);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public static ResponseEntity<GenericResponseModel> create(Object object) {
		var response = new GenericResponseModel("Registro criado com Sucesso.", Utils.sdfBaseDateforString(), object);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public static ResponseEntity<GenericResponseModel> badRequest(String message) {
		var response = new GenericResponseModel(message, Utils.sdfBaseDateforString(), null);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<GenericResponseModel> notAutorize() {
		var response = new GenericResponseModel("Não Autorizado.", Utils.sdfBaseDateforString(), null);
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	public static ResponseEntity<GenericResponseModel> notFound(String message) {
		String msg = message == null ? "Não encontrado." : message;
		var response = new GenericResponseModel(msg, Utils.sdfBaseDateforString(), null);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

}
