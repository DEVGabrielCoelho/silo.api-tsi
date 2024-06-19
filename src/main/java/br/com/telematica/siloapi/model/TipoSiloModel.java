package br.com.telematica.siloapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Tipo Silo")
public class TipoSiloModel {

	@NotBlank
	@Schema(description = "Nome do tipo de Silo", example = "Tipo 1")
	private String nome;
	@NotBlank
	@Schema(description = "Descrição", example = "Tipo 1 descrição")
	private String descricao;

	public TipoSiloModel() {
	}

	public TipoSiloModel(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipoSiloModel [");
		if (nome != null) {
			builder.append("nome=").append(nome).append(", ");
		}
		if (descricao != null) {
			builder.append("descricao=").append(descricao);
		}
		builder.append("]");
		return builder.toString();
	}

}
