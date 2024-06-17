package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.TipoSilo;

public interface TipoSiloRepository extends JpaRepository<TipoSilo, Long> {

	void deleteByTsicod(Integer tsicod);

}
