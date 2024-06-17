package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.AbrangenciaModel;
import br.com.telematica.siloapi.model.dto.AbrangenciaListaDetalhesDTO;
import br.com.telematica.siloapi.records.ItensAbrangentes;
import jakarta.persistence.EntityNotFoundException;

public interface AbrangenciaServiceInterface {

	AbrangenciaListaDetalhesDTO update(Long codigo, AbrangenciaModel abrangenciaModel);

	AbrangenciaListaDetalhesDTO save(AbrangenciaModel abrangenciaModel) throws IOException;

	List<AbrangenciaListaDetalhesDTO> findAll() throws EntityNotFoundException, IOException;

	AbrangenciaListaDetalhesDTO findById(@NonNull Long codigo) throws EntityNotFoundException, IOException;

	ResponseGlobalModel delete(@NonNull Long perfil) throws IOException;

	Page<AbrangenciaListaDetalhesDTO> findAll(String nome, Pageable pageable) throws EntityNotFoundException, IOException;

	ItensAbrangentes findByItemAbrangence();

}
