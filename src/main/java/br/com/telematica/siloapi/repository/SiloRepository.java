package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.telematica.siloapi.model.entity.Silo;

public interface SiloRepository extends JpaRepository<Silo, Long>, JpaSpecificationExecutor<Silo> {

	void deleteByPlanta_Placod(Long codigo);

}
