package br.com.telematica.siloapi.exception;

public record ResponseGlobalModel(boolean error, String messsage, String date) {
}
