package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.TipoSiloEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo Silo")
public class TipoSiloDTO extends Codigo {

	@Schema(description = "Código da Empresa", example = "1", required = true)
	private Long empresa;
	@Schema(description = "Descrição", example = "Tipo 1", required = true)
	private String descricao;

	public TipoSiloDTO() {
	}

	public TipoSiloDTO(Long codigo, Long empresa, String descricao) {
		super(codigo);
		this.empresa = empresa;
		this.descricao = descricao;
	}

	public TipoSiloDTO(TipoSiloEntity entity) {
		this.setCodigo(entity.getTsicod());
		this.empresa = entity.getEmpcod();
		this.descricao = entity.getTsides();
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
		builder.append("TipoSiloDTO [codigo=");
		builder.append(getCodigo());
		builder.append("empresa=");
		builder.append(empresa);
		builder.append(", descricao=");
		builder.append(descricao);
		builder.append("]");
		return builder.toString();
	}

}
