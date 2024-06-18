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
@Table(name = "planta")
public class Planta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long placod;
	@Column(length = 100, nullable = false)
	private String planom;
	@ManyToOne
	@JoinColumn(name = "empcod", nullable = false)
	private Empresa empresa;

	public Planta() {
	}

	public Planta(Long placod, String planom, Empresa empresa) {
		this.placod = placod;
		this.planom = planom;
		this.empresa = empresa;
	}

	public Planta plantaUpdateOrSave(String planom, Empresa empresa) {
		this.planom = planom;
		this.empresa = empresa;
		return this;
	}

	public Long getPlacod() {
		return placod;
	}

	public void setPlacod(Long placod) {
		this.placod = placod;
	}

	public String getPlanom() {
		return planom;
	}

	public void setPlanom(String planom) {
		this.planom = planom;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Planta [");
		if (placod != null) {
			builder.append("placod=").append(placod).append(", ");
		}
		if (planom != null) {
			builder.append("planom=").append(planom).append(", ");
		}
		if (empresa != null) {
			builder.append("empresa=").append(empresa);
		}
		builder.append("]");
		return builder.toString();
	}

}
