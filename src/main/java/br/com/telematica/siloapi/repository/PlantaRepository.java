package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.Planta;

public interface PlantaRepository extends JpaRepository<Planta, Integer> {

	void removeByPlacod(Integer codigo);

}
