package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.PlantaModel;
import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.entity.Planta;

public interface PlantaServInterface {

	ResponseEntity<PlantaDTO> save(PlantaModel planta) throws IOException;

	ResponseEntity<PlantaDTO> deleteByPlacod(Long codigo) throws IOException;

	ResponseEntity<PlantaDTO> update(Long codigo, PlantaModel planta) throws IOException;

	List<Planta> findAll() throws IOException;

	ResponseEntity<List<PlantaDTO>> findAllPlantaDTO() throws IOException;

	ResponseEntity<PlantaDTO> findById(Long codigo) throws IOException, EmptyResultDataAccessException;

	ResponseEntity<Page<PlantaDTO>> plantaFindAllPaginado(String searchTerm, Pageable pageable);
}
