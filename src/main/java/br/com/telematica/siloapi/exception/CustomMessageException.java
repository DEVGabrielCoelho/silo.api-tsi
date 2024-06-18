package br.com.telematica.siloapi.exception;

import java.io.IOException;

import com.google.gson.Gson;

import jakarta.persistence.EntityNotFoundException;

public class CustomMessageException {

	public CustomMessageException() {
		// TODO Auto-generated constructor stub
	}

	public static IOException exceptionCodigoIOException(String acao, String local, Object codigo, Object object, Throwable throwable) throws IOException {
		throw new IOException("Erro ao " + acao + " em " + ",Código fornecido " + codigo + ", objeto " + new Gson().toJson(object), throwable);
	}

	public static IOException exceptionIOException(String acao, String local, Object object, Throwable throwable) throws IOException {
		throw new IOException("Erro ao " + acao + " em " + ", objeto " + new Gson().toJson(object), throwable);
	}

	public static EntityNotFoundException exceptionEntityNotFoundException(Object codigo, String local, Throwable throwable) {
		throw new EntityNotFoundException("Não foi possível encontrar em " + local + " com o CODIGO " + codigo + "  fornecido.");
	}

}
