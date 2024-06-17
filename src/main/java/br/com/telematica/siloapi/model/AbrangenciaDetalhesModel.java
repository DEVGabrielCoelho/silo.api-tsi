package br.com.telematica.siloapi.model;

import com.fasterxml.jackson.databind.JsonNode;

import br.com.telematica.siloapi.model.enums.RecursoMapEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class AbrangenciaDetalhesModel {

	@NotBlank
	@Schema(name = "recurso", description = "Lista dos níveis de permissão. Permissões: BARRAGEM, CANAL, EMPRESA, PENDENCIA, FIRMWARE, LOGGER, MEDICAO, AUDIO, SIRENE, MODULO, USUARIO", example = "BARRAGEM")
	private RecursoMapEnum recurso;

	@NotBlank
	@Schema(name = "hierarquia", description = "Define o nível de hierarquia que a abrangência atinge. (0 = não, 1 = sim)", example = "0")
	private Integer hierarquia;

	@Schema(name = "dados", description = "Detalhes adicionais da abrangência em formato JSON.", example = "[1,2,3,4]")
	private JsonNode dados;

	public RecursoMapEnum getRecurso() {
		return recurso;
	}

	public void setRecurso(RecursoMapEnum recurso) {
		this.recurso = recurso;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbrangenciaDetalhesModel [");
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

	public AbrangenciaDetalhesModel(RecursoMapEnum recurso, Integer all, Integer hierarquia, JsonNode dados) {
		super();
		this.recurso = recurso;
		this.hierarquia = hierarquia;
		this.dados = dados;
	}

	public AbrangenciaDetalhesModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
