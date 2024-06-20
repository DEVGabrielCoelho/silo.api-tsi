package br.com.telematica.siloapi.model.entity;

import java.util.Date;

import br.com.telematica.sirene.models.entitys.SireneModulo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "silo_modulo")
public class SiloModulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long smocod;
	@ManyToOne
	@JoinColumn(name = "silcod", nullable = false)
	private Silo silo;
	@Column(nullable = false)
	private String smodes;
	@Column(nullable = false)
	private Long smotse;
	@Column(nullable = false, unique = true)
	private String smonse;
	@Column(nullable = false)
	private Long smotke;
	@Column(nullable = false)
	private Long smotme;
	private Date smohke;
	private Date smohme;
	@Column(nullable = false)
	private Integer smogmt;
	@Column(nullable = false)
	private String smocke;
	@Column(nullable = false)
	private String smocme;
	@Column(nullable = false)
	private String smosta;

	public SiloModulo(Long smocod, Silo silo, String smodes, Long smotse, String smonse, Long smotke, Long smotme, Date smohke, Date smohme, Integer smogmt, String smocke, String smocme, String smosta) {
		super();
		this.smocod = smocod;
		this.silo = silo;
		this.smodes = smodes;
		this.smotse = smotse;
		this.smonse = smonse;
		this.smotke = smotke;
		this.smotme = smotme;
		this.smohke = smohke;
		this.smohme = smohme;
		this.smogmt = smogmt;
		this.smocke = smocke;
		this.smocme = smocme;
		this.smosta = smosta;
	}

	public SiloModulo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SiloModulo sireneModuloRegisterKeep(Date keepalive) {
		this.smohke = keepalive;
		return this;
	}

	public SiloModulo sireneModuloRegisterMedicao(Date medicao) {
		this.smohme = medicao;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SiloModulo [");
		if (smocod != null) {
			builder.append("smocod=").append(smocod).append(", ");
		}
		if (silo != null) {
			builder.append("silo=").append(silo).append(", ");
		}
		if (smodes != null) {
			builder.append("smodes=").append(smodes).append(", ");
		}
		if (smotse != null) {
			builder.append("smotse=").append(smotse).append(", ");
		}
		if (smonse != null) {
			builder.append("smonse=").append(smonse).append(", ");
		}
		if (smotke != null) {
			builder.append("smotke=").append(smotke).append(", ");
		}
		if (smotme != null) {
			builder.append("smotme=").append(smotme).append(", ");
		}
		if (smohke != null) {
			builder.append("smohke=").append(smohke).append(", ");
		}
		if (smohme != null) {
			builder.append("smohme=").append(smohme).append(", ");
		}
		if (smogmt != null) {
			builder.append("smogmt=").append(smogmt).append(", ");
		}
		if (smocke != null) {
			builder.append("smocke=").append(smocke).append(", ");
		}
		if (smocme != null) {
			builder.append("smocme=").append(smocme).append(", ");
		}
		if (smosta != null) {
			builder.append("smosta=").append(smosta);
		}
		builder.append("]");
		return builder.toString();
	}

	public Long getSmocod() {
		return smocod;
	}

	public void setSmocod(Long smocod) {
		this.smocod = smocod;
	}

	public Silo getSilo() {
		return silo;
	}

	public void setSilo(Silo silo) {
		this.silo = silo;
	}

	public String getSmodes() {
		return smodes;
	}

	public void setSmodes(String smodes) {
		this.smodes = smodes;
	}

	public Long getSmotse() {
		return smotse;
	}

	public void setSmotse(Long smotse) {
		this.smotse = smotse;
	}

	public String getSmonse() {
		return smonse;
	}

	public void setSmonse(String smonse) {
		this.smonse = smonse;
	}

	public Long getSmotke() {
		return smotke;
	}

	public void setSmotke(Long smotke) {
		this.smotke = smotke;
	}

	public Long getSmotme() {
		return smotme;
	}

	public void setSmotme(Long smotme) {
		this.smotme = smotme;
	}

	public Date getSmohke() {
		return smohke;
	}

	public void setSmohke(Date smohke) {
		this.smohke = smohke;
	}

	public Date getSmohme() {
		return smohme;
	}

	public void setSmohme(Date smohme) {
		this.smohme = smohme;
	}

	public Integer getSmogmt() {
		return smogmt;
	}

	public void setSmogmt(Integer smogmt) {
		this.smogmt = smogmt;
	}

	public String getSmocke() {
		return smocke;
	}

	public void setSmocke(String smocke) {
		this.smocke = smocke;
	}

	public String getSmocme() {
		return smocme;
	}

	public void setSmocme(String smocme) {
		this.smocme = smocme;
	}

	public String getSmosta() {
		return smosta;
	}

	public void setSmosta(String smosta) {
		this.smosta = smosta;
	}

}
