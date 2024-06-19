package br.com.telematica.siloapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Silo")
public class SiloModel {

	@NotNull(message = "O campo 'tipoSilo' é obrigatório e não pode estar nulo.")
	@Schema(description = "Tipo do Silo", example = "1", nullable = false)
	private Long tipoSilo;

	@NotNull(message = "O campo 'planta' é obrigatório e não pode estar nulo.")
	@Schema(description = "Código da Planta", example = "1", nullable = true)
	private Long planta;

	@NotBlank(message = "O campo 'nome' é obrigatório e não pode estar em branco.")
	@Schema(description = "Nome", example = "Silo 1", nullable = true)
	private String nome;

	public SiloModel() {
	}

	public SiloModel(Long tipoSilo, Long planta, String nome) {
		this.tipoSilo = tipoSilo;
		this.planta = planta;
		this.nome = nome;
	}

	public Long getTipoSilo() {
		return tipoSilo;
	}

	public void setTipoSilo(Long tipoSilo) {
		this.tipoSilo = tipoSilo;
	}

	public Long getPlanta() {
		return planta;
	}

	public void setPlanta(Long planta) {
		this.planta = planta;
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
		builder.append("SiloModel [");
		if (tipoSilo != null) {
			builder.append("tipoSilo=").append(tipoSilo).append(", ");
		}
		if (planta != null) {
			builder.append("planta=").append(planta).append(", ");
		}
		if (nome != null) {
			builder.append("nome=").append(nome);
		}
		builder.append("]");
		return builder.toString();
	}

}
