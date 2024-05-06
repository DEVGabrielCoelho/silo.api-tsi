package br.com.telematica.siloapi.utils.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.utils.Utils;

public class MessageResponse {

	public static ResponseEntity<GenericResponseModel> sucess(Object object) {
		var response = new GenericResponseModel(200, "Requsição realizada com Sucesso.", Utils.sdfBaseDateforString(), object);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public static ResponseEntity<GenericResponseModel> create(Object object) {
		var response = new GenericResponseModel(201, "Registro criado com Sucesso.", Utils.sdfBaseDateforString(), object);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	public static ResponseEntity<GenericResponseModel> badRequest(String message) {
		var response = new GenericResponseModel(400, message, Utils.sdfBaseDateforString(), null);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	public static ResponseEntity<GenericResponseModel> notAutorize() {
		var response = new GenericResponseModel(401, "Não Autorizado.", Utils.sdfBaseDateforString(), null);
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	public static ResponseEntity<GenericResponseModel> notFound(String message) {
		String msg = message == null ? "Não encontrado." : message;
		var response = new GenericResponseModel(404, msg, Utils.sdfBaseDateforString(), null);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

}
