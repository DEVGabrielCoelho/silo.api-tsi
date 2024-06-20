package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.telematica.siloapi.model.entity.TipoSilo;

public interface TipoSiloRepository extends JpaRepository<TipoSilo, Long>, JpaSpecificationExecutor<TipoSilo> {

	void deleteByTsicod(Integer tsicod);

}
