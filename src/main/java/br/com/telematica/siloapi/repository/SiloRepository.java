package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.SiloEntity;

public interface SiloRepository extends JpaRepository<SiloEntity, Integer>{

    void deleteByPlacod(Integer codigo);

}
