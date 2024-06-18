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
@Table(name = "silo")
public class Silo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long silcod;
	@ManyToOne
	@JoinColumn(name = "tsicod", nullable = false)
	private TipoSilo tipoSilo;
	@Column(length = 100, nullable = false)
	private String silnom;
	@ManyToOne
	@JoinColumn(name = "placod", nullable = false)
	private Planta planta;

	public Silo() {
	}

	public Silo(Long silcod, TipoSilo tipoSilo, String silnom, Planta planta) {
		this.silcod = silcod;
		this.tipoSilo = tipoSilo;
		this.silnom = silnom;
		this.planta = planta;
	}

	public Long getSilcod() {
		return silcod;
	}

	public void setSilcod(Long silcod) {
		this.silcod = silcod;
	}

	public TipoSilo getTipoSilo() {
		return tipoSilo;
	}

	public void setTipoSilo(TipoSilo tipoSilo) {
		this.tipoSilo = tipoSilo;
	}

	public Planta getPlanta() {
		return planta;
	}

	public void setPlanta(Planta planta) {
		this.planta = planta;
	}

	public String getSilnom() {
		return silnom;
	}

	public void setSilnom(String silnom) {
		this.silnom = silnom;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Silo [");
		if (silcod != null) {
			builder.append("silcod=").append(silcod).append(", ");
		}
		if (tipoSilo != null) {
			builder.append("tipoSilo=").append(tipoSilo).append(", ");
		}
		if (silnom != null) {
			builder.append("silnom=").append(silnom).append(", ");
		}
		if (planta != null) {
			builder.append("planta=").append(planta);
		}
		builder.append("]");
		return builder.toString();
	}

}
