package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.CustomMessageException;
import br.com.telematica.siloapi.model.PlantaModel;
import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.entity.Planta;
import br.com.telematica.siloapi.repository.PlantaRepository;
import br.com.telematica.siloapi.services.PlantaServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PlantaServiceImpl implements PlantaServInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(PlantaServiceImpl.class);
	@Autowired
	private PlantaRepository plantaRepository;
	@Autowired
	private EmpresaServiceImpl empresaServiceImpl;
	private static final String RECURSO = "Planta";

	@Override
	public ResponseEntity<PlantaDTO> save(PlantaModel planta) throws IOException {
		Objects.requireNonNull(planta.getEmpresa(), "Código da Empresa está nulo.");
		Objects.requireNonNull(planta.getNome(), "Nome da planta está nulo.");
		try {
			var entity = new Planta();
			var emp = empresaServiceImpl.findById(planta.getEmpresa());
			entity.plantaUpdateOrSave(planta.getNome(), emp);
			var result = plantaRepository.save(entity);

			logger.info("Planta salva com successo." + result);
			return MessageResponse.success(new PlantaDTO(result));
		} catch (Exception e) {
			throw CustomMessageException.exceptionIOException("criar", RECURSO, planta, e);

		}
	}

	@Override
	public ResponseEntity<PlantaDTO> deleteByPlacod(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código da Planta está nulo.");
		try {
			var entity = findEntity(codigo);
			if (entity == null)
				throw new EntityNotFoundException("Não foi possível encontrar a Planta com o ID fornecido.");

			plantaRepository.removeByPlacod(entity.getPlacod());

			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a Planta com o ID fornecido. Error: " + e.getCause());
			throw CustomMessageException.exceptionEntityNotFoundException(codigo, RECURSO, e);
		} catch (Exception e) {
			logger.error("Erro ao deletar a Planta. Erro: ", e);
			throw CustomMessageException.exceptionIOException("Deletar", RECURSO, codigo, e);
		}
	}

	@Override
	public ResponseEntity<PlantaDTO> update(Long codigo, PlantaModel planta) throws IOException {
		Objects.requireNonNull(codigo, "Código da Planta está nulo.");
		Objects.requireNonNull(planta.getEmpresa(), "Código da Empresa está nulo.");
		Objects.requireNonNull(planta.getNome(), "Nome da planta está nulo.");
		try {

			var entity = findEntity(codigo);
			var emp = empresaServiceImpl.findById(planta.getEmpresa());
			entity.plantaUpdateOrSave(planta.getNome(), emp);
			var result = plantaRepository.save(entity);
			logger.info("Planta atualizado com successo." + result);

			return MessageResponse.success(new PlantaDTO(result));
		} catch (Exception e) {
			throw CustomMessageException.exceptionCodigoIOException("atualizar", RECURSO, codigo, planta, e);
		}
	}

	@Override
	public List<Planta> findAll() throws IOException {
		return plantaRepository.findAll();
	}

	@Override
	public ResponseEntity<List<PlantaDTO>> findAllPlantaDTO() throws IOException {
		return MessageResponse.success(plantaRepository.findAll().stream().map(this::convertToPlantaDTO).collect(Collectors.toList()));

	}

	@Override
	public ResponseEntity<PlantaDTO> findById(Long codigo) throws IOException, EmptyResultDataAccessException {
		Objects.requireNonNull(codigo, "Código da Plata está nulo.");

		var result = findEntity(codigo);

		if (result == null) {
			logger.error("Planta não encontrada.");
			return MessageResponse.success(null);
		}

		return MessageResponse.success(new PlantaDTO(result));

	}

	private PlantaDTO convertToPlantaDTO(Planta plantaEntity) {
		return new PlantaDTO(plantaEntity);
	}

	Planta findEntity(Long codigo) {
		Objects.requireNonNull(codigo, "Código está nulo.");
		var result = plantaRepository.findById(codigo).orElse(null);

		if (result == null) {
			logger.error("Tipo Silo não encontrada.");
			return null;
		}
		return result;
	}
}
