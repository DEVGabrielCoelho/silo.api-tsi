package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.CustomMessageException;
import br.com.telematica.siloapi.model.MedicaoModel;
import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.model.entity.Medicao;
import br.com.telematica.siloapi.repository.MedicaoRepository;
import br.com.telematica.siloapi.services.MedicaoServInterface;
import br.com.telematica.siloapi.utils.Utils;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;

@Service
public class MedicaoServiceImpl implements MedicaoServInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(MedicaoServiceImpl.class);
	@Autowired
	private MedicaoRepository medicaoRepository;
	@Autowired
	private SiloServiceImpl siloServiceImpl;

	private static final String RECURSO = "Medição";

	@Override
	public ResponseEntity<MedicaoDTO> save(MedicaoModel medicaoDTO) throws IOException {
		checkDataMedicao(medicaoDTO);
		try {

			Date dateMedicao = Utils.sdfStringforDate(medicaoDTO.getDataMedicao());
			var silo = siloServiceImpl.findEntity(medicaoDTO.getSilo());
			var entity = new Medicao(dateMedicao, silo, medicaoDTO.getUmidade(), medicaoDTO.getAna(), medicaoDTO.getBarometro(), medicaoDTO.getTemperatura(), medicaoDTO.getDistancia());
			var result = medicaoRepository.save(entity);

			logger.info("Medição salva com sucesso." + result);
//			var stringDateMedi = Utils.sdfDateforString(result.getMsidth());

			return MessageResponse.success(new MedicaoDTO(result));
		} catch (Exception e) {
			throw CustomMessageException.exceptionIOException("criar", RECURSO, medicaoDTO, e);
		}
	}

	@Override
	public ResponseEntity<MedicaoDTO> deleteByMsidth(String msidth) throws IOException {
		Objects.requireNonNull(msidth, "Data da Medição está nulo.");
		try {
			medicaoRepository.deleteByMsidth(Utils.sdfStringforDate(msidth));
			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a Medição com o ID fornecido. Error: ", e);
			throw CustomMessageException.exceptionEntityNotFoundException(msidth, RECURSO, e);
		} catch (Exception e) {
			logger.error("Erro ao deletar o tipo do silo. Erro: ", e);
			throw CustomMessageException.exceptionIOException("deletar", RECURSO, msidth, e);
		}
	}

	@Override
	public ResponseEntity<MedicaoDTO> update(MedicaoModel medicaoDTO) throws IOException {
		checkDataMedicao(medicaoDTO);
		try {

			Date dateMedicao = Utils.sdfStringforDate(medicaoDTO.getDataMedicao());
			var entity = medicaoRepository.findByMsidth(dateMedicao);
			var silo = siloServiceImpl.findEntity(medicaoDTO.getSilo());
			entity.updateMedicao(silo, medicaoDTO.getUmidade(), medicaoDTO.getAna(), medicaoDTO.getBarometro(), medicaoDTO.getTemperatura(), medicaoDTO.getDistancia());
			var result = medicaoRepository.save(entity);
			logger.info("Medição atualizado com sucesso." + result);
//			var stringDateMedi = Utils.sdfDateforString(result.getMsidth());

			return MessageResponse.success(new MedicaoDTO(result));
		} catch (Exception e) {
			throw CustomMessageException.exceptionIOException("criar", RECURSO, medicaoDTO, e);
		}
	}

	@Override
	public List<Medicao> findAll() throws IOException {
		return medicaoRepository.findAll();
	}

	@Override
	public ResponseEntity<List<MedicaoDTO>> findAllMedicaoDTO() throws IOException {
		return MessageResponse.success(medicaoRepository.findAll().stream().map(this::convertToMedicaoDTO).collect(Collectors.toList()));

	}

	@Override
	public MedicaoDTO findByData(Date date) throws IOException {
		return new MedicaoDTO(medicaoRepository.findByMsidth(date));
	}

	private MedicaoDTO convertToMedicaoDTO(Medicao medicaoDTOEntity) {
		return new MedicaoDTO(medicaoDTOEntity);
	}

	private void checkDataMedicao(MedicaoModel model) {
		Objects.requireNonNull(model.getDataMedicao(), "Data da Medição está nulo.");
		Objects.requireNonNull(model.getSilo(), "Código do Silo está nulo.");
		Objects.requireNonNull(model.getUmidade(), "Umidade está nulo.");
		Objects.requireNonNull(model.getTemperatura(), "Temperatura está nulo.");
		Objects.requireNonNull(model.getAna(), "Ana está nulo.");
		Objects.requireNonNull(model.getBarometro(), "Barometro está nulo.");
		Objects.requireNonNull(model.getDistancia(), "Distância está nulo.");

	}
}
