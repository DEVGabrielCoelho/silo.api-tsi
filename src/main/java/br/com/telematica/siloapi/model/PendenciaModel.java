package br.com.telematica.siloapi.model;

import br.com.telematica.siloapi.model.enums.PendenciaEnum;
import br.com.telematica.siloapi.model.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Modelo de Pendencia")
public class PendenciaModel {

	@NotBlank
	@Schema(name = "numSerie", description = "Numero de Série do Modulo", example = "N123124", format = "String")
	private String numSerie;
	@NotBlank
	@Schema(name = "tipoPendencia", description = "Tipo da Pendência", example = "DATA_HORA", format = "String")
	private PendenciaEnum tipoPendencia;
	@NotBlank
	@Schema(name = "status", description = "Status da pendencia", example = "PENDENCIA", format = "Sring")
	private StatusEnum status;
	@NotBlank
	@Schema(name = "descricao", description = "Descrição da pendencia", example = "Descrição", format = "String")
	private String descricao;
	@Schema(name = "codigoFirmware", description = "Se for pendencia colocar o código do firmware cadastrado.", example = "1", format = "Long")
	private Long codigoFirmware;

	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public PendenciaEnum getTipoPendencia() {
		return tipoPendencia;
	}

	public void setTipoPendencia(PendenciaEnum tipoPendencia) {
		this.tipoPendencia = tipoPendencia;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getCodigoFirmware() {
		return codigoFirmware;
	}

	public void setCodigoFirmware(Long codigoFirmware) {
		this.codigoFirmware = codigoFirmware;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PendenciaModel [");
		if (numSerie != null) {
			builder.append("numSerie=").append(numSerie).append(", ");
		}
		if (tipoPendencia != null) {
			builder.append("tipoPendencia=").append(tipoPendencia).append(", ");
		}
		if (status != null) {
			builder.append("status=").append(status).append(", ");
		}
		if (descricao != null) {
			builder.append("descricao=").append(descricao).append(", ");
		}
		if (codigoFirmware != null) {
			builder.append("codigoFirmware=").append(codigoFirmware);
		}
		builder.append("]");
		return builder.toString();
	}

	public PendenciaModel(String numSerie, PendenciaEnum tipoPendencia, StatusEnum status, String descricao, Long codigoFirmware) {
		super();
		this.numSerie = numSerie;
		this.tipoPendencia = tipoPendencia;
		this.status = status;
		this.descricao = descricao;
		this.codigoFirmware = codigoFirmware;
	}

	public PendenciaModel() {
		super();
	}

}
