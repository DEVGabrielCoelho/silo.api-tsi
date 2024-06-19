package br.com.telematica.siloapi.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface SiloModuloServInterface {

	ResponseEntity<Object> save(Object object);

	ResponseEntity<Object> deleteByPlacod(Long codigo);

	ResponseEntity<Object> update(Long codigo, Object object);

	ResponseEntity<List<Object>> findAllSiloDTO();

	ResponseEntity<Object> findById(Long codigo);

}
