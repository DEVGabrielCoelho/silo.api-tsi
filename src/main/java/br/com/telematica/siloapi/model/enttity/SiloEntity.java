package br.com.telematica.siloapi.model.enttity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "silo")
public class SiloEntity {

	@Id
	@Column(nullable = false)
	private Integer silcod;
	@Column(nullable = false)
	private Integer tsicod;
	@Column(length = 100, nullable = false)
	private String silnom;
	@Column(nullable = false)
	private Integer placod;

	public SiloEntity() {
	}

	public SiloEntity(Integer silcod, Integer tsicod, String silnom, Integer placod) {
		this.silcod = silcod;
		this.tsicod = tsicod;
		this.silnom = silnom;
		this.placod = placod;
	}

	public Integer getSilcod() {
		return silcod;
	}

	public void setSilcod(Integer silcod) {
		this.silcod = silcod;
	}

	public Integer getTsicod() {
		return tsicod;
	}

	public void setTsicod(Integer tsicod) {
		this.tsicod = tsicod;
	}

	public String getSilnom() {
		return silnom;
	}

	public void setSilnom(String silnom) {
		this.silnom = silnom;
	}

	public Integer getPlacod() {
		return placod;
	}

	public void setPlacod(Integer placod) {
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
