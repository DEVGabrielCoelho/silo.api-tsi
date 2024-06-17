package br.com.telematica.siloapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.AbrangenciaDetalhes;

public interface AbrangenciaDetalhesRepository extends JpaRepository<AbrangenciaDetalhes, Long> {

	List<AbrangenciaDetalhes> findByAbrangencia_Abrcod(Long codigo);

	Optional<AbrangenciaDetalhes> findByAbrangencia_abrcodAndRecurso_recnomContaining(Long codigo, String nome);

}
