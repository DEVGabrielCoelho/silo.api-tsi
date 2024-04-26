package br.com.telematica.siloapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "silo")
public class SiloEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long silcod;
	@Column(nullable = false)
	private Long tsicod;
	@Column(length = 100, nullable = false)
	private String silnom;
	@Column(nullable = false)
	private Long placod;

	public SiloEntity() {
	}

	public SiloEntity(Long silcod, Long tsicod, String silnom, Long placod) {
		this.silcod = silcod;
		this.tsicod = tsicod;
		this.silnom = silnom;
		this.placod = placod;
	}

	public Long getSilcod() {
		return silcod;
	}

	public void setSilcod(Long silcod) {
		this.silcod = silcod;
	}

	public Long getTsicod() {
		return tsicod;
	}

	public void setTsicod(Long tsicod) {
		this.tsicod = tsicod;
	}

	public String getSilnom() {
		return silnom;
	}

	public void setSilnom(String silnom) {
		this.silnom = silnom;
	}

	public Long getPlacod() {
		return placod;
	}

	public void setPlacod(Long placod) {
		this.placod = placod;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SiloEntity [silcod=");
		builder.append(silcod);
		builder.append(", tsicod=");
		builder.append(tsicod);
		builder.append(", silnom=");
		builder.append(silnom);
		builder.append(", placod=");
		builder.append(placod);
		builder.append("]");
		return builder.toString();
	}

}
