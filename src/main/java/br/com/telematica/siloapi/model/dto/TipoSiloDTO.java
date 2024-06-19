package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.TipoSilo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo Silo")
public class TipoSiloDTO extends Codigo {

	@Schema(description = "Nome", example = "Tipo 1", required = true)
	private String nome;

	@Schema(description = "Descrição", example = "Tipo 1", required = true)
	private String descricao;

	public TipoSiloDTO() {
	}

	public TipoSiloDTO(Long codigo, String nome, String descricao) {
		super(codigo);
		this.nome = nome;
		this.descricao = descricao;
	}

	public TipoSiloDTO(TipoSilo entity) {
		this.setCodigo(entity.getTsicod());
		this.descricao = entity.getTsides();
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
		builder.append("TipoSiloDTO [");
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
