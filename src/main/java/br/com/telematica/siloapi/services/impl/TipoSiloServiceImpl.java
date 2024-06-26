package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.CustomMessageException;
import br.com.telematica.siloapi.handler.AbrangenciaHandler;
import br.com.telematica.siloapi.model.TipoSiloModel;
import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import br.com.telematica.siloapi.model.entity.TipoSilo;
import br.com.telematica.siloapi.model.enums.TipoSiloEnum;
import br.com.telematica.siloapi.records.CheckAbrangenciaRec;
import br.com.telematica.siloapi.repository.TipoSiloRepository;
import br.com.telematica.siloapi.services.TipoSiloServInterface;
import br.com.telematica.siloapi.utils.Utils;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoSiloServiceImpl implements TipoSiloServInterface {

	private static final Logger logger = LoggerFactory.getLogger(TipoSiloServiceImpl.class);
	private static final String RECURSO = "Tipo Silo";

	@Autowired
	private AbrangenciaHandler abrangenciaHandler;

	public CheckAbrangenciaRec checagemFixaAbrangencia() throws EntityNotFoundException, IOException {
		return abrangenciaHandler.checkAbrangencia("TIPOSILO");
	}

	@Autowired
	private TipoSiloRepository tipoSiloRepository;

	@Override
	public ResponseEntity<TipoSiloDTO> save(TipoSiloModel tipoSiloModel) throws RuntimeException, IOException {
		try {
			TipoSilo entity = new TipoSilo();
			entity.setTsinom(tipoSiloModel.getNome());
			entity.setTsides(tipoSiloModel.getDescricao());
			entity.setTsitip(tipoSiloModel.getTipoSilo().getTipo());
			entity.setTsiach(Utils.converterMParaMm(tipoSiloModel.getAlturaCheio()));
			entity.setTsidse(Utils.converterMParaMm(tipoSiloModel.getDistanciaSensor()));

			if (tipoSiloModel.getTipoSilo() == TipoSiloEnum.HORIZONTAL)
				entity.tipoSiloHorizontal(tipoSiloModel.getLargura(), tipoSiloModel.getComprimento());
			if (tipoSiloModel.getTipoSilo() == TipoSiloEnum.VERTICAL)
				entity.tipoSiloVertical(tipoSiloModel.getRaio());

			TipoSilo result = tipoSiloRepository.save(entity);

			logger.info("Tipo Silo salvo com sucesso: " + result);
			return MessageResponse.success(new TipoSiloDTO(result));
		} catch (Exception e) {
			logger.error("Erro ao salvar o Tipo de Silo: ", e);
			throw CustomMessageException.exceptionIOException("salvar", RECURSO, tipoSiloModel, e);
		}
	}

	@Override
	public ResponseEntity<TipoSiloDTO> deleteByTsicod(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código do Tipo do Silo está nulo.");
		try {
			TipoSilo tipoSilo = findEntity(codigo);
			tipoSiloRepository.deleteById(tipoSilo.getTsicod());

			logger.info("Tipo Silo com ID " + codigo + " deletado com sucesso.");
			return MessageResponse.success(null);
		} catch (EntityNotFoundException e) {
			logger.error("Não foi possível encontrar o tipo silo com o ID fornecido: ", e);
			throw CustomMessageException.exceptionEntityNotFoundException(codigo, RECURSO, e);
		} catch (Exception e) {
			logger.error("Erro ao deletar o tipo do silo: ", e);
			throw CustomMessageException.exceptionIOException("deletar", RECURSO, codigo, e);
		}
	}

	@Override
	public ResponseEntity<TipoSiloDTO> update(Long codigo, TipoSiloModel tipoSiloModel) throws IOException {
		try {
			TipoSilo resultEntity = tipoSiloRepository.findById(codigo).orElseThrow(() -> CustomMessageException.exceptionEntityNotFoundException(codigo, RECURSO, null));

			resultEntity.setTsinom(tipoSiloModel.getNome());
			resultEntity.setTsides(tipoSiloModel.getDescricao());
			resultEntity.setTsitip(tipoSiloModel.getTipoSilo().getTipo());
			resultEntity.setTsiach(Utils.converterMParaMm(tipoSiloModel.getAlturaCheio()));
			resultEntity.setTsidse(Utils.converterMParaMm(tipoSiloModel.getDistanciaSensor()));

			if (tipoSiloModel.getTipoSilo() == TipoSiloEnum.HORIZONTAL)
				resultEntity.tipoSiloHorizontal(tipoSiloModel.getLargura(), tipoSiloModel.getComprimento());
			if (tipoSiloModel.getTipoSilo() == TipoSiloEnum.HORIZONTAL)
				resultEntity.tipoSiloVertical(tipoSiloModel.getRaio());
			TipoSilo result = tipoSiloRepository.save(resultEntity);

			logger.info("Tipo Silo atualizado com sucesso: " + result);
			return MessageResponse.success(new TipoSiloDTO(result));
		} catch (Exception e) {
			logger.error("Erro ao atualizar o Tipo de Silo: ", e);
			throw CustomMessageException.exceptionCodigoIOException("atualizar", RECURSO, codigo, tipoSiloModel, e);
		}
	}

	@Override
	public ResponseEntity<List<TipoSiloDTO>> findAllTipoSiloDTO() throws IOException {
		var check = checagemFixaAbrangencia();
		Specification<TipoSilo> spec = Specification.where(null);
		if (check.isHier() == 0) {
			spec = spec.and(TipoSilo.filterByFields(null, null));
		} else {
			spec = spec.and(TipoSilo.filterByFields(null, check.listAbrangencia()));
		}
		List<TipoSiloDTO> tipoSiloDTOList = tipoSiloRepository.findAll(spec).stream().map(this::convertToTipoSiloDTO).collect(Collectors.toList());
		return MessageResponse.success(tipoSiloDTOList);
	}

	@Override
	public ResponseEntity<TipoSiloDTO> findById(Long codigo) throws IOException, EntityNotFoundException {
		Objects.requireNonNull(codigo, "Código do Tipo do Silo está nulo.");
		var check = checagemFixaAbrangencia();
		Long idPermitted = abrangenciaHandler.findIdAbrangenciaPermi(check, codigo);
		if (idPermitted == null) {
			// throw new EntityNotFoundException("Acesso negado ou entidade não
			// encontrada.");
			return MessageResponse.success(null);
		}
		TipoSilo result = findEntity(idPermitted);

		return MessageResponse.success(new TipoSiloDTO(result));
	}

	@Override
	public ResponseEntity<Page<TipoSiloDTO>> tipoSiloFindAllPaginado(String searchTerm, Pageable pageable) throws EntityNotFoundException, IOException {
		var check = checagemFixaAbrangencia();
		Specification<TipoSilo> spec = Specification.where(null);
		if (check.isHier() == 0) {
			spec = spec.and(TipoSilo.filterByFields(searchTerm, null));
		} else {
			spec = spec.and(TipoSilo.filterByFields(searchTerm, check.listAbrangencia()));
		}
		Page<TipoSilo> result = tipoSiloRepository.findAll(spec, pageable);
		return ResponseEntity.ok(result.map(TipoSiloDTO::new));
	}

	private TipoSiloDTO convertToTipoSiloDTO(TipoSilo tipoSilo) {
		return new TipoSiloDTO(tipoSilo);
	}

	TipoSilo findEntity(Long codigo) {
		return tipoSiloRepository.findById(codigo).orElseThrow(() -> {
			logger.error("Tipo Silo não encontrado com o ID: " + codigo);
			return null;
		});
	}

}
