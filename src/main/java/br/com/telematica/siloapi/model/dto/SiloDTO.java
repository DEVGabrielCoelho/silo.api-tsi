package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.SiloEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Silo")
public class SiloDTO {

	@Schema(description = "Código", example = "1", required = true)
	private Integer codigo;
	@Schema(description = "Tipo do Silo", example = "1", required = false)
	private Integer tipoSilo;
	@Schema(description = "Código Planta", example = "1", required = true)
	private Integer codiPlanta;
	@Schema(description = "Nome", example = "Silo 1", required = true)
	private String nome;

	public SiloDTO() {
	}

	public SiloDTO(Integer codigo, Integer tipoSilo, Integer codiPlanta, String nome) {
		this.codigo = codigo;
		this.tipoSilo = tipoSilo;
		this.codiPlanta = codiPlanta;
		this.nome = nome;
	}

	public SiloDTO(SiloEntity entity) {
		this.codigo = entity.getSilcod();
		this.tipoSilo = entity.getTsicod();
		this.codiPlanta = entity.getPlacod();
		this.nome = entity.getSilnom();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getTipoSilo() {
		return tipoSilo;
	}

	public void setTipoSilo(Integer tipoSilo) {
		this.tipoSilo = tipoSilo;
	}

	public Integer getCodiPlanta() {
		return codiPlanta;
	}

	public void setCodiPlanta(Integer codiPlanta) {
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
		builder.append(codigo);
		builder.append(", tipoSilo=");
		builder.append(tipoSilo);
		builder.append(", codiPlanta=");
		builder.append(codiPlanta);
		builder.append(", nome=");
		builder.append(nome);
		builder.append("]");
		return builder.toString();
	}

}
