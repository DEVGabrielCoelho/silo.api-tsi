package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.entity.SiloEntity;
import jakarta.transaction.Transactional;

public interface SiloInterface {

	public ResponseEntity<GenericResponseModel> save(SiloDTO siloDTO) throws RuntimeException;

	@Transactional
	public ResponseEntity<GenericResponseModel> deleteByPlacod(Integer codigo) throws IOException;

	public ResponseEntity<GenericResponseModel> update(SiloDTO siloDTO) throws IOException;

	public List<SiloEntity> findAll() throws IOException;

	public ResponseEntity<GenericResponseModel> findAllSiloDTO() throws IOException;

	public ResponseEntity<GenericResponseModel> findById(Integer id) throws IOException, EmptyResultDataAccessException;
}
