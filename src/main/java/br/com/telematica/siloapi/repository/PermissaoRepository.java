package br.com.telematica.siloapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.PermissaoEntity;

public interface PermissaoRepository extends JpaRepository<PermissaoEntity, Long> {
	List<PermissaoEntity> findByUsucodAndPercod(Long usucod, Long percod);

	List<PermissaoEntity> findByUsucod(Long usucod);

	List<PermissaoEntity> findByPercod(Long percod);
}
