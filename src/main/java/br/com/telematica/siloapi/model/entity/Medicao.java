package br.com.telematica.siloapi.model.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.Predicate;

@Entity
@Table(name = "medicao")
public class Medicao {

	@Id
	@Column(nullable = false)
	private Date msidth;
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

	public Medicao(Date msidth, SiloModulo silomodulo, Double msiumi, Double msiana, Double msibar, Double msitem, Double msidis) {
		this.msidth = msidth;
//		this.silo = silo;
		this.silomodulo = silomodulo;
		this.msiumi = msiumi;
		this.msiana = msiana;
		this.msibar = msibar;
		this.msitem = msitem;
		this.msidis = msidis;
	}

	public Medicao updateMedicao(SiloModulo silomodulo, Double msiumi, Double msiana, Double msibar, Double msitem, Double msidis) {
//		this.silo = silo;
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

	public static Specification<Medicao> filterByFields(String searchTerm, List<Long> listSmocod, String dataInicio, String dataFim) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			// Filtragem por lista de IDs de SiloModulo
			if (listSmocod != null && !listSmocod.isEmpty()) {
				predicates.add(root.get("silomodulo").get("smocod").in(listSmocod));
			}

			// Filtragem por intervalo de datas
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if (dataInicio != null && !dataInicio.isEmpty()) {
				try {
					Date startDate = dateFormat.parse(dataInicio);
					predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("msidth"), startDate));
				} catch (ParseException e) {
					// Ignora se a conversão falhar
				}
			}

			if (dataFim != null && !dataFim.isEmpty()) {
				try {
					Date endDate = dateFormat.parse(dataFim);
					predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("msidth"), endDate));
				} catch (ParseException e) {
					// Ignora se a conversão falhar
				}
			}

			// Filtragem por termo de busca
			if (searchTerm != null && !searchTerm.isEmpty()) {
				// String likePattern = "%" + searchTerm.toLowerCase() + "%";

				List<Predicate> searchPredicates = new ArrayList<>();

				// Tenta converter o termo de busca para Double
				try {
					Double searchTermDouble = Double.valueOf(searchTerm);
					searchPredicates.add(criteriaBuilder.equal(root.get("msiumi"), searchTermDouble));
					searchPredicates.add(criteriaBuilder.equal(root.get("msiana"), searchTermDouble));
					searchPredicates.add(criteriaBuilder.equal(root.get("msibar"), searchTermDouble));
					searchPredicates.add(criteriaBuilder.equal(root.get("msitem"), searchTermDouble));
					searchPredicates.add(criteriaBuilder.equal(root.get("msidis"), searchTermDouble));
				} catch (NumberFormatException e) {
					// Ignora se a conversão para Double falhar
				}

				predicates.add(criteriaBuilder.or(searchPredicates.toArray(Predicate[]::new)));
			}

			return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
		};
	}

}
