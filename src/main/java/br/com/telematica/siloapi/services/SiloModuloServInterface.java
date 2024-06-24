package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.SiloModuloModel;
import br.com.telematica.siloapi.model.dto.SiloModuloDTO;

public interface SiloModuloServInterface {

	ResponseEntity<SiloModuloDTO> save(SiloModuloModel object) throws IOException;

	ResponseEntity<SiloModuloDTO> delete(Long codigo) throws IOException;

	ResponseEntity<SiloModuloDTO> update(Long codigo, SiloModuloModel object) throws IOException;

	ResponseEntity<List<SiloModuloDTO>> findAll();

	ResponseEntity<SiloModuloDTO> findId(Long codigo);

	ResponseEntity<Page<SiloModuloDTO>> siloModuloFindAllPaginado(String searchTerm, Pageable pageable);

}
