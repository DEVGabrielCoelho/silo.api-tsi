package br.com.telematica.siloapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Tipo Silo")
public class TipoSiloModel {

	@NotBlank
	@Schema(description = "Código da Empresa", example = "1")
	private Long empresa;
	@NotBlank
	@Schema(description = "Descrição", example = "Tipo 1")
	private String descricao;

	public TipoSiloModel() {
	}

	public TipoSiloModel(Long empresa, String descricao) {
		this.empresa = empresa;
		this.descricao = descricao;
	}

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
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
		if (empresa != null) {
			builder.append("empresa=").append(empresa).append(", ");
		}
		if (descricao != null) {
			builder.append("descricao=").append(descricao);
		}
		builder.append("]");
		return builder.toString();
	}

}
