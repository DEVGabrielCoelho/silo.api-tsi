package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import br.com.telematica.siloapi.model.AbrangenciaModel;
import br.com.telematica.siloapi.model.dto.AbrangenciaListaDetalhesDTO;
import br.com.telematica.siloapi.records.ItensAbrangentes;
import jakarta.persistence.EntityNotFoundException;

public interface AbrangenciaServInterface {

	ResponseEntity<AbrangenciaListaDetalhesDTO> update(Long codigo, AbrangenciaModel abrangenciaModel);

	ResponseEntity<AbrangenciaListaDetalhesDTO> save(AbrangenciaModel abrangenciaModel) throws IOException;

	ResponseEntity<List<AbrangenciaListaDetalhesDTO>> findAll() throws EntityNotFoundException, IOException;

	ResponseEntity<AbrangenciaListaDetalhesDTO> findById(@NonNull Long codigo) throws EntityNotFoundException, IOException;

	ResponseEntity<AbrangenciaListaDetalhesDTO> delete(@NonNull Long perfil) throws IOException;

	ResponseEntity<Page<AbrangenciaListaDetalhesDTO>> findAll(String nome, Pageable pageable) throws EntityNotFoundException, IOException;

	ResponseEntity<ItensAbrangentes> findByItemAbrangence() throws IOException;

}
