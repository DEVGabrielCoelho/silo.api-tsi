package br.com.telematica.siloapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import br.com.telematica.siloapi.model.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
	@NonNull
	Page<UsuarioEntity> findAll(@NonNull Pageable pageable);

	@NonNull
	Page<UsuarioEntity> findByUsulogLike(@NonNull String usulog, @NonNull Pageable pageable);

	@NonNull
	Optional<UsuarioEntity> findByUsulog(@NonNull String usulog);
}
