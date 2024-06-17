package br.com.telematica.siloapi.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Modelo de Medição")
public class MedicaoDeviceModel {

	@NotBlank
	@Schema(name = "numeroSerie", description = "Numero de Série", example = "N123124", format = "String")
	private String numeroSerie;
	@NotBlank
	@Schema(name = "dataInicio", description = "Data Inicio", example = "2024-05-22T10:05:01.001", format = "String")
	private String dataInicio;
	@NotBlank
	@Schema(name = "dataFim", description = "Data Fim", example = "2024-05-22T10:05:01.010", format = "String")
	private String dataFim;
	@NotBlank
	@Schema(name = "medicao", description = "Lista de Medições Aferidas", example = "[{\"1\": 93,\"2\": 96,\"3\": 50,\"4\": 15,\"5\": 27,\"6\": 29,\"7\": 29,\"8\": 92,\"data\": \"2024-05-22T10:05:01.001\"}]", format = "String")
	private List<Object> medicao;

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public List<Object> getMedicao() {
		return medicao;
	}

	public void setMedicao(List<Object> medicao) {
		this.medicao = medicao;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MedicaoModel [");
		if (numeroSerie != null) {
			builder.append("numeroSerie=").append(numeroSerie).append(", ");
		}
		if (dataInicio != null) {
			builder.append("dataInicio=").append(dataInicio).append(", ");
		}
		if (dataFim != null) {
			builder.append("dataFim=").append(dataFim).append(", ");
		}
		if (medicao != null) {
			builder.append("medicao=").append(medicao);
		}
		builder.append("]");
		return builder.toString();
	}

	public MedicaoDeviceModel(String numeroSerie, String dataInicio, String dataFim, List<Object> medicao) {
		super();
		this.numeroSerie = numeroSerie;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.medicao = medicao;
	}

	public MedicaoDeviceModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
