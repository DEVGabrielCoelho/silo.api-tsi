package br.com.telematica.siloapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "perfil")
public class PerfilEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long percod;
	@Column(nullable = false)
	private String perdes;
	@Column(nullable = false)
	private Integer perdel;

	public Long getPercod() {
		return percod;
	}

	public void setPercod(Long percod) {
		this.percod = percod;
	}

	public String getPerdes() {
		return perdes;
	}

	public void setPerdes(String perdes) {
		this.perdes = perdes;
	}

	public Integer getPerdel() {
		return perdel;
	}

	public void setPerdel(Integer perdel) {
		this.perdel = perdel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Perfil [percod=");
		builder.append(percod);
		builder.append(", perdes=");
		builder.append(perdes);
		builder.append(", perdel=");
		builder.append(perdel);
		builder.append("]");
		return builder.toString();
	}

	public PerfilEntity(Long percod, String perdes, Integer perdel) {
		super();
		this.percod = percod;
		this.perdes = perdes;
		this.perdel = perdel;
	}

	public PerfilEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
