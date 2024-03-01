package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.PlantaEntity;

public interface PlantaRepository extends JpaRepository<PlantaEntity, Integer>{

    void removeByPlacod(Integer codigo);

}
