package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.entity.PlantaEntity;
import jakarta.transaction.Transactional;

public interface PlantaInterface {

	public ResponseEntity<GenericResponseModel> save(PlantaDTO planta) throws RuntimeException;

	@Transactional
	public ResponseEntity<GenericResponseModel> deleteByPlacod(Integer codigo) throws IOException;

	public ResponseEntity<GenericResponseModel> update(PlantaDTO planta) throws IOException;

	public List<PlantaEntity> findAll() throws IOException;

	public ResponseEntity<GenericResponseModel> findAllPlantaDTO() throws IOException;

	public ResponseEntity<GenericResponseModel> findById(Integer id) throws IOException, EmptyResultDataAccessException;

}
