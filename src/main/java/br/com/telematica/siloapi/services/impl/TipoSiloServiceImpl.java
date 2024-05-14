package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import br.com.telematica.siloapi.model.entity.TipoSiloEntity;
import br.com.telematica.siloapi.repository.TipoSiloRepository;
import br.com.telematica.siloapi.services.TipoSiloInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;

@Service
public class TipoSiloServiceImpl implements TipoSiloInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(TipoSiloServiceImpl.class);
	@Autowired
	private TipoSiloRepository tipoSiloRepository;

	@Override
	public ResponseEntity<GenericResponseModel> save(TipoSiloDTO tipoSiloDTO) throws RuntimeException {
		try {

			if (tipoSiloDTO == null) {
				logger.error("Tipo Silo está nula.");
				throw new RuntimeException("Tipo Silo está nula.");
			}
			var entity = new TipoSiloEntity(tipoSiloDTO.getCodigo(), tipoSiloDTO.getEmpresa(), tipoSiloDTO.getDescricao());
			var result = tipoSiloRepository.save(entity);

			logger.info("Tipo Silo salva com sucesso." + result);
			return MessageResponse.sucess(new TipoSiloDTO(result.getTsicod(), result.getEmpcod(), result.getTsides()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<GenericResponseModel> deleteByTsicod(Integer codigo) throws IOException {
		if (codigo == null) {
			logger.error("O ID do tipo silo está nulo.");
			throw new IOException("O ID do tipo silo está nulo.");
		}
		try {
			tipoSiloRepository.deleteByTsicod(codigo);
			return MessageResponse.sucess(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar o tipo silo com o ID fornecido. Error: " + e.getCause());
			throw new IOException("Não foi possível encontrar o tipo silo com o ID fornecido.", e);
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<GenericResponseModel> update(TipoSiloDTO tipoSiloDTO) throws IOException {
		if (tipoSiloDTO == null) {
			logger.error("Tipo Silo está nula.");
			throw new RuntimeException("Tipo Silo está nulo.");
		}
		try {
			var entity = new TipoSiloEntity(tipoSiloDTO.getCodigo(), tipoSiloDTO.getEmpresa(), tipoSiloDTO.getDescricao());
			var result = tipoSiloRepository.save(entity);
			logger.info("Tipo Silo atualizado com sucesso." + result);
			return MessageResponse.sucess(new TipoSiloDTO(result.getTsicod(), result.getEmpcod(), result.getTsides()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public List<TipoSiloEntity> findAll() throws IOException {
		return tipoSiloRepository.findAll();
	}

	@Override
	public ResponseEntity<GenericResponseModel> findAllTipoSiloDTO() throws IOException {
		try {
			return MessageResponse.sucess(tipoSiloRepository.findAll().stream().map(this::convertToTipoSiloDTO).collect(Collectors.toList()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public TipoSiloDTO findById(Integer id) throws IOException, EmptyResultDataAccessException {
		if (id == null) {
			logger.error("Id está nulo.");
			throw new IOException("Id está nulo.");
		}
		var result = tipoSiloRepository.findById(id).orElse(null);

		if (result == null) {
			logger.error("Tipo Silo não encontrada.");
			throw new EmptyResultDataAccessException("Tipo Silo não encontrada.", 1);
		}
		return new TipoSiloDTO(result.getTsicod(), result.getEmpcod(), result.getTsides());
	}

	private TipoSiloDTO convertToTipoSiloDTO(TipoSiloEntity siloDTOEntity) {
		return new TipoSiloDTO(siloDTOEntity);
	}

}
