package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.entity.Planta;
import br.com.telematica.siloapi.repository.PlantaRepository;
import br.com.telematica.siloapi.services.PlantaServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;

@Service
public class PlantaServiceImpl implements PlantaServInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(PlantaServiceImpl.class);
	@Autowired
	private PlantaRepository plantaRepository;

	@Override
	public ResponseEntity<Object> save(PlantaDTO planta) throws RuntimeException {
		try {

			if (planta == null) {
				logger.error("Planta está nula.");
				return MessageResponse.badRequest("Planta está nula.");
			}
			var entity = new Planta(planta.getCodigo(), planta.getNome(), planta.getCodigoEmpresa());
			var result = plantaRepository.save(entity);

			logger.info("Planta salva com successo." + result);
			return MessageResponse.success(new PlantaDTO(result.getPlacod(), result.getEmpcod(), result.getPlanom()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Object> deleteByPlacod(Integer codigo) throws IOException {
		if (codigo == null) {
			logger.error("O ID da planta está nulo.");
			return MessageResponse.badRequest("O ID da planta está nulo.");
		}
		try {
			plantaRepository.removeByPlacod(codigo);

			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a planta com o ID fornecido. Error: " + e.getCause());
			return MessageResponse.notFound("Não foi possível encontrar a planta com o ID fornecido." + e.getMessage());
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Object> update(PlantaDTO planta) throws IOException {
		try {
			if (planta == null) {
				logger.error("Planta está nula.");
				return MessageResponse.badRequest("Planta está nulo.");
			}
			var entity = new Planta(planta.getCodigo(), planta.getNome(), planta.getCodigoEmpresa());
			var result = plantaRepository.save(entity);
			logger.info("Planta atualizado com successo." + result);

			return MessageResponse.success(new PlantaDTO(result.getPlacod(), result.getEmpcod(), result.getPlanom()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public List<Planta> findAll() throws IOException {
		return plantaRepository.findAll();
	}

	@Override
	public ResponseEntity<Object> findAllPlantaDTO() throws IOException {
		try {

			return MessageResponse.success(plantaRepository.findAll().stream().map(this::convertToPlantaDTO).collect(Collectors.toList()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Object> findById(Integer id) throws IOException, EmptyResultDataAccessException {
		try {
			if (id == null) {
				logger.error("Id está nulo.");
				return MessageResponse.badRequest("Id está nulo.");
			}
			var result = plantaRepository.findById(id).orElse(null);

			if (result == null) {
				logger.error("Planta não encontrada.");
				throw new EmptyResultDataAccessException("Planta não encontrada.", 1);
			}

			return MessageResponse.success(new PlantaDTO(result.getPlacod(), result.getEmpcod(), result.getPlanom()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	private PlantaDTO convertToPlantaDTO(Planta plantaEntity) {
		return new PlantaDTO(plantaEntity);
	}
}
