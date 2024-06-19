package br.com.telematica.siloapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_silo")
public class TipoSilo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long tsicod;
	@Column(length = 50, nullable = false)
	private String tsinom;
	@Column(length = 50, nullable = false)
	private String tsides;

	public TipoSilo tipoSiloEntity(String tsinom, String tsides) {
		this.tsinom = tsinom;
		this.tsides = tsides;
		return this;
	}

	public TipoSilo(Long tsicod, String tsinom, String tsides) {
		super();
		this.tsicod = tsicod;
		this.tsinom = tsinom;
		this.tsides = tsides;
	}

	public TipoSilo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipoSilo [");
		if (tsicod != null) {
			builder.append("tsicod=").append(tsicod).append(", ");
		}
		if (tsinom != null) {
			builder.append("tsinom=").append(tsinom).append(", ");
		}
		if (tsides != null) {
			builder.append("tsides=").append(tsides);
		}
		builder.append("]");
		return builder.toString();
	}

	public Long getTsicod() {
		return tsicod;
	}

	public void setTsicod(Long tsicod) {
		this.tsicod = tsicod;
	}

	public String getTsinom() {
		return tsinom;
	}

	public void setTsinom(String tsinom) {
		this.tsinom = tsinom;
	}

	public String getTsides() {
		return tsides;
	}

	public void setTsides(String tsides) {
		this.tsides = tsides;
	}

}
