package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.model.entity.EmpresaEntity;
import br.com.telematica.siloapi.repository.EmpresaRepository;
import br.com.telematica.siloapi.services.EmpresaInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import ch.qos.logback.classic.Logger;

@Service
public class EmpresaServiceImpl implements EmpresaInterface {

	private static Logger logger = (Logger) LoggerFactory.getLogger(EmpresaServiceImpl.class);
	@Autowired
	private EmpresaRepository empresaRepository;

	@Override
	public ResponseEntity<GenericResponseModel> salvar(EmpresaDTO empresa) throws RuntimeException {
		try {
			if (empresa == null) {
				logger.error("Empresa está nula.");
				return MessageResponse.badRequest("Empresa está nula.");
			}
			var entity = new EmpresaEntity(empresa.getCodigo(), empresa.getNome(), empresa.getCnpj());
			var result = empresaRepository.save(entity);

			logger.info("Empresa salva com sucesso." + result);

			return MessageResponse.sucess(new EmpresaDTO(result.getEmpcod(), result.getEmpnom(), result.getEmpcnp()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<GenericResponseModel> deleteByEmpcod(Integer codigo) throws IOException {
		try {
			if (codigo == null) {
				logger.error("O ID da empresa está nulo.");
				return MessageResponse.notFound("O ID da empresa está nulo.");
			}
			try {
				empresaRepository.removeByEmpcod(codigo);
			} catch (EmptyResultDataAccessException e) {
				logger.error("Não foi possível encontrar a empresa com o ID fornecido. Error: " + e.getCause());
				return MessageResponse.notFound("Não foi possível encontrar a empresa com o ID fornecido." + e);
			}
			return MessageResponse.sucess(null);
		} catch (Exception e) {
			logger.error("Ocorreu um erro ao excluir a empresa. Error: " + e.getCause());
			return MessageResponse.badRequest("Ocorreu um erro ao excluir a empresa." + e.getCause());
		}
	}

	@Override
	public ResponseEntity<GenericResponseModel> update(EmpresaDTO empresa) throws IOException {
		try {

			if (empresa == null) {
				logger.error("Empresa está nula.");
				return MessageResponse.badRequest("Empresa está nulo.");
			}
			var entity = new EmpresaEntity(empresa.getCodigo(), empresa.getNome(), empresa.getCnpj());
			var result = empresaRepository.save(entity);
			logger.info("Empresa atualizado com sucesso." + result);

			return MessageResponse.sucess(new EmpresaDTO(result.getEmpcod(), result.getEmpnom(), result.getEmpcnp()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@Override
	public List<EmpresaEntity> findAll() throws IOException {
		return empresaRepository.findAll();
	}

	@Override
	public ResponseEntity<GenericResponseModel> findAllEmpresaDTO() {
		try {

			return MessageResponse.sucess(empresaRepository.findAll().stream().map(this::convertToEmpresaDTO).collect(Collectors.toList()));
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}

	}

	@Override
	public EmpresaDTO findById(Integer id) throws IOException, EmptyResultDataAccessException {
		if (id == null) {
			logger.error("Id está nulo.");
			throw new IOException("Id está nulo.");
		}
		var result = empresaRepository.findById(id).orElse(null);

		if (result == null) {
			throw new EmptyResultDataAccessException("Empresa não encontrada.", 1);
		}
		return new EmpresaDTO(result.getEmpcod(), result.getEmpnom(), result.getEmpcnp());
	}

	private EmpresaDTO convertToEmpresaDTO(EmpresaEntity empresaEntity) {
		return new EmpresaDTO(empresaEntity);
	}

}
