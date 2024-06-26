package br.com.telematica.siloapi.model.dto;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.telematica.siloapi.model.entity.AbrangenciaDetalhes;
import br.com.telematica.siloapi.utils.JsonNodeConverter;

public class AbrangenciaDetalhesDTO {

	private String recurso;

	private Integer hierarquia;

	private JsonNode dados;

	public String getRecurso() {
		return recurso;
	}

	public Integer getHierarquia() {
		return hierarquia;
	}

	public void setHierarquia(Integer hierarquia) {
		this.hierarquia = hierarquia;
	}

	public JsonNode getDados() {
		return dados;
	}

	public void setDados(JsonNode dados) {
		this.dados = dados;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbrangenciaDetalhes [");
		if (recurso != null) {
			builder.append("recurso=").append(recurso).append(", ");
		}

		if (hierarquia != null) {
			builder.append("hierarquia=").append(hierarquia).append(", ");
		}
		if (dados != null) {
			builder.append("dados=").append(dados);
		}
		builder.append("]");
		return builder.toString();
	}

	public AbrangenciaDetalhesDTO(String recurso, Integer hierarquia, JsonNode dados) {
		super();
		this.recurso = recurso;
		this.hierarquia = hierarquia;
		this.dados = dados;
	}

	public AbrangenciaDetalhesDTO(AbrangenciaDetalhes abrDetalhes) {
		super();
		JsonNodeConverter jsonNode = new JsonNodeConverter();

		this.recurso = abrDetalhes.getRecurso().getRecnom();
		this.hierarquia = abrDetalhes.getAbdhie();
		this.dados = jsonNode.convertToEntityAttribute(abrDetalhes.getAbddat());
	}

	public AbrangenciaDetalhesDTO() {
		super();
		
	}

}
