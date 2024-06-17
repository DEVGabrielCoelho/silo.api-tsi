package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import br.com.telematica.siloapi.model.entity.TipoSiloEntity;
import jakarta.transaction.Transactional;

public interface TipoSiloInterface {

	public ResponseEntity<GenericResponseModel> save(TipoSiloDTO tipoSiloDTO) throws RuntimeException;

	@Transactional
	public ResponseEntity<GenericResponseModel> deleteByTsicod(Integer codigo) throws IOException;

	public ResponseEntity<GenericResponseModel> update(TipoSiloDTO tipoSiloDTO) throws IOException;

	public List<TipoSiloEntity> findAll() throws IOException;

	public ResponseEntity<GenericResponseModel> findAllTipoSiloDTO() throws IOException;

	public TipoSiloDTO findById(Integer id) throws IOException, EmptyResultDataAccessException;

}
