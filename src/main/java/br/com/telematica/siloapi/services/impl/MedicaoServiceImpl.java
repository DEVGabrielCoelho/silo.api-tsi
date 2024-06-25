package br.com.telematica.siloapi.services.impl;

import static br.com.telematica.siloapi.utils.Utils.sdfStringforDate;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.CustomMessageException;
import br.com.telematica.siloapi.model.MedicaoDeviceModel;
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
	@Lazy
	private SiloModuloServiceImpl siloModuloServiceImpl;

	@Override
	public ResponseEntity<MedicaoDTO> save(MedicaoModel medicaoModel) throws IOException, ParseException {
		checkDataMedicao(medicaoModel);
		Date dateMedicao = sdfStringforDate(medicaoModel.getDataMedicao());
		var siloModulo = siloModuloServiceImpl.findEntity(medicaoModel.getSilo());
		siloModuloServiceImpl.registerMedicaoInModulo(siloModulo, new Date());
		Medicao medicao = new Medicao(dateMedicao, siloModulo, medicaoModel.getUmidade(), medicaoModel.getAna(),
				medicaoModel.getBarometro(), medicaoModel.getTemperatura(), medicaoModel.getDistancia());
		Medicao savedMedicao = medicaoRepository.save(medicao);
		logger.info("Medição salva com sucesso: " + savedMedicao);
		return MessageResponse.success(new MedicaoDTO(savedMedicao));
	}

	@Override
	public ResponseEntity<MedicaoDTO> saveData(MedicaoDeviceModel medicaoModel) throws IOException, ParseException {

		Date dateMedicao = Utils.convertTimestampToDate(medicaoModel.getTimestamp());
		var siloModulo = siloModuloServiceImpl.findEntity(Long.valueOf(medicaoModel.getDevEUI()));
		siloModuloServiceImpl.registerMedicaoInModulo(siloModulo, new Date());
		Medicao medicao = new Medicao(dateMedicao, siloModulo, medicaoModel.getObject().getHumidity(),
				medicaoModel.getObject().getAnalogInput(),
				medicaoModel.getObject().getBarometer(), medicaoModel.getObject().getTemperature(),
				medicaoModel.getObject().getIlluminance());

		Medicao savedMedicao = medicaoRepository.save(medicao);
		logger.info("Medição salva com sucesso: " + savedMedicao);
		return MessageResponse.success(new MedicaoDTO(savedMedicao));

	}

	@Override
	public ResponseEntity<MedicaoDTO> deleteByMsidth(String msidth) throws IOException, ParseException {
		Objects.requireNonNull(msidth, "Data da Medição está nula.");
		try {
			medicaoRepository.deleteByMsidth(Utils.sdfStringforDate(msidth));
			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a Medição com o ID fornecido. Erro: ", e);
			throw CustomMessageException.exceptionEntityNotFoundException(msidth, RECURSO, e);
		}
	}

	@Override
	public ResponseEntity<MedicaoDTO> update(MedicaoModel medicaoModel) throws IOException, ParseException {
		checkDataMedicao(medicaoModel);

		Date dateMedicao = Utils.sdfStringforDate(medicaoModel.getDataMedicao());
		Medicao existingMedicao = medicaoRepository.findByMsidth(dateMedicao);

		var siloModulo = siloModuloServiceImpl.findEntity(medicaoModel.getSilo());
		siloModuloServiceImpl.registerMedicaoInModulo(siloModulo, new Date());

		existingMedicao.updateMedicao(siloModulo, medicaoModel.getUmidade(), medicaoModel.getAna(),
				medicaoModel.getBarometro(), medicaoModel.getTemperatura(), medicaoModel.getDistancia());

		Medicao updatedMedicao = medicaoRepository.save(existingMedicao);
		logger.info("Medição atualizada com sucesso: " + updatedMedicao);
		return MessageResponse.success(new MedicaoDTO(updatedMedicao));

	}

	@Override
	public List<Medicao> findAll() throws IOException {
		return medicaoRepository.findAll();
	}

	@Override
	public ResponseEntity<List<MedicaoDTO>> findAllMedicaoDTO() throws IOException {
		List<MedicaoDTO> medicaoDTOs = medicaoRepository.findAll().stream().map(this::convertToMedicaoDTO)
				.collect(Collectors.toList());
		return MessageResponse.success(medicaoDTOs);
	}

	@Override
	public MedicaoDTO findByData(Date date) throws IOException {
		return new MedicaoDTO(medicaoRepository.findByMsidth(date));
	}
	
	@Override
	public ResponseEntity<Page<MedicaoDTO>> medicaoFindAllPaginado(String searchTerm, String dataInicio, String dataFim, Pageable pageable) {
        Specification<Medicao> spec = Medicao.filterByFields(searchTerm, null, dataInicio, dataFim);
        Page<Medicao> result = medicaoRepository.findAll(spec, pageable);
        return ResponseEntity.ok(result.map(MedicaoDTO::new));
    }

	private MedicaoDTO convertToMedicaoDTO(Medicao medicaoEntity) {
		return new MedicaoDTO(medicaoEntity);
	}

	Medicao ultimaMedicao(SiloModulo siloModulo) {
		Optional<Medicao> medicao = medicaoRepository.findFirstBySilomoduloOrderByMsidthDesc(siloModulo);
		if (medicao.isEmpty())
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
