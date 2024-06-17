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

import br.com.telematica.siloapi.model.TipoSiloModel;
import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import br.com.telematica.siloapi.model.entity.TipoSiloEntity;
import br.com.telematica.siloapi.repository.TipoSiloRepository;
import br.com.telematica.siloapi.services.TipoSiloServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;

@Service
public class TipoSiloServiceImpl implements TipoSiloServInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(TipoSiloServiceImpl.class);
	@Autowired
	private TipoSiloRepository tipoSiloRepository;
	@Autowired
	private EmpresaServiceImpl empresaService;

	@Override
	public ResponseEntity<TipoSiloDTO> save(TipoSiloModel tipoSiloDTO) throws RuntimeException, IOException {
		Objects.requireNonNull(tipoSiloDTO.getEmpresa(), "Código da Empresa está nulo.");
		Objects.requireNonNull(tipoSiloDTO.getDescricao(), "Descrição está nulo.");

		try {
			var empresa = empresaService.findById(tipoSiloDTO.getEmpresa());
			if (empresa == null) {
				throw new IOException("Empresa não encontrada.");
			}

			var result = tipoSiloRepository.save(new TipoSiloEntity(null, empresa, tipoSiloDTO.getDescricao()));

			logger.info("Tipo Silo salvo com sucesso. " + result);
			return MessageResponse.success(new TipoSiloDTO(result));
		} catch (Exception e) {
			logger.error("Erro ao salvar o Tipo de Silo.", e);
			throw new IOException("Erro ao salvar o Tipo de Silo.", e);
		}
	}

	@Override
	public ResponseEntity<TipoSiloDTO> deleteByTsicod(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código do Tipo do Silo está nulo.");
		try {
			var tipoSilo = tipoSiloRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException("Não foi possível encontrar o tipo silo com o ID fornecido.", 1));

			if (tipoSilo == null)
				throw new EmptyResultDataAccessException("Não foi possível encontrar o tipo silo com o ID fornecido.", 1);
			Long codiTipoSilo = tipoSilo.getTsicod();
			if (codiTipoSilo == null)
				throw new EmptyResultDataAccessException("Não foi possível encontrar o tipo silo com o ID fornecido.", 1);

			tipoSiloRepository.deleteById(codiTipoSilo);
			logger.info("Tipo Silo com ID " + codigo + " deletado com sucesso.");

			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar o tipo silo com o ID fornecido. Erro: ", e);
			throw new IOException("Não foi possível encontrar o tipo silo com o ID fornecido.", e);
		} catch (Exception e) {
			logger.error("Erro ao deletar o tipo do silo. Erro: ", e);
			throw new IOException("Erro ao deletar o tipo do silo.", e);
		}
	}

	@Override
	public ResponseEntity<TipoSiloDTO> update(Long codigo, TipoSiloModel tipoSiloDTO) throws IOException {
		Objects.requireNonNull(codigo, "Código do Tipo do Silo está nulo.");
		Objects.requireNonNull(tipoSiloDTO.getEmpresa(), "Código da Empresa está nulo.");
		Objects.requireNonNull(tipoSiloDTO.getDescricao(), "Descrição está nulo.");

		try {
			var empresa = empresaService.findById(tipoSiloDTO.getEmpresa());
			if (empresa == null)
				throw new IOException("Empresa não encontrada.");

			var resultEntity = tipoSiloRepository.findById(codigo).orElse(null);
			if (resultEntity == null)
				throw new IOException("Tipo de Silo não encontrado");

			var entity = resultEntity.tipoSiloEntity(empresa, tipoSiloDTO.getDescricao());
			if (entity == null)
				throw new IOException("Erro ao atualizar a entidade do Tipo de Silo");

			var result = tipoSiloRepository.save(entity);

			logger.info("Tipo Silo atualizado com sucesso. " + result);
			return MessageResponse.success(new TipoSiloDTO(result));
		} catch (Exception e) {
			logger.error("Erro ao atualizar o Tipo de Silo.", e);
			throw new IOException("Erro ao atualizar o Tipo de Silo.", e);
		}
	}

	@Override
	public ResponseEntity<List<TipoSiloDTO>> findAllTipoSiloDTO() throws IOException {
		try {
			return MessageResponse.success(tipoSiloRepository.findAll().stream().map(this::convertToTipoSiloDTO).collect(Collectors.toList()));
		} catch (Exception e) {
			throw new IOException("Erro ao salvar o tipo do silo.");
		}
	}

	@Override
	public ResponseEntity<TipoSiloDTO> findById(Long codigo) throws IOException, EmptyResultDataAccessException {
		if (codigo == null) {
			logger.error("Id está nulo.");
			throw new IOException("Id está nulo.");
		}
		var result = tipoSiloRepository.findById(codigo).orElse(null);

		if (result == null) {
			logger.error("Tipo Silo não encontrada.");
			throw new EmptyResultDataAccessException("Tipo Silo não encontrada.", 1);
		}
		return MessageResponse.success(new TipoSiloDTO(result));
	}

	private TipoSiloDTO convertToTipoSiloDTO(TipoSiloEntity siloDTOEntity) {
		return new TipoSiloDTO(siloDTOEntity);
	}

}
