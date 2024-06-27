package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.handler.AbrangenciaHandler;
import br.com.telematica.siloapi.model.SiloModel;
import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.entity.Silo;
import br.com.telematica.siloapi.records.CheckAbrangenciaRec;
import br.com.telematica.siloapi.repository.SiloRepository;
import br.com.telematica.siloapi.services.SiloServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SiloServiceImpl implements SiloServInterface {

	private static final Logger logger = LoggerFactory.getLogger(SiloServiceImpl.class);

	@Autowired
	private SiloRepository siloRepository;

	@Autowired
	private TipoSiloServiceImpl tipoSiloService;

	@Autowired
	private PlantaServiceImpl plantaService;

	@Autowired
	private AbrangenciaHandler abrangenciaHandler;

	private static final String SILO = "SILO";

	private CheckAbrangenciaRec findAbrangencia() {
		return abrangenciaHandler.checkAbrangencia(SILO);
	}

	@Override
	public ResponseEntity<SiloDTO> save(SiloModel siloModel) throws IOException {
		try {
			var tipoSilo = tipoSiloService.findEntity(siloModel.getTipoSilo());
			if (tipoSilo == null)
				throw new EntityNotFoundException("Tipo Silo não encontrado.");
			var planta = plantaService.findEntity(siloModel.getPlanta());
			if (planta == null)
				throw new EntityNotFoundException("Planta não encontrado.");
			var entity = new Silo(null, tipoSilo, siloModel.getNome(), planta, siloModel.getLatitude(), siloModel.getLongitude());
			var result = siloRepository.save(entity);

			logger.info("Silo salvo com sucesso: " + result);
			return MessageResponse.success(new SiloDTO(result));
		} catch (EntityNotFoundException e) {
			logger.error("Erro ao salvar o Silo: ", e);
			throw new IOException("Erro ao salvar o Silo: " + siloModel, e);
		}
	}

	@Override
	public ResponseEntity<SiloDTO> deleteByPlacod(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código do Silo está nulo.");
		try {
			var silo = findCodigo(codigo);
			siloRepository.delete(silo);
			logger.info("Silo deletado com sucesso: " + silo);
			return MessageResponse.success(null);
		} catch (EntityNotFoundException e) {
			logger.error("Não foi possível encontrar o silo com o ID fornecido: ", e);
			throw new EntityNotFoundException("Não foi possível encontrar o silo com o ID fornecido.", e);
		} catch (Exception e) {
			logger.error("Erro ao deletar o Silo: ", e);
			throw new IOException("Erro ao deletar o Silo: " + codigo, e);
		}
	}

	@Override
	public ResponseEntity<SiloDTO> update(Long codigo, SiloModel siloModel) throws IOException {
		try {
			var silo = findCodigo(codigo);

			var tipoSilo = tipoSiloService.findEntity(siloModel.getTipoSilo());
			if (tipoSilo == null)
				throw new EntityNotFoundException("Tipo Silo não encontrado.");
			var planta = plantaService.findEntity(siloModel.getPlanta());
			if (planta == null)
				throw new EntityNotFoundException("Planta não encontrado.");
			var entity = new Silo(silo.getSilcod(), tipoSilo, siloModel.getNome(), planta, siloModel.getLatitude(), siloModel.getLongitude());
			var result = siloRepository.save(entity);

			logger.info("Silo atualizado com sucesso: " + result);
			return MessageResponse.success(new SiloDTO(result));
		} catch (EntityNotFoundException e) {
			logger.error("Erro ao atualizar o Silo: ", e);
			throw new IOException("Erro ao atualizar o Silo " + codigo + ": " + siloModel, e);
		}
	}

	@Override
	public List<SiloDTO> sendListAbrangenciaSiloDTO() throws IOException {
		return siloRepository.findAll().stream().map(SiloDTO::new).toList();
	}

	@Override
	public ResponseEntity<List<SiloDTO>> findAllSiloDTO() throws IOException {
		Specification<Silo> spec = Specification.where(null);

		if (findAbrangencia().isHier() == 0) {
			spec = spec.and(Silo.filterByFields(null, null));
		} else {
			spec = spec.and(Silo.filterByFields(null, findAbrangencia().listAbrangencia()));
		}

		List<SiloDTO> siloDTOList = siloRepository.findAll(spec).stream().map(this::abrangenciaSilo).toList();
		return MessageResponse.success(siloDTOList);
	}

	@Override
	public ResponseEntity<SiloDTO> findById(Long codigo) {
		Objects.requireNonNull(codigo, "Código do Silo está nulo.");
		Long idPermitted = abrangenciaHandler.findIdAbrangenciaPermi(findAbrangencia(), codigo);
		if (idPermitted == null) {
			return MessageResponse.success(null);
		}
		var result = findCodigo(idPermitted);
		return MessageResponse.success(new SiloDTO(result));
	}

	@Override
	public ResponseEntity<Page<SiloDTO>> siloFindAllPaginado(String searchTerm, Pageable pageable) {
		try {
			Specification<Silo> spec = Specification.where(null);

			if (findAbrangencia().isHier() == 0) {
				spec = spec.and(Silo.filterByFields(searchTerm, null));
			} else {
				spec = spec.and(Silo.filterByFields(searchTerm, findAbrangencia().listAbrangencia()));
			}

			Page<Silo> result = siloRepository.findAll(spec, pageable);
			return ResponseEntity.ok(result.map(this::abrangenciaSilo));
		} catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	public Silo findCodigo(Long codigo) {
		Objects.requireNonNull(codigo, "Código do silo está nulo.");
		return siloRepository.findById(codigo).orElse(null);
	}

	public SiloDTO abrangenciaSilo(Silo entity) {
		var tipoSiloDTO = tipoSiloService.findTipoSiloAbrangencia(entity.getTipoSilo().getTsicod());
		var plantaDTO = plantaService.findPlantaAbrangencia(entity.getPlanta().getPlacod());

		SiloDTO siloDTO = new SiloDTO();
		siloDTO.setCodigo(entity.getSilcod());
		siloDTO.setTipoSilo(tipoSiloDTO);
		siloDTO.setPlanta(plantaDTO);
		siloDTO.setNome(entity.getSilnom());
		siloDTO.setLongitude(entity.getSillon());
		siloDTO.setLatitude(entity.getSillat());

		return siloDTO;
	}

}
