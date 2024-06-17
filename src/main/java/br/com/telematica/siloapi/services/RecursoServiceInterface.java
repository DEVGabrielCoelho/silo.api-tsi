package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.RecursoModel;
import br.com.telematica.siloapi.model.dto.RecursoDTO;
import jakarta.persistence.EntityNotFoundException;

public interface RecursoServiceInterface {

	RecursoDTO save(RecursoModel perModel);

	RecursoDTO update(Long codigo, RecursoModel perModel);

	List<RecursoDTO> findAll() throws EntityNotFoundException, IOException;

	RecursoDTO findById(@NonNull Long codigo) throws EntityNotFoundException, IOException;

	ResponseGlobalModel delete(@NonNull Long perfil) throws IOException;

	Page<RecursoDTO> findAll(String nome, Pageable pageable) throws EntityNotFoundException, IOException;

	RecursoDTO findByString(@NonNull String nome) throws EntityNotFoundException, IOException;

}
