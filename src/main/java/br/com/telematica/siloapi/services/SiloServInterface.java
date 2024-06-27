package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.SiloModel;
import br.com.telematica.siloapi.model.dto.SiloDTO;

public interface SiloServInterface {

	ResponseEntity<SiloDTO> save(SiloModel siloDTO) throws IOException;

	ResponseEntity<SiloDTO> deleteByPlacod(Long codigo) throws IOException;

	ResponseEntity<SiloDTO> update(Long codigo, SiloModel siloDTO) throws IOException;

	List<SiloDTO> sendListAbrangenciaSiloDTO() throws IOException;

	ResponseEntity<List<SiloDTO>> findAllSiloDTO() throws IOException;

	ResponseEntity<SiloDTO> findById(Long codigo);

	ResponseEntity<Page<SiloDTO>> siloFindAllPaginado(String searchTerm, Pageable pageable);
}
