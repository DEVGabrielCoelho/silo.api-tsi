package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.UsuarioEntity;

public class UsuarioDTO extends Codigo {

	private String login;
	private String senha;
	private String email;
	private PerfilDTO perfil;

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

	public PerfilDTO getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilDTO perfil) {
		this.perfil = perfil;
	}

	public static String consultaPagable(String value) {
		switch (value) {
		case "codigo":
			return "usucod";
		case "login":
			return "usulog";
		case "senha":
			return "ususen";
		case "email":
			return "usuema";
		default:
			throw new IllegalArgumentException("Unexpected value: " + value);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuarioDTO [");
		if (login != null) {
			builder.append("login=").append(login).append(", ");
		}
		if (senha != null) {
			builder.append("senha=").append(senha).append(", ");
		}
		if (email != null) {
			builder.append("email=").append(email).append(", ");
		}
		if (perfil != null) {
			builder.append("perfil=").append(perfil).append(", ");
		}
		if (getCodigo() != null) {
			builder.append("getCodigo()=").append(getCodigo());
		}
		builder.append("]");
		return builder.toString();
	}

	public UsuarioDTO(Long codigo, String login, String senha, String email, PerfilDTO perfil) {
		super(codigo);
		this.login = login;
		this.senha = senha;
		this.email = email;
		this.perfil = perfil;
	}

	public UsuarioDTO(UsuarioEntity user) {
		super();
		this.setCodigo(user.getUsucod());
		this.login = user.getUsulog();
		this.senha = user.getUsusen();
		this.email = user.getUsuema();
		this.perfil = new PerfilDTO(user.getPerfil());
	}

	public UsuarioDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsuarioDTO(Long codigo) {
		super(codigo);
		// TODO Auto-generated constructor stub
	}

}
