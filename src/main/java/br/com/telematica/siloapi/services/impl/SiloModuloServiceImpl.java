package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.SiloModuloModel;
import br.com.telematica.siloapi.model.dto.SiloModuloDTO;
import br.com.telematica.siloapi.model.entity.Medicao;
import br.com.telematica.siloapi.model.entity.SiloModulo;
import br.com.telematica.siloapi.model.entity.TipoSilo;
import br.com.telematica.siloapi.model.enums.TipoSiloEnum;
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
	private MedicaoServiceImpl medicaoServiceImpl;

	private double volumeTotal = 0;
	private double volumeStatus = 0;

	@Override
	public ResponseEntity<SiloModuloDTO> save(SiloModuloModel object) throws IOException {
		try {
			var silo = siloServiceImpl.findEntity(object.getSilo());
			var entity = new SiloModulo(null, silo, object.getDescricao(), object.getTotalSensor(),
					object.getNumSerie(), object.getTimeoutKeepAlive(), object.getTimeoutMedicao(), null, null,
					object.getGmt(), object.getCorKeepAlive(), object.getCorMedicao(), object.getStatus().getStatus());

			SiloModulo result = siloModuloRepository.save(entity);
			logger.info("Módulo do Silo salvo com sucesso: " + result);
			return MessageResponse.success(new SiloModuloDTO(result));
		} catch (Exception e) {
			logger.error("Erro ao salvar o Módulo do Silo: ", e);
			throw new IOException("Erro ao salvar o Módulo do Silo: " + object, e);
		}
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
		try {
			var silo = siloServiceImpl.findEntity(object.getSilo());
			var siloModulo = siloModuloRepository.findById(codigo).orElseThrow(() -> new EntityNotFoundException(
					"Não foi possível encontrar o módulo do silo com o ID fornecido: " + codigo));

			var entity = new SiloModulo(siloModulo.getSmocod(), silo, object.getDescricao(), object.getTotalSensor(),
					object.getNumSerie(), object.getTimeoutKeepAlive(), object.getTimeoutMedicao(), null, null,
					object.getGmt(), object.getCorKeepAlive(), object.getCorMedicao(),
					object.getStatus().getStatus());

			SiloModulo result = siloModuloRepository.save(entity);
			logger.info("Módulo do Silo atualizado com sucesso: " + result);
			return MessageResponse.success(new SiloModuloDTO(result));
		} catch (Exception e) {
			logger.error("Erro ao atualizar o Módulo do Silo: ", e);
			throw new IOException("Erro ao atualizar o Módulo do Silo: " + object, e);
		}
	}

	@Override
	public ResponseEntity<List<SiloModuloDTO>> findAll() {
		List<SiloModulo> modulos = siloModuloRepository.findAll();
		List<SiloModuloDTO> dtoList = modulos.stream().map(this::dtoCalc)
				.collect(Collectors.toList());
		return MessageResponse.success(dtoList);
	}

	@Override
	public ResponseEntity<SiloModuloDTO> findId(Long codigo) {
		var siloModulo = findEntity(codigo);
		return MessageResponse.success(dtoCalc(siloModulo));
	}

	SiloModulo findEntity(Long codigo) {
		return siloModuloRepository.findById(codigo).orElseThrow(() -> {
			logger.error("Módulo do Silo não encontrado com o ID: " + codigo);
			return new EntityNotFoundException("Módulo do Silo não encontrado com o ID: " + codigo);
		});
	}

	SiloModulo findEntityNSE(String nse) {
		return siloModuloRepository.findBySmonse(nse).orElseThrow(() -> {
			logger.error("Módulo do Silo não encontrado com o número de série: " + nse);
			return new EntityNotFoundException("Módulo do Silo não encontrado com o número de série: " + nse);
		});
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
		var tipo = TipoSiloEnum.valueOf(tipoSilo.getTsitip());
		double raio = tipoSilo.getTsirai();
		double largura = tipoSilo.getTsilar();
		double comprimento = tipoSilo.getTsicom();
		double alturaSilo = tipoSilo.getTsiach() - tipoSilo.getTsidse();
		Medicao ultimaMedicao = medicaoServiceImpl.ultimaMedicao(siloModulo);
		if (tipo == TipoSiloEnum.HORIZONTAL) {
			volumeTotal = Utils.calcularVolumeHorizontal(comprimento, largura, alturaSilo);
			volumeStatus = Utils.calcularVolumeHorizontal(comprimento, largura, ultimaMedicao.getMsidis()); // valor da medição

		}
		if (tipo == TipoSiloEnum.VERTICAL) {
			volumeTotal = Utils.calcularVolumeVertical(raio, alturaSilo);
			volumeStatus = Utils.calcularVolumeVertical(raio, ultimaMedicao.getMsidis()); // valor da medição

		}

		var siloModuloDTO = new SiloModuloDTO(siloModulo);
		siloModuloDTO.volumeSilo(volumeTotal, volumeStatus);

		return siloModuloDTO;
	}

}
