package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.handler.AbrangenciaHandler;
import br.com.telematica.siloapi.model.EmpresaModel;
import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.model.entity.Empresa;
import br.com.telematica.siloapi.records.CheckAbrangenciaRec;
import br.com.telematica.siloapi.repository.EmpresaRepository;
import br.com.telematica.siloapi.services.EmpresaServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EmpresaServiceImpl implements EmpresaServInterface {

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private AbrangenciaHandler abrancenciaHandler;

	public CheckAbrangenciaRec checagemFixaBarragem() throws EntityNotFoundException, IOException {
		return abrancenciaHandler.checkAbrangencia("EMPRESA");
	}

	@Override
	public ResponseEntity<EmpresaDTO> empresaDeleteById(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código da Empresa está nulo.");

		try {
			var empresa = empresaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException("Não foi possível encontrar a empresa com o ID fornecido.", 1));

			if (empresa.getEmpdel() == 0) {
				log.info("Empresa não encontrada ou já deletada.");
				throw new IOException("Empresa não encontrada ou já deletada.");
			}

			empresa.empresaDel(0);
			empresaRepository.save(empresa);

			return MessageResponse.success(null);
		} catch (EmptyResultDataAccessException e) {
			log.error("Não foi possível encontrar a empresa com o ID fornecido. Erro: ", e);
			throw new IOException("Não foi possível encontrar a empresa com o ID fornecido.", e);
		} catch (Exception e) {
			log.error("Erro ao deletar a empresa. Erro: ", e);
			throw new IOException("Erro ao deletar a empresa.", e);
		}
	}

	@Override
	public ResponseEntity<Page<EmpresaDTO>> empresaFindAllPaginado(String nome, Pageable pageable) throws IOException {
		Objects.requireNonNull(pageable, "Pageable da Empresa está nulo.");

		var check = checagemFixaBarragem();
		Integer empdel = 1;

		Specification<Empresa> spec = Specification.where(null);

		if (check.isHier() == 0) {
			spec = spec.and(Empresa.filterByFields(nome, empdel, null));
		} else {
			spec = spec.and(Empresa.filterByFields(nome, empdel, check.listAbrangencia()));
		}

		Page<Empresa> result = empresaRepository.findAll(spec, pageable);

		return MessageResponse.success(result.map(EmpresaDTO::new));
	}

	public List<EmpresaDTO> findByEmpresa() {
		try {

			var result = empresaRepository.findByEmpdel(1);
			return result.stream().map(empresa -> new EmpresaDTO(empresa)).collect(Collectors.toList());
		} catch (Exception e) {
			log.info("Erro ", e);
			return null;
		}
	}

	@Override
	public ResponseEntity<List<EmpresaDTO>> empresaFindAll() throws IOException {
		var check = checagemFixaBarragem();
		var result = check.isHier() == 0 ? empresaRepository.findByEmpdel(1) : empresaRepository.findByEmpdelAndEmpcodIn(1, check.listAbrangencia());

		return MessageResponse.success(result.stream().map(empresa -> new EmpresaDTO(empresa)).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<EmpresaDTO> empresaUpdate(Long codigo, EmpresaModel empresaModel) throws IOException {
		Objects.requireNonNull(codigo, "Código da Empresa está nulo.");
		Objects.requireNonNull(empresaModel.getCnpj(), "CNPJ da Empresa está nulo.");
		Objects.requireNonNull(empresaModel.getNome(), "Nome da Empresa está nulo.");
		var empresa = empresaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException("Não foi possível encontrar a empresa com o ID fornecido.", 1));
		if (empresa == null || empresa.getEmpdel() == 0) {
			log.info("Empresa não encontrado ou deletado.");
			throw new IOException("Empresa não encontrado ou deletado.");
		}
		String nomFant = empresaModel.getNomeFantasia() == null ? empresa.getEmpfan() : empresaModel.getNomeFantasia();
		String tel = empresaModel.getTelefone() == null ? empresa.getEmptel() : empresaModel.getTelefone();
		var empresaEntity = new Empresa(codigo, empresaModel.getCnpj(), empresaModel.getNome(), nomFant, tel, 1);
		return MessageResponse.success(new EmpresaDTO(empresaRepository.save(empresaEntity)));

	}

	@Override
	public ResponseEntity<EmpresaDTO> empresaSave(EmpresaModel empresaModel) throws IOException {
		Objects.requireNonNull(empresaModel.getCnpj(), "CNPJ da Empresa está nulo.");
		Objects.requireNonNull(empresaModel.getNome(), "Nome da Empresa está nulo.");
		Empresa emp = null;
		try {
			Empresa empresa = new Empresa(null, empresaModel.getCnpj(), empresaModel.getNome(), empresaModel.getNomeFantasia(), empresaModel.getTelefone(), 1);
			emp = empresaRepository.save(empresa);
		} catch (Exception e) {
			log.error("Erro ao realizar o cadastro de uma empresa.", e);
			throw new IOException("Erro ao realizar o cadastro de uma empresa.", e);
		}
		return MessageResponse.success(new EmpresaDTO(emp));
	}

	@Override
	public ResponseEntity<EmpresaDTO> findByIdApi(Long codigo) throws EntityNotFoundException, IOException {
		var empresa = findById(codigo);
		if (empresa == null)
			throw new IOException("Sem abrangência para essa Empresa.");
		return MessageResponse.success(new EmpresaDTO(empresa));
	}

	@Override
	public ResponseEntity<EmpresaDTO> empresaFindByCnpjApi(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código da Empresa está nulo.");
		var empresa = empresaFindByCnpjAbrangencia(codigo);
		if (empresa == null)
			throw new RuntimeException("Sem abrangencia para essa empresa");
		return MessageResponse.success(empresa);
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
		var emp = empresaRepository.findById(codigo).get();
		if (emp == null)
			return null;
		var empresa = findByIdAbrangencia(emp);
		if (empresa == null)
			return null;
		return empresa;
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

	public Empresa findByIdEntity(@NonNull Long codigo) {
		Objects.requireNonNull(codigo, "Código da Empresa está nulo.");
		var entity = empresaRepository.findById(codigo);
		if (entity.isEmpty() || entity.get().getEmpdel() == 0) {
			log.info("Empresa não encontrado ou deletado.");
			return null;
		}
		return entity.get();
	}

}
