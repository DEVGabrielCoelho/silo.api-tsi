package br.com.telematica.siloapi.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PlantaDTO", description = "Objeto de transferência de dados de Planta")
public class PlantaModel {

	@Schema(description = "Código da empresa", example = "1", nullable = false)
	private Long empresa;
	@Schema(description = "Nome da planta", example = "Planta 1", nullable = false)
	private String nome;

	public PlantaModel() {
	}

	public PlantaModel(Long empresa, String nome) {
		this.empresa = empresa;
		this.nome = nome;
	}

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlantaModel [");
		if (empresa != null) {
			builder.append("empresa=").append(empresa).append(", ");
		}
		if (nome != null) {
			builder.append("nome=").append(nome);
		}
		builder.append("]");
		return builder.toString();
	}

}
