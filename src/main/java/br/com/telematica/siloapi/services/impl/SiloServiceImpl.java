package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.entity.Silo;
import br.com.telematica.siloapi.repository.SiloRepository;
import br.com.telematica.siloapi.services.SiloServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;

@Service
public class SiloServiceImpl implements SiloServInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(SiloServiceImpl.class);
	@Autowired
	private SiloRepository siloRepository;

	@Override
	public ResponseEntity<Object> save(SiloDTO siloDTO) throws RuntimeException {
		try {

			if (siloDTO == null) {
				logger.error("Silo está nula.");
				throw new RuntimeException("Silo está nula.");
			}

			var entity = new Silo(siloDTO.getCodigo(), siloDTO.getTipoSilo(), siloDTO.getNome(), siloDTO.getCodiPlanta());
			var result = siloRepository.save(entity);

			logger.info("Silo salva com successo." + result);
			return MessageResponse.success(new SiloDTO(result.getSilcod(), result.getTsicod(), result.getPlacod(), result.getSilnom()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Object> deleteByPlacod(Integer codigo) throws IOException {
		if (codigo == null) {
			logger.error("O ID do silo está nulo.");
			return MessageResponse.badRequest("O ID do silo está nulo.");
		}
		try {
			siloRepository.deleteByPlacod(codigo);
			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar o silo com o ID fornecido. Error: " + e.getCause());
			return MessageResponse.notFound("Não foi possível encontrar o silo com o ID fornecido." + e.getMessage());
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Object> update(SiloDTO siloDTO) throws IOException {
		if (siloDTO == null) {
			logger.error("Silo está nula.");
			return MessageResponse.badRequest("Silo está nulo.");
		}
		try {
			var entity = new Silo(siloDTO.getCodigo(), siloDTO.getTipoSilo(), siloDTO.getNome(), siloDTO.getCodiPlanta());
			var result = siloRepository.save(entity);
			logger.info("Silo atualizado com successo." + result);
			return MessageResponse.success(new SiloDTO(result.getSilcod(), result.getTsicod(), result.getPlacod(), result.getSilnom()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public List<Silo> findAll() throws IOException {
		return siloRepository.findAll();
	}

	@Override
	public ResponseEntity<Object> findAllSiloDTO() throws IOException {
		try {
			return MessageResponse.success(siloRepository.findAll().stream().map(this::convertToSiloDTO).collect(Collectors.toList()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Object> findById(Integer id) throws IOException, EmptyResultDataAccessException {
		if (id == null) {
			logger.error("Id está nulo.");
			return MessageResponse.badRequest("Id está nulo.");
		}
		try {

			var result = siloRepository.findById(id).orElse(null);

			if (result == null) {
				logger.error("Silo não encontrada.");
				throw new EmptyResultDataAccessException("Silo não encontrada.", 1);
			}
			return MessageResponse.success(new SiloDTO(result.getSilcod(), result.getTsicod(), result.getPlacod(), result.getSilnom()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	private SiloDTO convertToSiloDTO(Silo siloDTOEntity) {
		return new SiloDTO(siloDTOEntity);
	}
}
