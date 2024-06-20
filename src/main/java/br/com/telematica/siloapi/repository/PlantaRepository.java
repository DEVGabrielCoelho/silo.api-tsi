package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.telematica.siloapi.model.entity.Planta;

public interface PlantaRepository extends JpaRepository<Planta, Long>, JpaSpecificationExecutor<Planta> {

	void removeByPlacod(Long codigo);

}
