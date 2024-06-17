package br.com.telematica.siloapi.model;

import br.com.telematica.siloapi.model.entity.SiloEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Silo")
public class SiloModel {

	@Schema(description = "Tipo do Silo", example = "1", nullable = false)
	private Long tipoSilo;
	@Schema(description = "Código Planta", example = "1", nullable = true)
	private Long planta;
	@Schema(description = "Nome", example = "Silo 1", nullable = true)
	private String nome;

	public SiloModel() {
	}

	public SiloModel(Long tipoSilo, Long planta, String nome) {
		this.tipoSilo = tipoSilo;
		this.planta = planta;
		this.nome = nome;
	}

	public SiloModel(SiloEntity entity) {
		this.tipoSilo = entity.getTsicod();
		this.planta = entity.getPlacod();
		this.nome = entity.getSilnom();
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
