package br.com.telematica.siloapi.model.dto;

import java.util.List;
import java.util.Map;

public class KeepAliveDTO {

	private String dataKeepAlive;
	private Map<String, List<Map<String, Object>>> pendencias;

	public String getDataKeepAlive() {
		return dataKeepAlive;
	}

	public void setDataKeepAlive(String dataRequisicao) {
		this.dataKeepAlive = dataRequisicao;
	}

	public Map<String, List<Map<String, Object>>> getPendencias() {
		return pendencias;
	}

	public void setPendencias(Map<String, List<Map<String, Object>>> pendencias) {
		this.pendencias = pendencias;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("KeepAliveDTO [");
		if (dataKeepAlive != null) {
			builder.append("dataKeepAlive=").append(dataKeepAlive).append(", ");
		}
		if (pendencias != null) {
			builder.append("pendencias=").append(pendencias);
		}
		builder.append("]");
		return builder.toString();
	}

	public KeepAliveDTO(String dataKeepAlive, Map<String, List<Map<String, Object>>> pendencias) {
		super();
		this.dataKeepAlive = dataKeepAlive;
		this.pendencias = pendencias;
	}

	public KeepAliveDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
