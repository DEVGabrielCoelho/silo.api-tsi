package br.com.telematica.siloapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "planta")
public class PlantaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer placod;
	@Column(length = 100, nullable = false)
	private String planom;
	@Column(nullable = false)
	private Integer empcod;

	public PlantaEntity() {
	}

	public PlantaEntity(Integer placod, String planom, Integer empcod) {
		this.placod = placod;
		this.planom = planom;
		this.empcod = empcod;
	}

	public Integer getPlacod() {
		return placod;
	}

	public void setPlacod(Integer placod) {
		this.placod = placod;
	}

	public String getPlanom() {
		return planom;
	}

	public void setPlanom(String planom) {
		this.planom = planom;
	}

	public Integer getEmpcod() {
		return empcod;
	}

	public void setEmpcod(Integer empcod) {
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
