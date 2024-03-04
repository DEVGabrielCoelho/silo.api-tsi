package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.TipoSiloEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo Silo")
public class TipoSiloDTO {

	@Schema(description = "Código", example = "1", required = true)
	private Integer codigo;
	@Schema(description = "Código da Empresa", example = "1", required = true)
	private Integer empresa;
	@Schema(description = "Descrição", example = "Tipo 1", required = true)
	private String descricao;

	public TipoSiloDTO() {
	}

	public TipoSiloDTO(Integer codigo, Integer empresa, String descricao) {
		this.codigo = codigo;
		this.empresa = empresa;
		this.descricao = descricao;
	}

	public TipoSiloDTO(TipoSiloEntity entity) {
		this.codigo = entity.getTsicod();
		this.empresa = entity.getEmpcod();
		this.descricao = entity.getTsides();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Integer empresa) {
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
		builder.append("TipoSiloDTO [codigo=");
		builder.append(codigo);
		builder.append(", empresa=");
		builder.append(empresa);
		builder.append(", descricao=");
		builder.append(descricao);
		builder.append("]");
		return builder.toString();
	}

}
