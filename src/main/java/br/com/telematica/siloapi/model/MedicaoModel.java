package br.com.telematica.siloapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Medicão")
public class MedicaoModel {

	@NotBlank
	@Schema(description = "Data", example = "2021-08-01T00:00:00.000Z")
	private String dataMedicao;
	@NotBlank
	@Schema(description = "Código", example = "1")
	private Long silo;
	@NotBlank
	@Schema(description = "Umidade", example = "1.0")
	private Double umidade;
	@NotBlank
	@Schema(description = "Ana", example = "1.0")
	private Double ana;
	@NotBlank
	@Schema(description = "Barometro", example = "1.0")
	private Double barometro;
	@NotBlank
	@Schema(description = "Temperatura", example = "1.0")
	private Double temperatura;
	@NotBlank
	@Schema(description = "Distancia", example = "1.0")
	private Double distancia;

	public MedicaoModel() {
	}

	public MedicaoModel(String dataMedicao, Long silo, Double umidade, Double ana, Double barometro, Double temperatura, Double distancia) {
		this.dataMedicao = dataMedicao;
		this.silo = silo;
		this.umidade = umidade;
		this.ana = ana;
		this.barometro = barometro;
		this.temperatura = temperatura;
		this.distancia = distancia;
	}

	public String getDataMedicao() {
		return dataMedicao;
	}

	public void setDataMedicao(String dataMedicao) {
		this.dataMedicao = dataMedicao;
	}

	public Long getSilo() {
		return silo;
	}

	public void setSilo(Long silo) {
		this.silo = silo;
	}

	public Double getUmidade() {
		return umidade;
	}

	public void setUmidade(Double umidade) {
		this.umidade = umidade;
	}

	public Double getAna() {
		return ana;
	}

	public void setAna(Double ana) {
		this.ana = ana;
	}

	public Double getBarometro() {
		return barometro;
	}

	public void setBarometro(Double barometro) {
		this.barometro = barometro;
	}

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MedicaoDTO [dataMedicao=");
		builder.append(dataMedicao);
		builder.append(", silo=");
		builder.append(silo);
		builder.append(", umidade=");
		builder.append(umidade);
		builder.append(", ana=");
		builder.append(ana);
		builder.append(", barometro=");
		builder.append(barometro);
		builder.append(", temperatura=");
		builder.append(temperatura);
		builder.append(", distancia=");
		builder.append(distancia);
		builder.append("]");
		return builder.toString();
	}

}
