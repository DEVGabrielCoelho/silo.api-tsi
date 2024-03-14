package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.MedicaoEntity;
import br.com.telematica.siloapi.util.Utils;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Medicão")
public class MedicaoDTO {

	@Schema(description = "Data", example = "2021-08-01T00:00:00.000Z", required = true)
	private String dataMedicao;
	@Schema(description = "Código", example = "1", required = true)
	private Integer codigoSilo;
	@Schema(description = "Umidade", example = "1.0", required = false)
	private Double umidade;
	@Schema(description = "Ana", example = "1.0", required = false)
	private Double ana;
	@Schema(description = "Barometro", example = "1.0", required = false)
	private Double barometro;
	@Schema(description = "Temperatura", example = "1.0", required = false)
	private Double temperatura;
	@Schema(description = "Distancia", example = "1.0", required = false)
	private Double distancia;

	public MedicaoDTO() {
	}

	public MedicaoDTO(String dataMedicao, Integer codigoSilo, Double umidade, Double ana, Double barometro, Double temperatura, Double distancia) {
		this.dataMedicao = dataMedicao;
		this.codigoSilo = codigoSilo;
		this.umidade = umidade;
		this.ana = ana;
		this.barometro = barometro;
		this.temperatura = temperatura;
		this.distancia = distancia;
	}

	public MedicaoDTO(MedicaoEntity medEntity) {
		this.dataMedicao = Utils.sdfDateforString(medEntity.getMsidth());
		this.codigoSilo = medEntity.getSilcod();
		this.umidade = medEntity.getMsiumi();
		this.ana = medEntity.getMsiana();
		this.barometro = medEntity.getMsibar();
		this.temperatura = medEntity.getMsitem();
		this.distancia = medEntity.getMsidis();
	}

	public String getDataMedicao() {
		return dataMedicao;
	}

	public void setDataMedicao(String dataMedicao) {
		this.dataMedicao = dataMedicao;
	}

	public Integer getCodigoSilo() {
		return codigoSilo;
	}

	public void setCodigoSilo(Integer codigoSilo) {
		this.codigoSilo = codigoSilo;
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
		builder.append(", codigoSilo=");
		builder.append(codigoSilo);
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
