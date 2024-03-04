package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.model.entity.EmpresaEntity;
import br.com.telematica.siloapi.repository.EmpresaRepository;
import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;

@Service
public class EmpresaService {

	private static Logger logger = (Logger) LoggerFactory.getLogger(EmpresaService.class);
	@Autowired
	private EmpresaRepository empresaRepository;

	public EmpresaDTO salvar(EmpresaDTO empresa) throws RuntimeException {
		if (empresa == null) {
			logger.error("Empresa está nula.");
			throw new RuntimeException("Empresa está nula.");
		}
		var entity = new EmpresaEntity(empresa.getCodigo(), empresa.getNome(), empresa.getCnpj());
		var result = empresaRepository.save(entity);

		logger.info("Empresa salva com sucesso." + result);
		return new EmpresaDTO(result.getEmpcod(), result.getEmpnom(), result.getEmpcnp());
	}

	@Transactional
	public void deleteByEmpcod(Integer codigo) throws IOException {
		if (codigo == null) {
			logger.error("O ID da empresa está nulo.");
			throw new IOException("O ID da empresa está nulo.");
		}
		try {
			empresaRepository.removeByEmpcod(codigo);

		} catch (EmptyResultDataAccessException e) {
			logger.error("Não foi possível encontrar a empresa com o ID fornecido. Error: " + e.getCause());
			throw new IOException("Não foi possível encontrar a empresa com o ID fornecido.", e);
		} catch (Exception e) {
			logger.error("Ocorreu um erro ao excluir a empresa. Error: " + e.getCause());
			throw new IOException("Ocorreu um erro ao excluir a empresa.", e.getCause());
		}
	}

	public EmpresaDTO update(EmpresaDTO empresa) throws IOException {
		if (empresa == null) {
			logger.error("Empresa está nula.");
			throw new RuntimeException("Empresa está nulo.");
		}
		var entity = new EmpresaEntity(empresa.getCodigo(), empresa.getNome(), empresa.getCnpj());
		var result = empresaRepository.save(entity);
		logger.info("Empresa atualizado com sucesso." + result);
		return new EmpresaDTO(result.getEmpcod(), result.getEmpnom(), result.getEmpcnp());
	}

	public List<EmpresaEntity> findAll() throws IOException {
		return empresaRepository.findAll();
	}

	public List<EmpresaDTO> findAllEmpresaDTO() throws IOException {
		return empresaRepository.findAll().stream().map(this::convertToEmpresaDTO).collect(Collectors.toList());
	}

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
