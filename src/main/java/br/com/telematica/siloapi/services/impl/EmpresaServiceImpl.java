package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.handler.AbrangenciaHandler;
import br.com.telematica.siloapi.model.EmpresaModel;
import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.model.entity.Empresa;
import br.com.telematica.siloapi.records.CheckAbrangenciaRec;
import br.com.telematica.siloapi.repository.EmpresaRepository;
import br.com.telematica.siloapi.utils.Utils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EmpresaServiceImpl {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private AbrangenciaHandler abrancenciaHandler;

	public CheckAbrangenciaRec checagemFixaBarragem() throws EntityNotFoundException, IOException {
		return abrancenciaHandler.checkAbrangencia("EMPRESA");
	}

	public ResponseGlobalModel empresaDeleteById(Long code) throws IOException {
		Objects.requireNonNull(code, "Código da Empresa está nulo.");

		var empresa = findByIdEntity(code);

		if (empresa != null) {
			var emp = new Empresa(empresa.getEmpcod(), empresa.getEmpcnp(), empresa.getEmpnom(), empresa.getEmpfan(), empresa.getEmptel(), 0);
			empresaRepository.save(emp);
		}
		return Utils.responseMessageSucess("Empresa apagado com sucesso. Código da Empresa: " + code);
	}

	public Page<EmpresaDTO> empresaFindAllPaginado(String nome, Pageable pageable) throws IOException {
		Objects.requireNonNull(pageable, "Pageable da Empresa está nulo.");
		var check = checagemFixaBarragem();
		Integer empdel = 1;

		Specification<Empresa> spec;
		if (check.isHier() == 0) {
			spec = Empresa.filterByFields(nome, empdel, null);
		} else {
			spec = Empresa.filterByFields(nome, empdel, check.listAbrangencia());
		}

		Page<Empresa> result = empresaRepository.findAll(spec, pageable);
		return result.map(EmpresaDTO::new);
	}

	// public Page<EmpresaDTO> empresaFindAllPaginado(String nome, Pageable
	// pageable) throws IOException {
	// Objects.requireNonNull(pageable, "Pageable da Empresa está nulo.");
	// var check = checagemFixaBarragem();
	// Page<Empresa> result;
	// if (nome == null)
	// result = check.isHier() == 0 ? empresaRepository.findByEmpdel(1, pageable) :
	// empresaRepository.findByEmpdelAndEmpcodIn(1, pageable,
	// check.listAbrangencia());
	// else
	// result = check.isHier() == 0 ?
	// empresaRepository.findByEmpnomLikeAndEmpdel(nome, 1, pageable) :
	// empresaRepository.findByEmpnomLikeAndEmpdelAndEmpcodIn(nome, 1, pageable,
	// check.listAbrangencia());
	//
	// return result.map(empresa -> new EmpresaDTO(empresa));
	//
	// }

	public List<EmpresaDTO> findByEmpresa() {
		try {

			var result = empresaRepository.findByEmpdel(1);
			return result.stream().map(empresa -> new EmpresaDTO(empresa)).collect(Collectors.toList());
		} catch (Exception e) {
			log.info("Erro ", e);
			return null;
		}
	}

	public List<EmpresaDTO> empresaFindAll() throws IOException {
		var check = checagemFixaBarragem();
		var result = check.isHier() == 0 ? empresaRepository.findByEmpdel(1) : empresaRepository.findByEmpdelAndEmpcodIn(1, check.listAbrangencia());

		return result.stream().map(empresa -> new EmpresaDTO(empresa)).collect(Collectors.toList());
	}

	public EmpresaDTO empresaUpdate(Long codigo, EmpresaModel empresa) {
		Objects.requireNonNull(codigo, "Código da Empresa está nulo.");
		Objects.requireNonNull(empresa.getCnpj(), "CNPJ da Empresa está nulo.");
		Objects.requireNonNull(empresa.getNome(), "Nome da Empresa está nulo.");
		return new EmpresaDTO(empresaRepository.save(new Empresa(codigo, empresa.getCnpj(), empresa.getNome(), empresa.getNomeFantasia(), empresa.getTelefone(), 1)));

	}

	public EmpresaDTO empresaSave(EmpresaModel empresa) {
		Objects.requireNonNull(empresa.getCnpj(), "CNPJ da Empresa está nulo.");
		Objects.requireNonNull(empresa.getNome(), "Nome da Empresa está nulo.");
		Empresa emp = null;
		if (empresa != null) {
			emp = empresaRepository.save(new Empresa(null, empresa.getCnpj(), empresa.getNome(), empresa.getNomeFantasia(), empresa.getTelefone(), 1));
		}
		return new EmpresaDTO(emp);
	}

	public Empresa findByIdEntity(@NonNull Long codigo) {
		Objects.requireNonNull(codigo, "Código da Empresa está nulo.");
		var entity = empresaRepository.findById(codigo);
		if (entity.isEmpty()) {
			log.info("Empresa não encontrado ou deletado.");
			return null;
		}
		return entity.get();
	}

	public Empresa findByIdAbrangencia(Empresa emp) throws EntityNotFoundException, IOException {
		var check = checagemFixaBarragem();
		var findIdAbrangencia = abrancenciaHandler.findIdAbrangenciaPermi(check, emp.getEmpcod());
		if (findIdAbrangencia == null)
			return null;
		return emp;
	}

	public Empresa findById(Long codigo) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(codigo, "Código da Empresa está nulo.");
		var emp = findByIdEntity(codigo);
		if (emp == null)
			return null;
		var empresa = findByIdAbrangencia(emp);
		if (empresa == null)
			return null;
		return empresa;
	}

	public EmpresaDTO findByIdApi(Long codigo) throws EntityNotFoundException, IOException {
		var empresa = findById(codigo);
		if (empresa == null)
			throw new IOException("Sem abrangência para essa Empresa.");
		return new EmpresaDTO(empresa);
	}

	public Empresa empresaFindByCnpjEntity(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código da Empresa está nulo.");
		Optional<Empresa> emp = empresaRepository.findByEmpcnp(codigo);
		if (!emp.isPresent() || emp.get().getEmpcod() == 0) {
			log.info("Empresa não encontrado ou deletado.");
			return null;
		}
		return emp.get();
	}

	public EmpresaDTO empresaFindByCnpjAbrangencia(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código da Empresa está nulo.");
		Empresa emp = empresaFindByCnpjEntity(codigo);
		if (emp == null)
			return null;
		Empresa empAbrangencia = findByIdAbrangencia(emp);
		if (empAbrangencia == null)
			return null;
		return new EmpresaDTO(emp);
	}

	public EmpresaDTO empresaFindByCnpjApi(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código da Empresa está nulo.");
		var empresa = empresaFindByCnpjAbrangencia(codigo);
		if (empresa == null)
			throw new RuntimeException("Sem abrangencia para essa empresa");
		return empresa;
	}

}
