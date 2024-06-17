package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.UsuarioModel;
import br.com.telematica.siloapi.model.dto.UsuarioDTO;
import br.com.telematica.siloapi.model.dto.UsuarioPermissaoDTO;
import jakarta.persistence.EntityNotFoundException;

public interface UsuarioServInterface {

	UsuarioDTO findById(@NonNull Long codigo) throws EntityNotFoundException, IOException;

	List<UsuarioDTO> findAll() throws EntityNotFoundException, IOException;

	UsuarioDTO saveUpdateEncodePassword(@NonNull Long codigo, @NonNull UsuarioModel userModel) throws EntityNotFoundException, IOException;

	UsuarioDTO saveUpdateEncodePassword(@NonNull UsuarioModel userModel) throws EntityNotFoundException, IOException;

	Page<UsuarioDTO> findAll(String nome, @NonNull Pageable pageable) throws EntityNotFoundException, IOException;

	ResponseGlobalModel delete(@NonNull Long perfil) throws IOException;

	UsuarioPermissaoDTO findByIdPermission(@NonNull Long codigo) throws EntityNotFoundException, IOException;

}
