package br.com.telematica.siloapi.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.telematica.siloapi.model.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>, JpaSpecificationExecutor<Empresa> {

	Optional<Empresa> findByEmpcnp(Long empcnp);

	Page<Empresa> findByEmpdel(Integer empdel, Pageable pageable);

	Page<Empresa> findByEmpdelAndEmpcodIn(Integer empdel, Pageable pageable, Collection<Long> list);

	Page<Empresa> findByEmpnomLikeAndEmpdel(String empnom, Integer empdel, Pageable pageable);

	Page<Empresa> findByEmpnomLikeAndEmpdelAndEmpcodIn(String empnom, Integer candel, Pageable pageable, Collection<Long> list);

	List<Empresa> findByEmpdel(Integer empdel);

	List<Empresa> findByEmpdelAndEmpcodIn(Integer empdel, Collection<Long> abdcods);

}
