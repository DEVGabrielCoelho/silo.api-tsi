package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.EmpresaEntity;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {

    EmpresaEntity findByEmpCnp(Integer empcnp);
}
