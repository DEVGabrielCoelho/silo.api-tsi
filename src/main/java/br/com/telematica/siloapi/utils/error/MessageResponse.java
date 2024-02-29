package br.com.telematica.siloapi.utils.error;

import java.util.Date;

public class MessageResponse {

	public static GenericResponseModel sucessRequest200(String message, Integer value, Object object) {
		var response = new GenericResponseModel(200, message, new Date(), value, object);
		return response;

	}

	public static GenericResponseModel exceptionRequest400(String message, Integer value, Object object) {
		var response = new GenericResponseModel(400, message, new Date(), value, object);
		return response;
	}

	public static GenericResponseModel exceptionRequest500(String message, Integer value, Object object) {
		var response = new GenericResponseModel(500, message, new Date(), value, object);
		return response;
	}

}
