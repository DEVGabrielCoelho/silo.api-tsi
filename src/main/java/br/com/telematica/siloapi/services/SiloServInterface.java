package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.entity.SiloEntity;
import jakarta.transaction.Transactional;

public interface SiloServInterface {

	public ResponseEntity<Object> save(SiloDTO siloDTO) throws RuntimeException;

	@Transactional
	public ResponseEntity<Object> deleteByPlacod(Integer codigo) throws IOException;

	public ResponseEntity<Object> update(SiloDTO siloDTO) throws IOException;

	public List<SiloEntity> findAll() throws IOException;

	public ResponseEntity<Object> findAllSiloDTO() throws IOException;

	public ResponseEntity<Object> findById(Integer id) throws IOException, EmptyResultDataAccessException;
}
