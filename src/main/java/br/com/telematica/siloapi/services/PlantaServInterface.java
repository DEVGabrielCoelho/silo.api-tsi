package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.entity.Planta;
import jakarta.transaction.Transactional;

public interface PlantaServInterface {

	public ResponseEntity<Object> save(PlantaDTO planta) throws RuntimeException;

	@Transactional
	public ResponseEntity<Object> deleteByPlacod(Integer codigo) throws IOException;

	public ResponseEntity<Object> update(PlantaDTO planta) throws IOException;

	public List<Planta> findAll() throws IOException;

	public ResponseEntity<Object> findAllPlantaDTO() throws IOException;

	public ResponseEntity<Object> findById(Integer id) throws IOException, EmptyResultDataAccessException;

}
