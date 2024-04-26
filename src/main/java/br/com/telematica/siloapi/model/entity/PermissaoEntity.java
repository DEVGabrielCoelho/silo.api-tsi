package br.com.telematica.siloapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "permissao")
public class PermissaoEntity {
	@Column(nullable = false)
	private Long usucod;
	@Column(nullable = false)
	private Long percod;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long pemcod;
	@Column(nullable = false)
	private String pemdes;
	@Column(nullable = false)
	private Integer pemget;
	@Column(nullable = false)
	private Integer pempos;
	@Column(nullable = false)
	private Integer pemput;
	@Column(nullable = false)
	private Integer pemdlt;
	@Column(nullable = false)
	private Integer pemdel;

	public Long getUsucod() {
		return usucod;
	}

	public void setUsucod(Long usucod) {
		this.usucod = usucod;
	}

	public Long getPercod() {
		return percod;
	}

	public void setPercod(Long percod) {
		this.percod = percod;
	}

	public Long getPemcod() {
		return pemcod;
	}

	public void setPemcod(Long pemcod) {
		this.pemcod = pemcod;
	}

	public String getPemdes() {
		return pemdes;
	}

	public void setPemdes(String pemdes) {
		this.pemdes = pemdes;
	}

	public Integer getPemget() {
		return pemget;
	}

	public void setPemget(Integer pemget) {
		this.pemget = pemget;
	}

	public Integer getPempos() {
		return pempos;
	}

	public void setPempos(Integer pempos) {
		this.pempos = pempos;
	}

	public Integer getPemput() {
		return pemput;
	}

	public void setPemput(Integer pemput) {
		this.pemput = pemput;
	}

	public Integer getPemdlt() {
		return pemdlt;
	}

	public void setPemdlt(Integer pemdlt) {
		this.pemdlt = pemdlt;
	}

	public Integer getPemdel() {
		return pemdel;
	}

	public void setPemdel(Integer pemdel) {
		this.pemdel = pemdel;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Permissao [usucod=");
		builder.append(usucod);
		builder.append(", percod=");
		builder.append(percod);
		builder.append(", pemcod=");
		builder.append(pemcod);
		builder.append(", pemdes=");
		builder.append(pemdes);
		builder.append(", pemget=");
		builder.append(pemget);
		builder.append(", pempos=");
		builder.append(pempos);
		builder.append(", pemput=");
		builder.append(pemput);
		builder.append(", pemdlt=");
		builder.append(pemdlt);
		builder.append(", pemdel=");
		builder.append(pemdel);
		builder.append("]");
		return builder.toString();
	}

	public PermissaoEntity(Long usucod, Long percod, Long pemcod, String pemdes, Integer pemget, Integer pempos, Integer pemput, Integer pemdlt, Integer pemdel) {
		super();
		this.usucod = usucod;
		this.percod = percod;
		this.pemcod = pemcod;
		this.pemdes = pemdes;
		this.pemget = pemget;
		this.pempos = pempos;
		this.pemput = pemput;
		this.pemdlt = pemdlt;
		this.pemdel = pemdel;
	}

	public PermissaoEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

}
