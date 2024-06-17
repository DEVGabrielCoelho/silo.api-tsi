package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.TipoSiloEntity;

public interface TipoSiloRepository extends JpaRepository<TipoSiloEntity, Long> {

	void deleteByTsicod(Integer tsicod);

}
