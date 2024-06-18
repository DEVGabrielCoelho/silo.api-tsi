package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.SiloModel;
import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.entity.Silo;

public interface SiloServInterface {

	ResponseEntity<SiloDTO> save(SiloModel siloDTO) throws IOException;

	ResponseEntity<SiloDTO> deleteByPlacod(Long codigo) throws IOException;

	ResponseEntity<SiloDTO> update(Long codigo, SiloModel siloDTO) throws IOException;

	List<Silo> findAll() throws IOException;

	ResponseEntity<List<SiloDTO>> findAllSiloDTO() throws IOException;

	ResponseEntity<SiloDTO> findById(Long codigo);

}
