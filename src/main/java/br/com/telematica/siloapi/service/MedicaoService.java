package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.model.entity.MedicaoEntity;
import br.com.telematica.siloapi.repository.MedicaoRepository;
import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;

@Service
public class MedicaoService {

	private static Logger logger = (Logger) LoggerFactory.getLogger(MedicaoService.class);
	@Autowired
	private MedicaoRepository medicaoRepository;

	public MedicaoDTO save(MedicaoDTO medicaoDTO) throws RuntimeException {
		try {

			if (medicaoDTO == null) {
				logger.error("Medição está nula.");
				throw new RuntimeException("Medição está nula.");
			}
			var entity = new MedicaoEntity(medicaoDTO.getDataMedicao(), medicaoDTO.getCodigoSilo(), medicaoDTO.getUmidade(), medicaoDTO.getAna(), medicaoDTO.getBarometro(), medicaoDTO.getTemperatura(), medicaoDTO.getDistancia());
			var result = medicaoRepository.save(entity);

			logger.info("Medição salva com sucesso." + result);
			return new MedicaoDTO(result.getMsidth(), result.getSilcod(), result.getMsiumi(), result.getMsiana(), result.getMsibar(), result.getMsitem(), result.getMsidis());
		} catch (Exception e) {
			logger.error("Ocorreu um erro ao salvar a Medição. Error: " + e.getCause());
			throw new RuntimeException("Exceção:" + e.getCause());
		}
	}

	@Transactional
	public void deleteByMsidth(Date msidth) throws IOException {
		if (msidth == null) {
			logger.error("O ID da Medição está nulo.");
			throw new IOException("O ID da Medição está nulo.");
		}
		try {
			medicaoRepository.deleteByMsidth(msidth);

		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a Medição com o ID fornecido. Error: " + e.getCause());
			throw new IOException("Não foi possível encontrar a Medição com o ID fornecido.", e);
		} catch (Exception e) {
			logger.error("Ocorreu um erro ao excluir a Medição. Error: " + e.getCause());
			throw new IOException("Ocorreu um erro ao excluir a Medição.", e.getCause());
		}
	}

	public MedicaoDTO update(MedicaoDTO medicaoDTO) throws IOException {
		if (medicaoDTO == null) {
			logger.error("Medição está nula.");
			throw new RuntimeException("Medição está nulo.");
		}
		var entity = new MedicaoEntity(medicaoDTO.getDataMedicao(), medicaoDTO.getCodigoSilo(), medicaoDTO.getUmidade(), medicaoDTO.getAna(), medicaoDTO.getBarometro(), medicaoDTO.getTemperatura(), medicaoDTO.getDistancia());
		var result = medicaoRepository.save(entity);
		logger.info("Medição atualizado com sucesso." + result);
		return new MedicaoDTO(result.getMsidth(), result.getSilcod(), result.getMsiumi(), result.getMsiana(), result.getMsibar(), result.getMsitem(), result.getMsidis());
	}

	public List<MedicaoEntity> findAll() throws IOException {
		return medicaoRepository.findAll();
	}

	public List<MedicaoDTO> findAllMedicaoDTO() throws IOException {
		return medicaoRepository.findAll().stream().map(this::convertToMedicaoDTO).collect(Collectors.toList());
	}

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
		return new MedicaoDTO(result.getMsidth(), result.getSilcod(), result.getMsiumi(), result.getMsiana(), result.getMsibar(), result.getMsitem(), result.getMsidis());
	}

	private MedicaoDTO convertToMedicaoDTO(MedicaoEntity medicaoDTOEntity) {
		return new MedicaoDTO(medicaoDTOEntity);
	}
}
