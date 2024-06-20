package br.com.telematica.siloapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.telematica.siloapi.model.entity.SiloModulo;

public interface SiloModuloRepository extends JpaRepository<SiloModulo, Long>, JpaSpecificationExecutor<SiloModulo> {

	Optional<SiloModulo> findBySmonse(String nse);
}
