package br.com.telematica.siloapi.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class PerfilModel {

	@NotBlank
	@Schema(name = "nome", description = "Nome da propriedade.", example = "nome", format = "string")
	private String nome;

	@Schema(name = "descricao", description = "Descrição para detalhes.", example = "Descrição", format = "string")
	private String descricao;

	@NotBlank
	@Schema(name = "permissoes", description = "Lista de permissões.", format = "string")
	private List<PermissaoModel> permissoes;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<PermissaoModel> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<PermissaoModel> permissoes) {
		this.permissoes = permissoes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PerfilModel [");
		if (nome != null) {
			builder.append("nome=").append(nome).append(", ");
		}
		if (descricao != null) {
			builder.append("descricao=").append(descricao);
		}
		if (permissoes != null) {
			builder.append("permissoes=").append(permissoes);
		}
		builder.append("]");
		return builder.toString();
	}

	public PerfilModel(@NotBlank String nome, String descricao, @NotBlank List<PermissaoModel> permissoes) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.permissoes = permissoes;
	}

	public PerfilModel() {
	}
}
