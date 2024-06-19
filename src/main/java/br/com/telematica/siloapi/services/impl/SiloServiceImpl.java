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

import com.google.gson.Gson;

import br.com.telematica.siloapi.model.SiloModel;
import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.entity.Silo;
import br.com.telematica.siloapi.repository.SiloRepository;
import br.com.telematica.siloapi.services.SiloServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SiloServiceImpl implements SiloServInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(SiloServiceImpl.class);
	@Autowired
	private SiloRepository siloRepository;
	@Autowired
	private TipoSiloServiceImpl tipoSiloService;
	@Autowired
	private PlantaServiceImpl plantaService;

	@Override
	public ResponseEntity<SiloDTO> save(SiloModel siloDTO) throws IOException {
		try {
			var tipoSilo = tipoSiloService.findEntity(siloDTO.getTipoSilo());
			var planta = plantaService.findEntity(siloDTO.getPlanta());

			var entity = new Silo(null, tipoSilo, siloDTO.getNome(), planta);
			var result = siloRepository.save(entity);

			logger.info("Silo salva com successo." + result);
			return MessageResponse.success(new SiloDTO(result));
		} catch (Exception e) {
			throw new IOException("Erro ao salvar o Silo." + new Gson().toJson(siloDTO), e);
		}
	}

	@Override
	public ResponseEntity<SiloDTO> deleteByPlacod(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código Silo está nulo.");
		try {
			var silo = findEntity(codigo);
			if (silo == null)
				throw new EntityNotFoundException("Não foi possível encontrar o silo com o ID " + codigo + " fornecido.");

			siloRepository.delete(silo);
			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar o silo com o ID fornecido. Error: " + e.getCause());
			throw new EntityNotFoundException("Não foi possível encontrar o silo com o ID fornecido.", e);
		} catch (Exception e) {
			throw new IOException("Erro ao salvar o Silo." + new Gson().toJson(codigo), e);
		}
	}

	@Override
	public ResponseEntity<SiloDTO> update(Long codigo, SiloModel siloModel) throws IOException {
		Objects.requireNonNull(codigo, "Código do Silo está nulo.");
		Objects.requireNonNull(siloModel.getTipoSilo(), "Código do Tipo do Silo está nulo.");
		Objects.requireNonNull(siloModel.getNome(), "Nome do Silo está nulo.");
		Objects.requireNonNull(siloModel.getPlanta(), "Código da planta está nulo.");

		try {
			var tipoSilo = tipoSiloService.findEntity(siloModel.getTipoSilo());
			var planta = plantaService.findEntity(siloModel.getPlanta());

			var entity = new Silo(codigo, tipoSilo, siloModel.getNome(), planta);
			var result = siloRepository.save(entity);
			logger.info("Silo atualizado com successo." + result);
			return MessageResponse.success(new SiloDTO(result));
		} catch (Exception e) {
			throw new IOException("Erro ao atualizar o Silo " + codigo + "." + new Gson().toJson(siloModel), e);
		}
	}

	@Override
	public List<Silo> findAll() throws IOException {
		return siloRepository.findAll();
	}

	@Override
	public ResponseEntity<List<SiloDTO>> findAllSiloDTO() throws IOException {
		return MessageResponse.success(siloRepository.findAll().stream().map(this::convertToSiloDTO).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<SiloDTO> findById(Long codigo) {
		Objects.requireNonNull(codigo, "Código do Silo está nulo.");

		var result = siloRepository.findById(codigo).orElse(null);

		if (result == null) {
			logger.error("Silo não encontrada.");
//			throw new EntityNotFoundException("Silo não encontrada.");
			return MessageResponse.success(null);
		}
		return MessageResponse.success(new SiloDTO(result));

	}

	private SiloDTO convertToSiloDTO(Silo siloDTOEntity) {
		return new SiloDTO(siloDTOEntity);
	}

	Silo findEntity(Long codigo) {
		var result = siloRepository.findById(codigo).orElse(null);

		if (result == null) {
			logger.error("Silo não encontrado.");
			return null;
		}

		return result;
	}
}
