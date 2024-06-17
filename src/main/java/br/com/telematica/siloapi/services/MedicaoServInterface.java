package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.model.entity.Medicao;
import jakarta.transaction.Transactional;

public interface MedicaoServInterface {

	public ResponseEntity<Object> save(MedicaoDTO medicaoDTO) throws RuntimeException;

	@Transactional
	public ResponseEntity<Object> deleteByMsidth(String msidth) throws Exception;

	public ResponseEntity<Object> update(MedicaoDTO medicaoDTO) throws IOException, Exception;

	public List<Medicao> findAll() throws IOException;

	public ResponseEntity<Object> findAllMedicaoDTO() throws IOException;

	public MedicaoDTO findByData(Date date) throws IOException, EmptyResultDataAccessException;
}