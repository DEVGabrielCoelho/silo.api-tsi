package br.com.telematica.siloapi.utils.error;

import br.com.telematica.siloapi.util.Utils;

public class MessageResponse {

	public static GenericResponseModel sucessRequest200(String message, Integer value, Object object) {
		var response = new GenericResponseModel(200, message, Utils.sdfBaseDateforString(), value, object);
		return response;

	}

	public static GenericResponseModel exceptionRequest400(String message, Integer value, Object object) {
		var response = new GenericResponseModel(400, message, Utils.sdfBaseDateforString(), value, object);
		return response;
	}

	public static GenericResponseModel exceptionRequest500(String message, Integer value, Object object) {
		var response = new GenericResponseModel(500, message, Utils.sdfBaseDateforString(), value, object);
		return response;
	}

}
