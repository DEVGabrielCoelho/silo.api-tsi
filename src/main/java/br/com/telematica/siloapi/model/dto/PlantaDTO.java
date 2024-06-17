package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.Planta;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PlantaDTO", description = "Objeto de transferência de dados de Planta")
public class PlantaDTO extends Codigo {

	@Schema(description = "Código da empresa", example = "1", nullable = false)
	private Long codigoEmpresa;
	@Schema(description = "Nome da planta", example = "Planta 1", nullable = false)
	private String nome;

	public PlantaDTO() {
	}

	public PlantaDTO(Long codigo, Long codigoEmpresa, String nome) {
		super(codigo);
		this.codigoEmpresa = codigoEmpresa;
		this.nome = nome;
	}

	public PlantaDTO(Planta entity) {
		super();
		this.setCodigo(entity.getPlacod());
		this.codigoEmpresa = entity.getEmpcod();
		this.nome = entity.getPlanom();
	}

	public Long getCodigoEmpresa() {
		return codigoEmpresa;
	}

	public void setCodigoEmpresa(Long codigoEmpresa) {
		this.codigoEmpresa = codigoEmpresa;
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
		builder.append("PlantaDTO [codigo=");
		builder.append(getCodigo());
		builder.append(", codigoEmpresa=");
		builder.append(codigoEmpresa);
		builder.append(", nome=");
		builder.append(nome);
		builder.append("]");
		return builder.toString();
	}

}
