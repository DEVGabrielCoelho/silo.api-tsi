package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.entity.PlantaEntity;
import br.com.telematica.siloapi.repository.PlantaRepository;
import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;

@Service
public class PlantaService {

	private static Logger logger = (Logger) LoggerFactory.getLogger(PlantaService.class);
	@Autowired
	private PlantaRepository plantaRepository;

	public PlantaDTO save(PlantaDTO planta) throws RuntimeException {
		if (planta == null) {
			throw new RuntimeException("Planta está nula.");
		}
		var entity = new PlantaEntity(planta.getCodigo(), planta.getNome(), planta.getCodigoEmpresa());
		var result = plantaRepository.save(entity);

		logger.info("Planta salva com sucesso." + result);
		return new PlantaDTO(result.getPlacod(), result.getPlanom(), result.getEmpcod());
	}

	@Transactional
	public void deleteByPlacod(Integer codigo) throws IOException {
		if (codigo == null) {
			throw new IOException("O ID da planta está nulo.");
		}
		try {
			plantaRepository.removeByPlacod(codigo);

		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a planta com o ID fornecido. Error: " +  e.getCause());
			throw new IOException("Não foi possível encontrar a planta com o ID fornecido.", e);
		} catch (Exception e) {
			logger.error("Ocorreu um erro ao excluir a planta. Error: " +  e.getCause());
			throw new IOException("Ocorreu um erro ao excluir a planta.", e.getCause());
		}
	}

	public PlantaDTO update(PlantaDTO planta) throws IOException {
		if (planta == null) {
			throw new RuntimeException("Planta está nulo.");
		}
		var entity = new PlantaEntity(planta.getCodigo(), planta.getNome(), planta.getCodigoEmpresa());
		var result = plantaRepository.save(entity);
		logger.info("Planta atualizado com sucesso." + result);
		return new PlantaDTO(result.getPlacod(), result.getPlanom(), result.getEmpcod());
	}

	public List<PlantaEntity> findAll() throws IOException {
		return plantaRepository.findAll();
	}

	public List<PlantaDTO> findAllPlantaDTO() throws IOException {
		return plantaRepository.findAll().stream().map(this::convertToPlantaDTO).collect(Collectors.toList());
	}

	public PlantaDTO findById(Integer id) throws IOException, EmptyResultDataAccessException {
		if (id == null) {
			throw new IOException("Id está nulo.");
		}
		var result = plantaRepository.findById(id).orElse(null);

		if (result == null) {
			throw new EmptyResultDataAccessException("Planta não encontrada.", 1);
		}
        return new PlantaDTO(result.getPlacod(), result.getPlanom(), result.getEmpcod());
	}

	private PlantaDTO convertToPlantaDTO(PlantaEntity plantaEntity) {
		return new PlantaDTO(plantaEntity);
	}
}

