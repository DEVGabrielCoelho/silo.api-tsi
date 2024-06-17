package br.com.telematica.siloapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_silo")
public class TipoSiloEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long tsicod;
	@ManyToOne
	@JoinColumn(name = "empcod", nullable = false)
	private Empresa empresa;
	@Column(length = 50, nullable = false)
	private String tsides;

	public TipoSiloEntity tipoSiloEntity(Empresa empresa, String tsides) {
		this.empresa = empresa;
		this.tsides = tsides;
		return this;
	}

	public TipoSiloEntity(Long tsicod, Empresa empresa, String tsides) {
		this.tsicod = tsicod;
		this.empresa = empresa;
		this.tsides = tsides;
	}

	public Long getTsicod() {
		return tsicod;
	}

	public void setTsicod(Long tsicod) {
		this.tsicod = tsicod;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getTsides() {
		return tsides;
	}

	public void setTsides(String tsides) {
		this.tsides = tsides;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipoSiloEntity [tsicod=");
		builder.append(tsicod);
		builder.append(", empresa=");
		builder.append(empresa);
		builder.append(", tsides=");
		builder.append(tsides);
		builder.append("]");
		return builder.toString();
	}

}
