package br.com.telematica.siloapi.services;

import java.io.IOException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import jakarta.transaction.Transactional;

public interface EmpresaInterface {

	public ResponseEntity<Object> salvar(EmpresaDTO empresa) throws RuntimeException;

	@Transactional
	public ResponseEntity<Object> deleteByEmpcod(Integer codigo) throws IOException;

	public ResponseEntity<Object> update(EmpresaDTO empresa) throws IOException;

	public ResponseEntity<Object> findAllEmpresaDTO();

	public EmpresaDTO findById(Integer id) throws IOException, EmptyResultDataAccessException;

}
