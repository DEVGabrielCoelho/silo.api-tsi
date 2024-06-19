package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.SiloModuloModel;
import br.com.telematica.siloapi.model.dto.SiloModuloDTO;

public interface SiloModuloServInterface {

	ResponseEntity<SiloModuloDTO> save(SiloModuloModel object) throws IOException;

	ResponseEntity<SiloModuloDTO> deleteByPlacod(Long codigo) throws IOException;

	ResponseEntity<SiloModuloDTO> update(Long codigo, SiloModuloModel object) throws IOException;

	ResponseEntity<List<SiloModuloDTO>> findAllSiloDTO();

	ResponseEntity<SiloModuloDTO> findById(Long codigo);

}
