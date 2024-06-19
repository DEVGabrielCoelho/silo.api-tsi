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
import br.com.telematica.siloapi.model.TipoSiloModel;
import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import br.com.telematica.siloapi.model.entity.TipoSilo;
import br.com.telematica.siloapi.repository.TipoSiloRepository;
import br.com.telematica.siloapi.services.TipoSiloServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoSiloServiceImpl implements TipoSiloServInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(TipoSiloServiceImpl.class);

	@Autowired
	private TipoSiloRepository tipoSiloRepository;

	private static final String RECURSO = "Tipo Silo";

	@Override
	public ResponseEntity<TipoSiloDTO> save(TipoSiloModel tipoSiloDTO) throws RuntimeException, IOException {
		Objects.requireNonNull(tipoSiloDTO.getNome(), "Nome do tipo de Silo está nulo.");
		Objects.requireNonNull(tipoSiloDTO.getDescricao(), "Descrição está nulo.");

		try {

			var result = tipoSiloRepository.save(new TipoSilo(null, tipoSiloDTO.getNome(), tipoSiloDTO.getDescricao()));

			logger.info("Tipo Silo salvo com sucesso. " + result);
			return MessageResponse.success(new TipoSiloDTO(result));
		} catch (Exception e) {
			logger.error("Erro ao salvar o Tipo de Silo.", e);
			throw CustomMessageException.exceptionIOException("salvar", RECURSO, tipoSiloDTO, e);
		}
	}

	@Override
	public ResponseEntity<TipoSiloDTO> deleteByTsicod(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código do Tipo do Silo está nulo.");
		try {
			var tipoSilo = findEntity(codigo);

			if (tipoSilo == null)
				throw CustomMessageException.exceptionEntityNotFoundException(codigo, RECURSO, null);
			Long codiTipoSilo = tipoSilo.getTsicod();
			if (codiTipoSilo == null)
				throw CustomMessageException.exceptionEntityNotFoundException(codigo, RECURSO, null);

			tipoSiloRepository.deleteById(codiTipoSilo);
			logger.info("Tipo Silo com ID " + codigo + " deletado com sucesso.");

			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar o tipo silo com o ID fornecido. Erro: ", e);
			throw CustomMessageException.exceptionEntityNotFoundException(codigo, RECURSO, e);
		} catch (Exception e) {
			logger.error("Erro ao deletar o tipo do silo. Erro: ", e);
			throw CustomMessageException.exceptionIOException("deletar", RECURSO, codigo, e);
		}
	}

	@Override
	public ResponseEntity<TipoSiloDTO> update(Long codigo, TipoSiloModel tipoSiloDTO) throws IOException {
		Objects.requireNonNull(codigo, "Código do Tipo do Silo está nulo.");
		Objects.requireNonNull(tipoSiloDTO.getNome(), "Código da Empresa está nulo.");
		Objects.requireNonNull(tipoSiloDTO.getDescricao(), "Descrição está nulo.");

		try {

			var resultEntity = tipoSiloRepository.findById(codigo).orElse(null);
			if (resultEntity == null)
				throw CustomMessageException.exceptionEntityNotFoundException(codigo, RECURSO, null);

			var entity = resultEntity.tipoSiloEntity(tipoSiloDTO.getNome(), tipoSiloDTO.getDescricao());
			var result = tipoSiloRepository.save(entity);

			logger.info("Tipo Silo atualizado com sucesso. " + result);
			return MessageResponse.success(new TipoSiloDTO(result));
		} catch (Exception e) {
			logger.error("Erro ao atualizar o Tipo de Silo.", e);
			throw CustomMessageException.exceptionCodigoIOException("atualizar", RECURSO, codigo, tipoSiloDTO, e);
		}
	}

	@Override
	public ResponseEntity<List<TipoSiloDTO>> findAllTipoSiloDTO() throws IOException {
		return MessageResponse.success(tipoSiloRepository.findAll().stream().map(this::convertToTipoSiloDTO).collect(Collectors.toList()));

	}

	@Override
	public ResponseEntity<TipoSiloDTO> findById(Long codigo) throws IOException, EntityNotFoundException {
		Objects.requireNonNull(codigo, "Código do Tipo do Silo está nulo.");
		var result = tipoSiloRepository.findById(codigo).orElse(null);

		if (result == null) {
			logger.error("Tipo Silo não encontrada.");
			return MessageResponse.success(null);
		}
		return MessageResponse.success(new TipoSiloDTO(result));
	}

	private TipoSiloDTO convertToTipoSiloDTO(TipoSilo siloDTOEntity) {
		return new TipoSiloDTO(siloDTOEntity);
	}

	TipoSilo findEntity(Long codigo) {
		Objects.requireNonNull(codigo, "Código está nulo.");
		var result = tipoSiloRepository.findById(codigo).orElse(null);

		if (result == null) {
			logger.error("Tipo Silo não encontrada.");
			return null;
		}
		return result;
	}

}
