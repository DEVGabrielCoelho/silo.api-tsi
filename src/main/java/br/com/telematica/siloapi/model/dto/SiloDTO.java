package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.Silo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Silo")
public class SiloDTO extends Codigo {

	@Schema(description = "Tipo do Silo", example = "1", nullable = false)
	private TipoSiloDTO tipoSilo;
	@Schema(description = "Código Planta", example = "1", nullable = true)
	private PlantaDTO planta;
	@Schema(description = "Nome", example = "Silo 1", nullable = true)
	private String nome;

	public SiloDTO() {
	}

	public SiloDTO(Silo entity) {
		this.setCodigo(entity.getSilcod());
		this.tipoSilo = new TipoSiloDTO(entity.getTipoSilo());
		this.planta = new PlantaDTO(entity.getPlanta());
		this.nome = entity.getSilnom();
	}

	public SiloDTO(Long codigo, TipoSiloDTO tipoSilo, PlantaDTO planta, String nome) {
		super(codigo);
		this.tipoSilo = tipoSilo;
		this.planta = planta;
		this.nome = nome;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SiloDTO [");
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

	public TipoSiloDTO getTipoSilo() {
		return tipoSilo;
	}

	public void setTipoSilo(TipoSiloDTO tipoSilo) {
		this.tipoSilo = tipoSilo;
	}

	public PlantaDTO getPlanta() {
		return planta;
	}

	public void setPlanta(PlantaDTO planta) {
		this.planta = planta;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
