package br.com.telematica.siloapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	@Column(nullable = false)
	private Long empcod;

	public Planta() {
	}

	public Planta(Long placod, String planom, Long empcod) {
		this.placod = placod;
		this.planom = planom;
		this.empcod = empcod;
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

	public Long getEmpcod() {
		return empcod;
	}

	public void setEmpcod(Long empcod) {
		this.empcod = empcod;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlantaEntity [placod=");
		builder.append(placod);
		builder.append(", planom=");
		builder.append(planom);
		builder.append(", empcod=");
		builder.append(empcod);
		builder.append("]");
		return builder.toString();
	}

}
