package br.com.telematica.siloapi.services;

import java.io.IOException;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import br.com.telematica.siloapi.model.LoggerModel;
import br.com.telematica.siloapi.model.dto.LoggerDTO;
import jakarta.persistence.EntityNotFoundException;

public interface LoggerServInterface {
  ResponseEntity<LoggerDTO> save(LoggerModel logModel) throws EntityNotFoundException, IOException;

  ResponseEntity<List<LoggerDTO>> findByAll() throws EntityNotFoundException, IOException;

  ResponseEntity<Page<LoggerDTO>> findByAllPaginado(Long smocod, String filtro, String startDate, String endDate,
      @NonNull Pageable pageable) throws EntityNotFoundException, IOException;

  ResponseEntity<List<LoggerDTO>> findAllByModulo(Long codigo);
}
