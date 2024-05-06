package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.entity.PlantaEntity;
import br.com.telematica.siloapi.repository.PlantaRepository;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;

@Service
public class PlantaService {

	private static Logger logger = (Logger) LoggerFactory.getLogger(PlantaService.class);
	@Autowired
	private PlantaRepository plantaRepository;

	public ResponseEntity<GenericResponseModel> save(PlantaDTO planta) throws RuntimeException {
		try {

			if (planta == null) {
				logger.error("Planta está nula.");
				return MessageResponse.badRequest("Planta está nula.");
			}
			var entity = new PlantaEntity(planta.getCodigo(), planta.getNome(), planta.getCodigoEmpresa());
			var result = plantaRepository.save(entity);

			logger.info("Planta salva com sucesso." + result);
			return MessageResponse.sucess(new PlantaDTO(result.getPlacod(), result.getEmpcod(), result.getPlanom()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Transactional
	public ResponseEntity<GenericResponseModel> deleteByPlacod(Integer codigo) throws IOException {
		if (codigo == null) {
			logger.error("O ID da planta está nulo.");
			return MessageResponse.badRequest("O ID da planta está nulo.");
		}
		try {
			plantaRepository.removeByPlacod(codigo);

			return MessageResponse.sucess(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a planta com o ID fornecido. Error: " + e.getCause());
			return MessageResponse.notFound("Não foi possível encontrar a planta com o ID fornecido." + e.getMessage());
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	public ResponseEntity<GenericResponseModel> update(PlantaDTO planta) throws IOException {
		try {
			if (planta == null) {
				logger.error("Planta está nula.");
				return MessageResponse.badRequest("Planta está nulo.");
			}
			var entity = new PlantaEntity(planta.getCodigo(), planta.getNome(), planta.getCodigoEmpresa());
			var result = plantaRepository.save(entity);
			logger.info("Planta atualizado com sucesso." + result);

			return MessageResponse.sucess(new PlantaDTO(result.getPlacod(), result.getEmpcod(), result.getPlanom()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	public List<PlantaEntity> findAll() throws IOException {
		return plantaRepository.findAll();
	}

	public ResponseEntity<GenericResponseModel> findAllPlantaDTO() throws IOException {
		try {

			return MessageResponse.sucess(plantaRepository.findAll().stream().map(this::convertToPlantaDTO).collect(Collectors.toList()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	public ResponseEntity<GenericResponseModel> findById(Integer id) throws IOException, EmptyResultDataAccessException {
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

			return MessageResponse.sucess(new PlantaDTO(result.getPlacod(), result.getEmpcod(), result.getPlanom()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	private PlantaDTO convertToPlantaDTO(PlantaEntity plantaEntity) {
		return new PlantaDTO(plantaEntity);
	}
}
