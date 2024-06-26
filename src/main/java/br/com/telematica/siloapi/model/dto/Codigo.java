package br.com.telematica.siloapi.model.dto;

public class Codigo {
	private Long codigo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Codigo [");
		if (codigo != null) {
			builder.append("codigo=").append(codigo);
		}
		builder.append("]");
		return builder.toString();
	}

	public Codigo(Long codigo) {
		super();
		this.codigo = codigo;
	}

	public Codigo() {
		super();
		
	}

}
