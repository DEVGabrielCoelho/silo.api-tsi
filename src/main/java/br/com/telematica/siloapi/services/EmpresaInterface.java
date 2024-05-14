package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.model.entity.EmpresaEntity;
import jakarta.transaction.Transactional;

public interface EmpresaInterface {

	public ResponseEntity<GenericResponseModel> salvar(EmpresaDTO empresa) throws RuntimeException;

	@Transactional
	public ResponseEntity<GenericResponseModel> deleteByEmpcod(Integer codigo) throws IOException;

	public ResponseEntity<GenericResponseModel> update(EmpresaDTO empresa) throws IOException;

	public List<EmpresaEntity> findAll() throws IOException;

	public ResponseEntity<GenericResponseModel> findAllEmpresaDTO();

	public EmpresaDTO findById(Integer id) throws IOException, EmptyResultDataAccessException;

}
