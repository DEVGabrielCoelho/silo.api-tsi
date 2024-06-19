package br.com.telematica.siloapi.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicao")
public class Medicao {

	@Id
	@Column(nullable = false)
	private Date msidth;
	@ManyToOne
	@JoinColumn(name = "silcod", nullable = false)
	private Silo silo;
	@ManyToOne
	@JoinColumn(name = "smocod", nullable = false)
	private SiloModulo silomodulo;
	private Double msiumi;
	private Double msiana;
	private Double msibar;
	private Double msitem;
	private Double msidis;

	public Medicao() {
	}

	public Medicao(Date msidth, Silo silo, SiloModulo silomodulo, Double msiumi, Double msiana, Double msibar,
			Double msitem, Double msidis) {
		this.msidth = msidth;
		this.silo = silo;
		this.silomodulo = silomodulo;
		this.msiumi = msiumi;
		this.msiana = msiana;
		this.msibar = msibar;
		this.msitem = msitem;
		this.msidis = msidis;
	}

	public Medicao updateMedicao(Silo silo, SiloModulo silomodulo, Double msiumi, Double msiana, Double msibar,
			Double msitem, Double msidis) {
		this.silo = silo;
		this.silomodulo = silomodulo;
		this.msiumi = msiumi;
		this.msiana = msiana;
		this.msibar = msibar;
		this.msitem = msitem;
		this.msidis = msidis;
		return this;
	}

	public Date getMsidth() {
		return msidth;
	}

	public void setMsidth(Date msidth) {
		this.msidth = msidth;
	}

	public Silo getSilo() {
		return silo;
	}

	public void setSilo(Silo silo) {
		this.silo = silo;
	}

	public SiloModulo getSilomodulo() {
		return silomodulo;
	}

	public void setSilomodulo(SiloModulo silomodulo) {
		this.silomodulo = silomodulo;
	}

	public Double getMsiumi() {
		return msiumi;
	}

	public void setMsiumi(Double msiumi) {
		this.msiumi = msiumi;
	}

	public Double getMsiana() {
		return msiana;
	}

	public void setMsiana(Double msiana) {
		this.msiana = msiana;
	}

	public Double getMsibar() {
		return msibar;
	}

	public void setMsibar(Double msibar) {
		this.msibar = msibar;
	}

	public Double getMsitem() {
		return msitem;
	}

	public void setMsitem(Double msitem) {
		this.msitem = msitem;
	}

	public Double getMsidis() {
		return msidis;
	}

	public void setMsidis(Double msidis) {
		this.msidis = msidis;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Medicao [");
		if (msidth != null) {
			builder.append("msidth=").append(msidth).append(", ");
		}
		if (silo != null) {
			builder.append("silo=").append(silo).append(", ");
		}
		if (silomodulo != null) {
			builder.append("silomodulo=").append(silomodulo).append(", ");
		}
		if (msiumi != null) {
			builder.append("msiumi=").append(msiumi).append(", ");
		}
		if (msiana != null) {
			builder.append("msiana=").append(msiana).append(", ");
		}
		if (msibar != null) {
			builder.append("msibar=").append(msibar).append(", ");
		}
		if (msitem != null) {
			builder.append("msitem=").append(msitem).append(", ");
		}
		if (msidis != null) {
			builder.append("msidis=").append(msidis);
		}
		builder.append("]");
		return builder.toString();
	}

}
