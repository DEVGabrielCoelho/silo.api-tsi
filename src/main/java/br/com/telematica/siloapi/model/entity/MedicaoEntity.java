package br.com.telematica.siloapi.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicao")
public class MedicaoEntity {

	@Id
	@Column(nullable = false)
	private Date msidth;
	@Column(nullable = false)
	private Integer silcod;
	private Double msiumi;
	private Double msiana;
	private Double msibar;
	private Double msitem;
	private Double msidis;

	public MedicaoEntity() {
	}

	public MedicaoEntity(Date msidth, Integer silcod, Double msiumi, Double msiana, Double msibar, Double msitem, Double msidis) {
		this.msidth = msidth;
		this.silcod = silcod;
		this.msiumi = msiumi;
		this.msiana = msiana;
		this.msibar = msibar;
		this.msitem = msitem;
		this.msidis = msidis;
	}

	public Date getMsidth() {
		return msidth;
	}

	public void setMsidth(Date msidth) {
		this.msidth = msidth;
	}

	public Integer getSilcod() {
		return silcod;
	}

	public void setSilcod(Integer silcod) {
		this.silcod = silcod;
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
		final StringBuffer sb = new StringBuffer("MeasureEntity{");
		sb.append("msidth=").append(msidth);
		sb.append(", silcod=").append(silcod);
		sb.append(", msiumi=").append(msiumi);
		sb.append(", msiana=").append(msiana);
		sb.append(", msibar=").append(msibar);
		sb.append(", msitem=").append(msitem);
		sb.append(", msidis=").append(msidis);
		sb.append('}');
		return sb.toString();
	}
}
