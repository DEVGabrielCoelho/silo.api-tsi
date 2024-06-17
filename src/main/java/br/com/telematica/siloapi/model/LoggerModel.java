package br.com.telematica.siloapi.model;

import br.com.telematica.siloapi.model.enums.LoggerEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Modelo de registro de Logger.")
public class LoggerModel {

	@NotBlank
	@Schema(name = "data", description = "Data do Log", example = "2024-05-22T10:05:01.001", format = "String")
	private String data;
	@NotBlank
	@Schema(name = "numSerie", description = "Número de Série", example = "N123124", format = "String")
	private String numSerie;
	@NotBlank
	@Schema(name = "tipoLogger", description = "Modelos de Loggers", example = "INFO", format = "String")
	private LoggerEnum tipoLogger;
	@NotBlank
	@Schema(name = "mensagem", description = "Mensagem do Logger", example = "Mensagem Teste", format = "String")
	private String mensagem;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public LoggerEnum getTipoLogger() {
		return tipoLogger;
	}

	public void setTipoLogger(LoggerEnum tipoLogger) {
		this.tipoLogger = tipoLogger;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoggerModel [");
		if (data != null) {
			builder.append("data=").append(data).append(", ");
		}
		if (numSerie != null) {
			builder.append("numSerie=").append(numSerie).append(", ");
		}
		if (tipoLogger != null) {
			builder.append("tipoLogger=").append(tipoLogger).append(", ");
		}
		if (mensagem != null) {
			builder.append("mensagem=").append(mensagem);
		}
		builder.append("]");
		return builder.toString();
	}

	public LoggerModel(String data, String numSerie, LoggerEnum tipoLogger, String mensagem) {
		super();
		this.data = data;
		this.numSerie = numSerie;
		this.tipoLogger = tipoLogger;
		this.mensagem = mensagem;
	}

	public LoggerModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
