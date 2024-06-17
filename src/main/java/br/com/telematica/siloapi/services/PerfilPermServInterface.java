package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.PerfilModel;
import br.com.telematica.siloapi.model.dto.PerfilPermissaoDTO;
import jakarta.persistence.EntityNotFoundException;

public interface PerfilPermServInterface {

	public PerfilPermissaoDTO save(PerfilModel perModel) throws IOException;

	public PerfilPermissaoDTO update(@NonNull Long codigo, PerfilModel perModel) throws EntityNotFoundException, IOException;

	public List<PerfilPermissaoDTO> findAll() throws EntityNotFoundException, IOException;

	public PerfilPermissaoDTO findById(@NonNull Long codigo) throws EntityNotFoundException, IOException;

	public ResponseGlobalModel delete(@NonNull Long perfil) throws IOException;

	Page<PerfilPermissaoDTO> findAll(String nome, @NonNull Pageable pageable) throws EntityNotFoundException, IOException;

}
