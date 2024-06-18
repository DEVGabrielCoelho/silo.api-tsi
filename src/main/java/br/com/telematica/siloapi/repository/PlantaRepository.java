package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.Planta;

public interface PlantaRepository extends JpaRepository<Planta, Long> {

	void removeByPlacod(Long codigo);

}
