package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.entity.SiloEntity;
import br.com.telematica.siloapi.repository.SiloRepository;
import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;

@Service
public class SiloService {

	private static Logger logger = (Logger) LoggerFactory.getLogger(SiloService.class);
	@Autowired
	private SiloRepository siloRepository;

	public SiloDTO save(SiloDTO siloDTO) throws RuntimeException {
		try {

			if (siloDTO == null) {
				logger.error("Silo está nula.");
				throw new RuntimeException("Silo está nula.");
			}
			// Integer silcod, Integer tsicod, String silnom, Integer placod
			var entity = new SiloEntity(siloDTO.getCodigo(), siloDTO.getTipoSilo(), siloDTO.getNome(), siloDTO.getCodiPlanta());
			var result = siloRepository.save(entity);

			logger.info("Silo salva com sucesso." + result);
			return new SiloDTO(result.getSilcod(), result.getTsicod(), result.getPlacod(), result.getSilnom());
		} catch (Exception e) {
			logger.error("Ocorreu um erro ao salvar a Silo. Error: " + e.getCause());
			throw new RuntimeException("Exceção:" + e.getCause());
		}
	}

	@Transactional
	public void deleteByPlacod(Integer codigo) throws IOException {
		if (codigo == null) {
			logger.error("O ID do silo está nulo.");
			throw new IOException("O ID do silo está nulo.");
		}
		try {
			siloRepository.deleteByPlacod(codigo);

		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar o silo com o ID fornecido. Error: " + e.getCause());
			throw new IOException("Não foi possível encontrar o silo com o ID fornecido.", e);
		} catch (Exception e) {
			logger.error("Ocorreu um erro ao excluir o silo. Error: " + e.getCause());
			throw new IOException("Ocorreu um erro ao excluir o silo.", e.getCause());
		}
	}

	public SiloDTO update(SiloDTO siloDTO) throws IOException {
		if (siloDTO == null) {
			logger.error("Silo está nula.");
			throw new RuntimeException("Silo está nulo.");
		}
		var entity = new SiloEntity(siloDTO.getCodigo(), siloDTO.getTipoSilo(), siloDTO.getNome(), siloDTO.getCodiPlanta());
		var result = siloRepository.save(entity);
		logger.info("Silo atualizado com sucesso." + result);
		return new SiloDTO(result.getSilcod(), result.getTsicod(), result.getPlacod(), result.getSilnom());
	}

	public List<SiloEntity> findAll() throws IOException {
		return siloRepository.findAll();
	}

	public List<SiloDTO> findAllSiloDTO() throws IOException {
		return siloRepository.findAll().stream().map(this::convertToSiloDTO).collect(Collectors.toList());
	}

	public SiloDTO findById(Integer id) throws IOException, EmptyResultDataAccessException {
		if (id == null) {
			logger.error("Id está nulo.");
			throw new IOException("Id está nulo.");
		}
		var result = siloRepository.findById(id).orElse(null);

		if (result == null) {
			logger.error("Silo não encontrada.");
			throw new EmptyResultDataAccessException("Silo não encontrada.", 1);
		}
		return new SiloDTO(result.getSilcod(), result.getTsicod(), result.getPlacod(), result.getSilnom());
	}

	private SiloDTO convertToSiloDTO(SiloEntity siloDTOEntity) {
		return new SiloDTO(siloDTOEntity);
	}
}
