package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.Medicao;
import br.com.telematica.siloapi.utils.Utils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Medicão")
public class MedicaoDTO {

	@NotBlank
	@Schema(description = "data", example = "2021-08-01T00:00:00.000Z")
	private String data;
	@NotBlank
	@Schema(description = "modulo", example = "1")
	private SiloModuloDTO modulo;
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

	public MedicaoDTO() {
	}
	
	public static String filtrarDirecao(String str) {
		switch (str.toUpperCase()) {
		case "DATA" -> {
			return "msidth";
		}
		case "MODULO" -> {
			return "smocod";
		}
		default -> throw new AssertionError();
		}
	}

	public MedicaoDTO(String data, SiloModuloDTO modulo, Double umidade, Double ana, Double barometro, Double temperatura, Double distancia) {
		this.data = data;
		this.modulo = modulo;
		this.umidade = umidade;
		this.ana = ana;
		this.barometro = barometro;
		this.temperatura = temperatura;
		this.distancia = distancia;
	}

	public MedicaoDTO(Medicao medEntity) {
		this.data = Utils.sdfDateforString(medEntity.getMsidth());
		this.modulo = new SiloModuloDTO(medEntity.getSilomodulo());
		this.umidade = medEntity.getMsiumi();
		this.ana = medEntity.getMsiana();
		this.barometro = medEntity.getMsibar();
		this.temperatura = medEntity.getMsitem();
		this.distancia = medEntity.getMsidis();
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public SiloModuloDTO getModulo() {
		return modulo;
	}

	public void setModulo(SiloModuloDTO modulo) {
		this.modulo = modulo;
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
		builder.append("MedicaoDTO [data=");
		builder.append(data);
		builder.append(", modulo=");
		builder.append(modulo);
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
