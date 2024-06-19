package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.telematica.siloapi.model.SiloModuloModel;
import br.com.telematica.siloapi.model.dto.SiloModuloDTO;
import br.com.telematica.siloapi.model.entity.SiloModulo;
import br.com.telematica.siloapi.repository.SiloModuloRepository;
import br.com.telematica.siloapi.services.SiloModuloServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SiloModuloServiceImpl implements SiloModuloServInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(SiloModuloServiceImpl.class);
	@Autowired
	private SiloModuloRepository siloModuloRepository;

	@Autowired
	private SiloServiceImpl siloServiceImpl;

	@Override
	public ResponseEntity<SiloModuloDTO> save(SiloModuloModel object) throws IOException {
		try {
			var silo = siloServiceImpl.findEntity(object.getSilo());

			var entity = new SiloModulo(null, silo, object.getDescricao(), object.getTotalSensor(), object.getNumSerie(), object.getTimeoutKeepAlive(), object.getTimeoutMedicao(), object.getGmt(), object.getCorKeepAlive(), object.getCorMedicao(), object.getStatus().getStatus());
			var result = siloModuloRepository.save(entity);

			logger.info("Módulo do Silo salvo com successo." + result);

			return MessageResponse.success(new SiloModuloDTO(result));
		} catch (Exception e) {
			throw new IOException("Erro ao salvar o Módulo do Silo." + new Gson().toJson(object), e);
		}
	}

	@Override
	public ResponseEntity<SiloModuloDTO> delete(Long codigo) throws IOException {
		SiloModulo siloModulo = null;
		try {

			siloModulo = siloModuloRepository.findById(codigo).orElse(null);
			if (siloModulo == null)
				throw new EntityNotFoundException("Não foi possível encontrar o modulo do silo com o ID " + codigo + " fornecido.");

			siloModuloRepository.delete(siloModulo);

			logger.info("Módulo do Silo deletado com successo." + siloModulo);

			return MessageResponse.success(null);
		} catch (Exception e) {
			throw new IOException("Erro ao deletar o Módulo do Silo." + new Gson().toJson(siloModulo), e);
		}
	}

	@Override
	public ResponseEntity<SiloModuloDTO> update(Long codigo, SiloModuloModel object) throws IOException {
		try {
			var silo = siloServiceImpl.findEntity(object.getSilo());
			var siloModulo = siloModuloRepository.findById(codigo);
			if (siloModulo.isEmpty() || !siloModulo.isPresent())
				throw new EntityNotFoundException("Não foi possível encontrar o modulo do silo com o ID " + codigo + " fornecido.");

			var entity = new SiloModulo(siloModulo.get().getSmocod(), silo, object.getDescricao(), object.getTotalSensor(), object.getNumSerie(), object.getTimeoutKeepAlive(), object.getTimeoutMedicao(), object.getGmt(), object.getCorKeepAlive(), object.getCorMedicao(),
					object.getStatus().getStatus());
			var result = siloModuloRepository.save(entity);

			logger.info("Módulo do Silo atualizado com successo." + result);

			return MessageResponse.success(new SiloModuloDTO(result));
		} catch (Exception e) {
			throw new IOException("Erro ao salvar o Módulo do Silo." + new Gson().toJson(object), e);
		}
	}

	@Override
	public ResponseEntity<List<SiloModuloDTO>> findAll() {
		List<SiloModulo> modulo = siloModuloRepository.findAll();
		return MessageResponse.success(modulo.stream().map(SiloModuloDTO::new).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<SiloModuloDTO> findId(Long codigo) {
		var siloModulo = siloModuloRepository.findById(codigo);
		if (siloModulo.isEmpty() || !siloModulo.isPresent())
			MessageResponse.success(null);

		return MessageResponse.success(new SiloModuloDTO(siloModulo.get()));
	}

	SiloModulo findEntity(Long codigo) {
		var result = siloModuloRepository.findById(codigo).orElse(null);

		if (result == null) {
			logger.error("Silo não encontrado.");
			return null;
		}

		return result;
	}

}
