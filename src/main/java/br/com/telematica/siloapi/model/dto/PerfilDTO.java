package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.PerfilEntity;

public class PerfilDTO extends Codigo {

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PerfilDTO [");
		if (getCodigo() != null) {
			builder.append("codigo=").append(getCodigo());
		}
		if (descricao != null) {
			builder.append("descricao=").append(descricao).append(", ");
		}
		builder.append("]");
		return builder.toString();
	}

	public PerfilDTO(Long codigo, String descricao) {
		super(codigo);
		this.descricao = descricao;
	}

	public PerfilDTO(PerfilEntity perfil) {
		super();
		this.setCodigo(perfil.getPercod());
		this.descricao = perfil.getPerdes();
	}

	public PerfilDTO() {
		super();
	}

}
