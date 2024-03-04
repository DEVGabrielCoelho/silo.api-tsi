package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import br.com.telematica.siloapi.model.entity.TipoSiloEntity;
import br.com.telematica.siloapi.repository.TipoSiloRepository;
import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;

@Service
public class TipoSiloService {

	private static Logger logger = (Logger) LoggerFactory.getLogger(TipoSiloService.class);
	@Autowired
	private TipoSiloRepository tipoSiloRepository;

	public TipoSiloDTO save(TipoSiloDTO tipoSiloDTO) throws RuntimeException {
		try {

			if (tipoSiloDTO == null) {
				logger.error("Tipo Silo está nula.");
				throw new RuntimeException("Tipo Silo está nula.");
			}
			// Integer silcod, Integer tsicod, String silnom, Integer placod
			var entity = new TipoSiloEntity(tipoSiloDTO.getCodigo(), tipoSiloDTO.getEmpresa(), tipoSiloDTO.getDescricao());
			var result = tipoSiloRepository.save(entity);

			logger.info("Tipo Silo salva com sucesso." + result);
			return new TipoSiloDTO(result.getTsicod(), result.getEmpcod(), result.getTsides());
		} catch (Exception e) {
			throw new RuntimeException("Exceção:" + e.getCause());
		}
	}

	@Transactional
	public void deleteByTsicod(Integer codigo) throws IOException {
		if (codigo == null) {
			logger.error("O ID do tipo silo está nulo.");
			throw new IOException("O ID do tipo silo está nulo.");
		}
		try {
			tipoSiloRepository.deleteByTsicod(codigo);

		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar o tipo silo com o ID fornecido. Error: " + e.getCause());
			throw new IOException("Não foi possível encontrar o tipo silo com o ID fornecido.", e);
		} catch (Exception e) {
			logger.error("Ocorreu um erro ao excluir o tipo silo. Error: " + e.getCause());
			throw new IOException("Ocorreu um erro ao excluir o tipo silo.", e.getCause());
		}
	}

	public TipoSiloDTO update(TipoSiloDTO tipoSiloDTO) throws IOException {
		if (tipoSiloDTO == null) {
			logger.error("Tipo Silo está nula.");
			throw new RuntimeException("Tipo Silo está nulo.");
		}
		var entity = new TipoSiloEntity(tipoSiloDTO.getCodigo(), tipoSiloDTO.getEmpresa(), tipoSiloDTO.getDescricao());
		var result = tipoSiloRepository.save(entity);
		logger.info("Tipo Silo atualizado com sucesso." + result);
		return new TipoSiloDTO(result.getTsicod(), result.getEmpcod(), result.getTsides());
	}

	public List<TipoSiloEntity> findAll() throws IOException {
		return tipoSiloRepository.findAll();
	}

	public List<TipoSiloDTO> findAllTipoSiloDTO() throws IOException {
		return tipoSiloRepository.findAll().stream().map(this::convertToTipoSiloDTO).collect(Collectors.toList());
	}

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
