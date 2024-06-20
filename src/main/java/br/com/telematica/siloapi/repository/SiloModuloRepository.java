package br.com.telematica.siloapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.SiloModulo;

public interface SiloModuloRepository extends JpaRepository<SiloModulo, Long> {

	Optional<SiloModulo> findBySmonse(String nse);
}
