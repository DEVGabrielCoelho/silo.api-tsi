package br.com.telematica.siloapi.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "MOdelo de Usuário.")
public class UsuarioModel {
	@Schema(name = "login", description = "Cadastro do login.", example = "admin", format = "String", nullable = false)
	private String login;
	@Schema(name = "senha", description = "Cadastro da senha.", example = "admin", format = "String", nullable = false)
	private String senha;
	@Schema(name = "email", description = "Cadastro do email.", example = "email@email.com", format = "String", nullable = false)
	private String email;
	@Schema(name = "nivelAcesso", description = "Descrição do Nivel de Acesso", example = "ADMIN", format = "String", nullable = false)
	private String nivelAcesso;
	@Schema(name = "permissao", description = "Lista dos níveis de permissão. Permissões: BARRAGEM, CANAL, EMPRESA, PENDENCIA, FIRMWARE, LOGGER, MEDICAO, AUDIO, SIRENE, MODULO, USUARIO", example = "[{\"descricao\": \"BARRAGEM\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1},{\"descricao\": \"CANAL\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1},{\"descricao\": \"EMPRESA\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1},{\"descricao\": \"MEDICAO\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1},{\"descricao\": \"AUDIO\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1},{\"descricao\": \"SIRENE\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1},{\"descricao\": \"MODULO\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1},{\"descricao\": \"USUARIO\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1},{\"descricao\": \"LOGGER\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1},{\"descricao\": \"PENDENCIA\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1},{\"descricao\": \"FIRMWARE\",\"get\": 1,\"post\": 1,\"put\": 1,\"delete\": 1}]", nullable = false)
	private List<PermissaoModel> permissao;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public List<PermissaoModel> getPermissao() {
		return permissao;
	}

	public void setPermissao(List<PermissaoModel> permissao) {
		this.permissao = permissao;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CadastroModel [");
		if (login != null) {
			builder.append("login=").append(login).append(", ");
		}
		if (senha != null) {
			builder.append("senha=").append(senha).append(", ");
		}
		if (email != null) {
			builder.append("email=").append(email).append(", ");
		}
		if (nivelAcesso != null) {
			builder.append("nivelAcesso=").append(nivelAcesso).append(", ");
		}
		if (permissao != null) {
			builder.append("permissao=").append(permissao);
		}
		builder.append("]");
		return builder.toString();
	}

	public UsuarioModel(String login, String senha, String email, String nivelAcesso, List<PermissaoModel> permissao) {
		super();
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.nivelAcesso = nivelAcesso;
		this.permissao = permissao;
	}

	public UsuarioModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
