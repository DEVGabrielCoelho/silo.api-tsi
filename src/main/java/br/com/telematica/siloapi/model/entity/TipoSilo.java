package br.com.telematica.siloapi.model.entity;

import br.com.telematica.siloapi.utils.Utils;
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
	@Column(nullable = false)
	private String tsinom;
	@Column(nullable = false)
	private String tsides;
	@Column(nullable = false)
	private String tsitip;
	@Column(nullable = false)
	private Double tsidse;
	@Column(nullable = false)
	private Double tsiach;

	private Double tsirai = Double.valueOf(0);
	private Double tsilar = Double.valueOf(0);
	private Double tsicom = Double.valueOf(0);

	public TipoSilo(Long tsicod, String tsinom, String tsides, String tsitip, Double tsidse, Double tsiach, Double tsirai, Double tsilar, Double tsicom) {
		super();
		this.tsicod = tsicod;
		this.tsinom = tsinom;
		this.tsides = tsides;
		this.tsitip = tsitip;
		this.tsidse = tsidse;
		this.tsiach = tsiach;
		this.tsirai = tsirai;
		this.tsilar = tsilar;
		this.tsicom = tsicom;
	}

	public TipoSilo tipoSiloVertical(Double tsirai) {
		this.tsirai = Utils.converterCmParaMm(tsirai);
		return this;
	}

	public TipoSilo tipoSiloHorizontal(Double tsilar, Double tsicom) {
		this.tsilar = Utils.converterCmParaMm(tsilar);
		this.tsicom = Utils.converterCmParaMm(tsicom);
		return this;
	}

	public TipoSilo() {
		super();
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
			builder.append("tsides=").append(tsides).append(", ");
		}
		if (tsitip != null) {
			builder.append("tsitip=").append(tsitip).append(", ");
		}
		if (tsidse != null) {
			builder.append("tsidse=").append(tsidse).append(", ");
		}
		if (tsiach != null) {
			builder.append("tsiach=").append(tsiach).append(", ");
		}
		if (tsirai != null) {
			builder.append("tsirai=").append(tsirai).append(", ");
		}
		if (tsilar != null) {
			builder.append("tsilar=").append(tsilar).append(", ");
		}
		if (tsicom != null) {
			builder.append("tsicom=").append(tsicom);
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

	public String getTsitip() {
		return tsitip;
	}

	public void setTsitip(String tsitip) {
		this.tsitip = tsitip;
	}

	public Double getTsidse() {
		return tsidse;
	}

	public void setTsidse(Double tsidse) {
		this.tsidse = tsidse;
	}

	public Double getTsiach() {
		return tsiach;
	}

	public void setTsiach(Double tsiach) {
		this.tsiach = tsiach;
	}

	public Double getTsirai() {
		return tsirai;
	}

	public void setTsirai(Double tsirai) {
		this.tsirai = tsirai;
	}

	public Double getTsilar() {
		return tsilar;
	}

	public void setTsilar(Double tsilar) {
		this.tsilar = tsilar;
	}

	public Double getTsicom() {
		return tsicom;
	}

	public void setTsicom(Double tsicom) {
		this.tsicom = tsicom;
	}

}
