package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.LoggerModel;
import br.com.telematica.siloapi.model.dto.LoggerDTO;
import br.com.telematica.siloapi.model.entity.LoggerEntity;
import br.com.telematica.siloapi.repository.LoggerRepository;
import br.com.telematica.siloapi.utils.Utils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LoggerServiceImpl {

	@Autowired
	private LoggerRepository logRepository;

	public LoggerDTO save(LoggerModel logModel) throws EntityNotFoundException, IOException {
		Date date = Utils.convertStringToDate(logModel.getData());
		String numSerie = logModel.getNumSerie();
		String tipo = logModel.getTipoLogger().toString();
		String menssagem = logModel.getMensagem();
		Objects.requireNonNull(date, "Data está nulo.");
		Objects.requireNonNull(numSerie, "Número de Série está nulo.");
		Objects.requireNonNull(tipo, "Tipo de Log está nulo.");
		Objects.requireNonNull(menssagem, "Mensagem está nulo.");

//		var sm = smService.findBySmonseEntity(numSerie);
//		if (sm == null)
//			throw new IOException("Módulo não encontrado.");

		var entity = new LoggerEntity(date, null, tipo, logModel.getMensagem());
		return new LoggerDTO(logRepository.save(entity));
	}

	public List<LoggerDTO> findByAll() throws EntityNotFoundException, IOException {
		var listAll = logRepository.findAll();

		return listAll.stream().map(list -> {
			Long cod = list.getSmocod();
			Objects.requireNonNull(cod, "Tipo de Log está nulo.");
//			SireneModuloDTO sm;
//			try {
//				sm = new SireneModuloDTO(smService.findByEntityModulo(cod).get());
//				return new LoggerDTO(list, sm);
//			} catch (EntityNotFoundException e) {
//				return new LoggerDTO(list, null);
//			}
			return new LoggerDTO(list);
		}).collect(Collectors.toList());
	}

	public Page<LoggerDTO> findByAllPaginado(Long smocod, String filtro, String startDate, String endDate, @NonNull Pageable pageable) throws EntityNotFoundException, IOException {
		Specification<LoggerEntity> spec;
		spec = LoggerEntity.filterByFields(smocod, filtro, startDate, endDate);

		Page<LoggerEntity> listAll = logRepository.findAll(spec, pageable);
		return listAll.map(list -> {
//            SireneModuloDTO sm = null;
//            try {
//                Long cod = list.getSmocod();
//                Objects.requireNonNull(cod, "Código do Módulo está nulo.");
//                sm = new SireneModuloDTO(smService.findByEntityModulo(cod).get());
//                return new LoggerDTO(list, sm);
//            } catch (EntityNotFoundException e) {
//                return new LoggerDTO(list, sm);
//            }
			return new LoggerDTO(list);
		});
	}

	public List<LoggerDTO> findAllByModulo(Long codigo) {
		var listAll = logRepository.findBySmocod(codigo);

		return listAll.stream().map(list -> {
			Long cod = list.getSmocod();
			Objects.requireNonNull(cod, "Tipo de Log está nulo.");
//			SireneModuloDTO sm = null;
//			try {
//				sm = new SireneModuloDTO(smService.findByEntityModulo(cod).get());
//				return new LoggerDTO(list, sm);
//			} catch (EntityNotFoundException e) {
//				return new LoggerDTO(list, null);
//			}
			return new LoggerDTO(list);
		}).collect(Collectors.toList());
	}

}
