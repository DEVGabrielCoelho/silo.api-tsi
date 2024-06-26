package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.CustomMessageException;
import br.com.telematica.siloapi.handler.AbrangenciaHandler;
import br.com.telematica.siloapi.model.PlantaModel;
import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.entity.Planta;
import br.com.telematica.siloapi.records.CheckAbrangenciaRec;
import br.com.telematica.siloapi.repository.PlantaRepository;
import br.com.telematica.siloapi.services.PlantaServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PlantaServiceImpl implements PlantaServInterface {

	private static final Logger logger = LoggerFactory.getLogger(PlantaServiceImpl.class);
	private static final String RECURSO = "Planta";

	@Autowired
	private PlantaRepository plantaRepository;

	@Autowired
	private EmpresaServiceImpl empresaServiceImpl;

	@Autowired
	private AbrangenciaHandler abrangenciaHandler;

	private static final String PLANTA = "PLANTA";

	public CheckAbrangenciaRec findAbrangencia() {
		return abrangenciaHandler.checkAbrangencia(PLANTA);
	}

	@Override
	public ResponseEntity<PlantaDTO> save(PlantaModel planta) throws IOException {
		validatePlantaFields(planta);

		var emp = empresaServiceImpl.findById(planta.getEmpresa());
		if (emp == null)
			throw new EntityNotFoundException("Empresa não encontrada.");
		var entity = new Planta();
		entity.plantaUpdateOrSave(planta.getNome(), emp);
		Planta savedPlanta = plantaRepository.save(entity);
		logger.info("Planta salva com sucesso: " + savedPlanta);
		return MessageResponse.create(new PlantaDTO(savedPlanta));
	}

	@Override
	public ResponseEntity<PlantaDTO> deleteByPlacod(Long codigo) throws IOException {
		try {
			var entity = findEntity(codigo);
			if (entity == null) {
				throw new EntityNotFoundException("Não foi possível encontrar a Planta com o ID fornecido.");
			}

			plantaRepository.removeByPlacod(entity.getPlacod());
			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a Planta com o ID fornecido. Error: ", e);
			throw CustomMessageException.exceptionEntityNotFoundException(codigo, RECURSO, e);
		}
	}

	@Override
	public ResponseEntity<PlantaDTO> update(Long codigo, PlantaModel planta) throws IOException {
		Objects.requireNonNull(codigo, "Código da Planta está nulo.");
		validatePlantaFields(planta);

		var entity = findEntity(codigo);
		if (entity == null)
			throw new EntityNotFoundException("Planta não encontrada.");
		var emp = empresaServiceImpl.findById(planta.getEmpresa());
		if (emp == null)
			throw new EntityNotFoundException("Empresa não encontrada.");
		entity.plantaUpdateOrSave(planta.getNome(), emp);

		Planta updatedPlanta = plantaRepository.save(entity);
		logger.info("Planta atualizada com sucesso: " + updatedPlanta);

		return MessageResponse.success(new PlantaDTO(updatedPlanta));

	}

	@Override
	public List<Planta> findAll() throws IOException {
		return plantaRepository.findAll();
	}

	@Override
	public ResponseEntity<List<PlantaDTO>> findAllPlantaDTO() throws IOException {

		Specification<Planta> spec = Specification.where(null);

		if (findAbrangencia().isHier() == 0) {
			spec = spec.and(Planta.filterByFields(null, null));
		} else {
			spec = spec.and(Planta.filterByFields(null, findAbrangencia().listAbrangencia()));
		}
		List<PlantaDTO> plantaDTOs = plantaRepository.findAll(spec).stream().map(this::convertPlantaDTO)
				.collect(Collectors.toList());
		return MessageResponse.success(plantaDTOs);
	}

	@Override
	public ResponseEntity<Page<PlantaDTO>> plantaFindAllPaginado(String searchTerm, Pageable pageable)
			throws EntityNotFoundException, IOException {

		Specification<Planta> spec = Specification.where(null);

		if (findAbrangencia().isHier() == 0) {
			spec = spec.and(Planta.filterByFields(searchTerm, null));
		} else {
			spec = spec.and(Planta.filterByFields(searchTerm, findAbrangencia().listAbrangencia()));
		}
		Page<Planta> result = plantaRepository.findAll(spec, pageable);
		return ResponseEntity.ok(result.map(this::convertPlantaDTO));
	}

	@Override
	public ResponseEntity<PlantaDTO> findById(Long codigo) throws IOException, EmptyResultDataAccessException {
		Objects.requireNonNull(codigo, "Código da Planta está nulo.");

		Long idPermitted = abrangenciaHandler.findIdAbrangenciaPermi(findAbrangencia(), codigo);
		if (idPermitted == null) {
			// throw new EntityNotFoundException("Acesso negado ou entidade não
			// encontrada.");
			return MessageResponse.success(null);
		}
		Planta result = findEntity(idPermitted);
		if (result == null) {
			logger.error("Planta não encontrada.");
			return MessageResponse.success(null);
		}

		return MessageResponse.success(convertPlantaDTO(result));
	}

	private PlantaDTO convertPlantaDTO(Planta plantaEntity) {
		return new PlantaDTO(plantaEntity, empresaServiceImpl.findByIdAbrangencia(plantaEntity.getEmpresa()));
	}

	Planta findEntity(Long codigo) {
		Objects.requireNonNull(codigo, "Código está nulo.");
		return plantaRepository.findById(codigo).orElse(null);
	}

	private void validatePlantaFields(PlantaModel planta) {
		Objects.requireNonNull(planta.getEmpresa(), "Código da Empresa está nulo.");
		Objects.requireNonNull(planta.getNome(), "Nome da planta está nulo.");
	}

	PlantaDTO findPlantaAbrangencia(Long codigo) {

		Long idPermitted = abrangenciaHandler.findIdAbrangenciaPermi(findAbrangencia(), codigo);
		if (idPermitted == null) {
			return null;
		}
		Planta result = findEntity(idPermitted);
		if (result == null) {
			logger.error("Planta não encontrada.");
			return null;
		}
		return new PlantaDTO(result, empresaServiceImpl.findByIdAbrangencia(result.getEmpresa()));
	}

}
