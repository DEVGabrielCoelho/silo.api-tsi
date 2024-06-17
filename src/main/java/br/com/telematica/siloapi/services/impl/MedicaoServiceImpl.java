package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.model.entity.MedicaoEntity;
import br.com.telematica.siloapi.repository.MedicaoRepository;
import br.com.telematica.siloapi.services.MedicaoInterface;
import br.com.telematica.siloapi.utils.Utils;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;

@Service
public class MedicaoServiceImpl implements MedicaoInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(MedicaoServiceImpl.class);
	@Autowired
	private MedicaoRepository medicaoRepository;

	@Override
	public ResponseEntity<Object> save(MedicaoDTO medicaoDTO) throws RuntimeException {
		try {

			if (medicaoDTO == null) {
				logger.error("Medição está nula.");
				throw new RuntimeException("Medição está nula.");
			}
			Date dateMedicao = Utils.sdfStringforDate(medicaoDTO.getDataMedicao());
			var entity = new MedicaoEntity(dateMedicao, medicaoDTO.getSilo(), medicaoDTO.getUmidade(), medicaoDTO.getAna(), medicaoDTO.getBarometro(), medicaoDTO.getTemperatura(), medicaoDTO.getDistancia());
			var result = medicaoRepository.save(entity);

			logger.info("Medição salva com sucesso." + result);
			var stringDateMedi = Utils.sdfDateforString(result.getMsidth());

			return MessageResponse.success(new MedicaoDTO(stringDateMedi, result.getSilcod(), result.getMsiumi(), result.getMsiana(), result.getMsibar(), result.getMsitem(), result.getMsidis()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Object> deleteByMsidth(String msidth) throws Exception {
		try {
			if (msidth == null) {
				logger.error("O ID da Medição está nulo.");
				return MessageResponse.badRequest("O ID da Medição está nulo.");
			}

			medicaoRepository.deleteByMsidth(Utils.sdfStringforDate(msidth));
			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a Medição com o ID fornecido. Error: ", e);
			return MessageResponse.notFound("Não foi possível encontrar a Medição com o ID fornecido." + e.getMessage());
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Object> update(MedicaoDTO medicaoDTO) throws IOException, Exception {
		try {
			if (medicaoDTO == null) {
				logger.error("Medição está nula.");
				return MessageResponse.badRequest("Medição está nulo.");
			}
			Date dateMedicao = Utils.sdfStringforDate(medicaoDTO.getDataMedicao());
			var entity = new MedicaoEntity(dateMedicao, medicaoDTO.getSilo(), medicaoDTO.getUmidade(), medicaoDTO.getAna(), medicaoDTO.getBarometro(), medicaoDTO.getTemperatura(), medicaoDTO.getDistancia());
			var result = medicaoRepository.save(entity);
			logger.info("Medição atualizado com sucesso." + result);
			var stringDateMedi = Utils.sdfDateforString(result.getMsidth());

			return MessageResponse.success(new MedicaoDTO(stringDateMedi, result.getSilcod(), result.getMsiumi(), result.getMsiana(), result.getMsibar(), result.getMsitem(), result.getMsidis()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public List<MedicaoEntity> findAll() throws IOException {
		return medicaoRepository.findAll();
	}

	@Override
	public ResponseEntity<Object> findAllMedicaoDTO() throws IOException {
		try {

			return MessageResponse.success(medicaoRepository.findAll().stream().map(this::convertToMedicaoDTO).collect(Collectors.toList()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}

	}

	@Override
	public MedicaoDTO findByData(Date date) throws IOException, EmptyResultDataAccessException {
		if (date == null) {
			logger.error("Data está nula.");
			throw new IOException("Data está nulo.");
		}
		var result = medicaoRepository.findByMsidth(date);

		if (result == null) {
			logger.error("Medição não encontrada.");
			throw new EmptyResultDataAccessException("Medição não encontrada.", 1);
		}
		var stringDateMedi = Utils.sdfDateforString(result.getMsidth());
		return new MedicaoDTO(stringDateMedi, result.getSilcod(), result.getMsiumi(), result.getMsiana(), result.getMsibar(), result.getMsitem(), result.getMsidis());
	}

	private MedicaoDTO convertToMedicaoDTO(MedicaoEntity medicaoDTOEntity) {
		return new MedicaoDTO(medicaoDTOEntity);
	}
}
