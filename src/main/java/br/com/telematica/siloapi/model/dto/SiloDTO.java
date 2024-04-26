package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.SiloEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Silo")
public class SiloDTO extends Codigo {

	@Schema(description = "Tipo do Silo", example = "1", nullable = false)
	private Long tipoSilo;
	@Schema(description = "Código Planta", example = "1", nullable = true)
	private Long codiPlanta;
	@Schema(description = "Nome", example = "Silo 1", nullable = true)
	private String nome;

	public SiloDTO() {
	}

	public SiloDTO(Long codigo, Long tipoSilo, Long codiPlanta, String nome) {
		super(codigo);
		this.tipoSilo = tipoSilo;
		this.codiPlanta = codiPlanta;
		this.nome = nome;
	}

	public SiloDTO(SiloEntity entity) {
		this.tipoSilo = entity.getTsicod();
		this.codiPlanta = entity.getPlacod();
		this.nome = entity.getSilnom();
	}

	public Long getTipoSilo() {
		return tipoSilo;
	}

	public void setTipoSilo(Long tipoSilo) {
		this.tipoSilo = tipoSilo;
	}

	public Long getCodiPlanta() {
		return codiPlanta;
	}

	public void setCodiPlanta(Long codiPlanta) {
		this.codiPlanta = codiPlanta;
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
		builder.append("SiloDTO [codigo=");
		builder.append(getCodigo());
		builder.append(" tipoSilo=");
		builder.append(tipoSilo);
		builder.append(", codiPlanta=");
		builder.append(codiPlanta);
		builder.append(", nome=");
		builder.append(nome);
		builder.append("]");
		return builder.toString();
	}

}
