package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.TipoSilo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo Silo")
public class TipoSiloDTO extends Codigo {

	@Schema(description = "Código da Empresa", example = "1", required = true)
	private EmpresaDTO empresa;
	@Schema(description = "Descrição", example = "Tipo 1", required = true)
	private String descricao;

	public TipoSiloDTO() {
	}

	public TipoSiloDTO(Long codigo, EmpresaDTO empresa, String descricao) {
		super(codigo);
		this.empresa = empresa;
		this.descricao = descricao;
	}

	public TipoSiloDTO(TipoSilo entity) {
		this.setCodigo(entity.getTsicod());
		this.empresa = new EmpresaDTO(entity.getEmpresa());
		this.descricao = entity.getTsides();
	}

	public EmpresaDTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDTO empresa) {
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
