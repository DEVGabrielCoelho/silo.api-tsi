package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.PendenciaDelete;
import br.com.telematica.siloapi.model.PendenciaModel;
import br.com.telematica.siloapi.model.dto.KeepAliveDTO;
import br.com.telematica.siloapi.model.dto.PendenciasDTO;
import br.com.telematica.siloapi.model.enums.StatusEnum;
import jakarta.persistence.EntityNotFoundException;

public interface PendenciaServInterface {

	ResponseEntity<KeepAliveDTO> findByKeepAlive(String num) throws EntityNotFoundException, IOException;

	ResponseEntity<List<PendenciasDTO>> findByAll();

	ResponseEntity<List<PendenciasDTO>> findByPentipAndpendel(String tipo);

	ResponseEntity<List<PendenciasDTO>> findByPenStaAndpendel(String status);

	ResponseEntity<PendenciasDTO> findById(Long id);

	ResponseEntity<List<PendenciasDTO>> findByPentipAndPenstaAndpendel(String tipo, String status);

	ResponseEntity<Page<PendenciasDTO>> findAllPaginado(String nome, Long modulo, Pageable pageable);

	ResponseEntity<PendenciasDTO> delete(PendenciaDelete delete) throws ParseException, IOException;

	ResponseEntity<PendenciasDTO> update(Long id, StatusEnum pendenciaModulo) throws ParseException;

	ResponseEntity<PendenciasDTO> save(PendenciaModel pendenciaModulo) throws EntityNotFoundException, IOException;
}
