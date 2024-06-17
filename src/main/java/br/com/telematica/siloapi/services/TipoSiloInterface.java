package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.TipoSiloModel;
import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import jakarta.transaction.Transactional;

public interface TipoSiloInterface {

	@Transactional
	public ResponseEntity<TipoSiloDTO> deleteByTsicod(Long codigo) throws IOException;

	public ResponseEntity<TipoSiloDTO> update(Long codigo, TipoSiloModel tipoSiloDTO) throws IOException;

	public ResponseEntity<List<TipoSiloDTO>> findAllTipoSiloDTO() throws IOException;

	public ResponseEntity<TipoSiloDTO> findById(Long id) throws IOException, EmptyResultDataAccessException;

	public ResponseEntity<TipoSiloDTO> save(TipoSiloModel tipoSilo) throws RuntimeException, IOException;

}
