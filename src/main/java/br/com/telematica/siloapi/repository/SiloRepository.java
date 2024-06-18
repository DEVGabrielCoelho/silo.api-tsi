package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.Silo;

public interface SiloRepository extends JpaRepository<Silo, Long> {

	void deleteByPlacod(Long codigo);

}
