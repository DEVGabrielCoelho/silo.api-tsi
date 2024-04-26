package br.com.telematica.siloapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de Autorização")
public class AuthModel {

	@Schema(name = "login", description = "Login", example = "admin")
	private String login;
	@Schema(name = "senha", description = "Senha", example = "admin")
	private String senha;

	public AuthModel() {
	}

	public AuthModel(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuthModel [");
		if (login != null) {
			builder.append("login=").append(login).append(", ");
		}
		if (senha != null) {
			builder.append("senha=").append(senha);
		}
		builder.append("]");
		return builder.toString();
	}

}
