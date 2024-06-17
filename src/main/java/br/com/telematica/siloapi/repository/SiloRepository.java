package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.Silo;

public interface SiloRepository extends JpaRepository<Silo, Integer> {

	void deleteByPlacod(Integer codigo);

}
