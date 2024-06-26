package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.handler.AbrangenciaHandler;
import br.com.telematica.siloapi.model.SiloModuloModel;
import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.dto.SiloModuloDTO;
import br.com.telematica.siloapi.model.entity.Medicao;
import br.com.telematica.siloapi.model.entity.SiloModulo;
import br.com.telematica.siloapi.model.entity.TipoSilo;
import br.com.telematica.siloapi.model.enums.TipoSiloEnum;
import br.com.telematica.siloapi.records.CheckAbrangenciaRec;
import br.com.telematica.siloapi.repository.SiloModuloRepository;
import br.com.telematica.siloapi.services.SiloModuloServInterface;
import br.com.telematica.siloapi.utils.Utils;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SiloModuloServiceImpl implements SiloModuloServInterface {

	private static final Logger logger = LoggerFactory.getLogger(SiloModuloServiceImpl.class);

	@Autowired
	private SiloModuloRepository siloModuloRepository;

	@Autowired
	private SiloServiceImpl siloServiceImpl;
	@Autowired
	@Lazy
	private MedicaoServiceImpl medicaoServiceImpl;

	@Autowired
	private AbrangenciaHandler abrangenciaHandler;

	private static final String MODULO = "MODULO";
	private final CheckAbrangenciaRec check = abrangenciaHandler.checkAbrangencia(MODULO); 


	@Override
	public ResponseEntity<SiloModuloDTO> save(SiloModuloModel object) throws IOException {
		var silo = siloServiceImpl.findCodigo(object.getSilo());
		if (silo == null)
			throw new EntityNotFoundException("Silo não encontrado.");
		if (findEntityNSE(object.getNumSerie()) != null)
			throw new EntityNotFoundException("Número de Série já cadastrado.");

		var entity = new SiloModulo(null, silo, object.getDescricao(), object.getTotalSensor(), object.getNumSerie(),
				object.getTimeoutKeepAlive(), object.getTimeoutMedicao(), null, null, object.getGmt(),
				object.getCorKeepAlive(), object.getCorMedicao(), object.getStatus().getStatus());

		SiloModulo result = siloModuloRepository.save(entity);
		logger.info("Módulo do Silo salvo com sucesso: " + result);
		return MessageResponse.create(new SiloModuloDTO(result));

	}

	@Override
	public ResponseEntity<SiloModuloDTO> delete(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código do Módulo do Silo está nulo.");
		try {
			var entity = siloModuloRepository.findById(codigo).orElseThrow(() -> new EntityNotFoundException(
					"Não foi possível encontrar o módulo do silo com o ID fornecido: " + codigo));

			siloModuloRepository.delete(entity);
			logger.info("Módulo do Silo deletado com sucesso: " + entity);
			return MessageResponse.success(null);
		} catch (Exception e) {
			logger.error("Erro ao deletar o Módulo do Silo: ", e);
			throw new IOException("Erro ao deletar o Módulo do Silo com o ID: " + codigo, e);
		}
	}

	@Override
	public ResponseEntity<SiloModuloDTO> update(Long codigo, SiloModuloModel object) throws IOException {
		var silo = siloServiceImpl.findCodigo(object.getSilo());
		if (silo == null)
			throw new EntityNotFoundException("Silo não encontrado.");
		var siloModulo = siloModuloRepository.findById(codigo).orElseThrow(() -> new EntityNotFoundException(
				"Não foi possível encontrar o módulo do silo com o ID fornecido: " + codigo));

		var entity = new SiloModulo(siloModulo.getSmocod(), silo, object.getDescricao(), object.getTotalSensor(),
				object.getNumSerie(), object.getTimeoutKeepAlive(), object.getTimeoutMedicao(), null, null,
				object.getGmt(), object.getCorKeepAlive(), object.getCorMedicao(),
				object.getStatus().getStatus());

		SiloModulo result = siloModuloRepository.save(entity);
		logger.info("Módulo do Silo atualizado com sucesso: " + result);
		return MessageResponse.success(new SiloModuloDTO(result));

	}

	@Override
	public ResponseEntity<List<SiloModuloDTO>> findAll() {
		// check = abrangenciaHandler.checkAbrangencia(MODULO);
		Specification<SiloModulo> spec = Specification.where(null);

		if (check.isHier() == 0) {
			spec = spec.and(SiloModulo.filterByFields(null, null));
		} else {
			spec = spec.and(SiloModulo.filterByFields(null, check.listAbrangencia()));
		}
		List<SiloModulo> modulos = siloModuloRepository.findAll(spec);
		List<SiloModuloDTO> dtoList = modulos.stream().map(this::dtoCalc).collect(Collectors.toList());
		return MessageResponse.success(dtoList);
	}

	@Override
	public ResponseEntity<Page<SiloModuloDTO>> siloModuloFindAllPaginado(String searchTerm, Pageable pageable)
			throws EntityNotFoundException, IOException {
		Specification<SiloModulo> spec = Specification.where(null);
		// check = abrangenciaHandler.checkAbrangencia(MODULO);
		if (check.isHier() == 0) {
			spec = spec.and(SiloModulo.filterByFields(searchTerm, null));
		} else {
			spec = spec.and(SiloModulo.filterByFields(searchTerm, check.listAbrangencia()));
		}
		Page<SiloModulo> result = siloModuloRepository.findAll(spec, pageable);
		return ResponseEntity.ok(result.map(this::dtoCalc));
	}

	@Override
	public ResponseEntity<SiloModuloDTO> findId(Long codigo) {
		// var check = abrangenciaHandler.checkAbrangencia(MODULO);
		Long idPermitted = abrangenciaHandler.findIdAbrangenciaPermi(check, codigo);
		if (idPermitted == null) {
			return MessageResponse.success(null);
		}
		var siloModulo = findEntity(idPermitted);
		return MessageResponse.success(dtoCalc(siloModulo));
	}

	SiloModulo findEntity(Long codigo) {
		Objects.requireNonNull(codigo, "Código do Módulo está nulo.");
		return siloModuloRepository.findById(codigo).orElse(null);
	}

	SiloModulo findEntityNSE(String nse) {
		Objects.requireNonNull(nse, "Número de Série do Módulo está nulo.");
		return siloModuloRepository.findBySmonse(nse).orElse(null);
	}

	public void registerKeepAliveInModulo(SiloModulo modulo, Date date) throws EntityNotFoundException {
		var mod = siloModuloRepository.save(modulo.sireneModuloRegisterKeep(date));
		logger.info("Registro de último KeepAlive efetuado com sucesso: " + mod);
	}

	public void registerMedicaoInModulo(SiloModulo modulo, Date date) throws EntityNotFoundException {
		var mod = siloModuloRepository.save(modulo.sireneModuloRegisterMedicao(date));
		logger.info("Registro de última Medição efetuado com sucesso: " + mod);
	}

	public SiloModuloDTO dtoCalc(SiloModulo siloModulo) {
		TipoSilo tipoSilo = siloModulo.getSilo().getTipoSilo();
		TipoSiloEnum tipo = TipoSiloEnum.valueOf(tipoSilo.getTsitip());
		SiloModuloDTO siloModuloDTO = new SiloModuloDTO(siloModulo);
		SiloDTO silo = siloServiceImpl.abrangenciaSilo(siloModulo.getSilo());
		if (silo == null)
			siloModuloDTO.setSilo(null);
		else
			siloModuloDTO.setSilo(silo);

		Medicao ultimaMedicao = medicaoServiceImpl.ultimaMedicao(siloModulo);
		if (ultimaMedicao == null) {

			return siloModuloDTO;
		}
		double raio = tipoSilo.getTsirai();
		double largura = tipoSilo.getTsilar();
		double comprimento = tipoSilo.getTsicom();
		double alturaSilo = tipoSilo.getTsiach() - tipoSilo.getTsidse();
		double ultimaMed = ultimaMedicao.getMsidis() - tipoSilo.getTsidse();

		double volumeTotal = 0;
		double volumeStatus = 0;

		if (tipo == TipoSiloEnum.HORIZONTAL) {
			volumeTotal = Utils.calcularVolumeHorizontal(comprimento, largura, alturaSilo);
			volumeStatus = Utils.calcularVolumeHorizontal(comprimento, largura, ultimaMed);
		} else if (tipo == TipoSiloEnum.VERTICAL) {
			volumeTotal = Utils.calcularVolumeVertical(raio, alturaSilo);
			volumeStatus = Utils.calcularVolumeVertical(raio, ultimaMed);
		}

		siloModuloDTO.volumeSilo(volumeTotal, volumeStatus);

		return siloModuloDTO;
	}
}
