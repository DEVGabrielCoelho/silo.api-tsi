package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.CustomMessageException;
import br.com.telematica.siloapi.model.MedicaoModel;
import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.model.entity.Medicao;
import br.com.telematica.siloapi.model.entity.SiloModulo;
import br.com.telematica.siloapi.repository.MedicaoRepository;
import br.com.telematica.siloapi.services.MedicaoServInterface;
import br.com.telematica.siloapi.utils.Utils;
import br.com.telematica.siloapi.utils.message.MessageResponse;

@Service
public class MedicaoServiceImpl implements MedicaoServInterface {

	private static final Logger logger = LoggerFactory.getLogger(MedicaoServiceImpl.class);
	private static final String RECURSO = "Medição";

	@Autowired
	private MedicaoRepository medicaoRepository;

	@Autowired
	private SiloModuloServiceImpl siloModuloServiceImpl;

	@Override
	public ResponseEntity<MedicaoDTO> save(MedicaoModel medicaoModel) throws IOException {
		checkDataMedicao(medicaoModel);
		try {
			Date dateMedicao = Utils.sdfStringforDate(medicaoModel.getDataMedicao());
			var siloModulo = siloModuloServiceImpl.findEntity(medicaoModel.getSilo());

			Medicao medicao = new Medicao(dateMedicao, siloModulo, medicaoModel.getUmidade(), medicaoModel.getAna(), medicaoModel.getBarometro(), medicaoModel.getTemperatura(), medicaoModel.getDistancia());

			Medicao savedMedicao = medicaoRepository.save(medicao);
			logger.info("Medição salva com sucesso: " + savedMedicao);
			return MessageResponse.success(new MedicaoDTO(savedMedicao));
		} catch (Exception e) {
			logger.error("Erro ao salvar a medição: ", e);
			throw CustomMessageException.exceptionIOException("salvar", RECURSO, medicaoModel, e);
		}
	}

	@Override
	public ResponseEntity<MedicaoDTO> deleteByMsidth(String msidth) throws IOException {
		Objects.requireNonNull(msidth, "Data da Medição está nula.");
		try {
			medicaoRepository.deleteByMsidth(Utils.sdfStringforDate(msidth));
			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a Medição com o ID fornecido. Erro: ", e);
			throw CustomMessageException.exceptionEntityNotFoundException(msidth, RECURSO, e);
		} catch (Exception e) {
			logger.error("Erro ao deletar a medição. Erro: ", e);
			throw CustomMessageException.exceptionIOException("deletar", RECURSO, msidth, e);
		}
	}

	@Override
	public ResponseEntity<MedicaoDTO> update(MedicaoModel medicaoModel) throws IOException {
		checkDataMedicao(medicaoModel);
		try {
			Date dateMedicao = Utils.sdfStringforDate(medicaoModel.getDataMedicao());
			Medicao existingMedicao = medicaoRepository.findByMsidth(dateMedicao);

			var siloModulo = siloModuloServiceImpl.findEntity(medicaoModel.getSilo());

			existingMedicao.updateMedicao(siloModulo, medicaoModel.getUmidade(), medicaoModel.getAna(), medicaoModel.getBarometro(), medicaoModel.getTemperatura(), medicaoModel.getDistancia());

			Medicao updatedMedicao = medicaoRepository.save(existingMedicao);
			logger.info("Medição atualizada com sucesso: " + updatedMedicao);
			return MessageResponse.success(new MedicaoDTO(updatedMedicao));
		} catch (Exception e) {
			logger.error("Erro ao atualizar a medição: ", e);
			throw CustomMessageException.exceptionIOException("atualizar", RECURSO, medicaoModel, e);
		}
	}

	@Override
	public List<Medicao> findAll() throws IOException {
		return medicaoRepository.findAll();
	}

	@Override
	public ResponseEntity<List<MedicaoDTO>> findAllMedicaoDTO() throws IOException {
		List<MedicaoDTO> medicaoDTOs = medicaoRepository.findAll().stream().map(this::convertToMedicaoDTO).collect(Collectors.toList());
		return MessageResponse.success(medicaoDTOs);
	}

	@Override
	public MedicaoDTO findByData(Date date) throws IOException {
		return new MedicaoDTO(medicaoRepository.findByMsidth(date));
	}

	private MedicaoDTO convertToMedicaoDTO(Medicao medicaoEntity) {
		return new MedicaoDTO(medicaoEntity);
	}

	Medicao ultimaMedicao(SiloModulo siloModulo) {
		Optional<Medicao> medicao = medicaoRepository.findFirstBySilomoduloOrderByMsidthDesc(siloModulo);
		if(medicao.isEmpty() || !medicao.isPresent())
			return null;
		return medicao.get();
	}
	
	private void checkDataMedicao(MedicaoModel model) {
		Objects.requireNonNull(model.getDataMedicao(), "Data da Medição está nula.");
		Objects.requireNonNull(model.getSilo(), "Código do Silo está nulo.");
		Objects.requireNonNull(model.getUmidade(), "Umidade está nula.");
		Objects.requireNonNull(model.getTemperatura(), "Temperatura está nula.");
		Objects.requireNonNull(model.getAna(), "Ana está nula.");
		Objects.requireNonNull(model.getBarometro(), "Barômetro está nulo.");
		Objects.requireNonNull(model.getDistancia(), "Distância está nula.");
	}
}
