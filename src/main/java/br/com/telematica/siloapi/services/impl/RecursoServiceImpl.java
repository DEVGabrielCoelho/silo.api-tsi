package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.RecursoModel;
import br.com.telematica.siloapi.model.dto.RecursoDTO;
import br.com.telematica.siloapi.model.entity.Recurso;
import br.com.telematica.siloapi.model.enums.RecursoMapEnum;
import br.com.telematica.siloapi.repository.RecursoRepository;
import br.com.telematica.siloapi.services.RecursoServiceInterface;
import br.com.telematica.siloapi.utils.Utils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RecursoServiceImpl implements RecursoServiceInterface {

	@Autowired
	private RecursoRepository recursoRepository;

	public Recurso findByIdEntity(@NonNull Long codigo) {
		var entity = recursoRepository.findById(codigo);
		if (entity.isEmpty())
			return null;
		return entity.get();
	}

	public Recurso findByIdEntity(@NonNull String nome) {
		var entity = recursoRepository.findByRecnom(nome);
		if (entity.isEmpty())
			return null;
		return entity.get();
	}

	public RecursoDTO findByIdApi(@NonNull String codigo) throws EntityNotFoundException, IOException {
		var entity = findByIdEntity(codigo);
		if (entity == null)
			return null;

		return new RecursoDTO(entity);
	}

	public RecursoDTO findByIdApi(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		var entity = findByIdEntity(codigo);
		if (entity == null)
			return null;

		return new RecursoDTO(entity);
	}

	public Recurso saveEntity(RecursoModel recursoModel) {
		Objects.requireNonNull(recursoModel.getNome(), "Nome do Recurso está nulo.");
		return recursoRepository.save(new Recurso(null, RecursoMapEnum.mapDescricaoToNome(recursoModel.getNome().getNome()), recursoModel.getDescricao()));
	}

	public Recurso updateEntity(Long codigo, RecursoModel recursoModel) {
		Objects.requireNonNull(codigo, "Código do Recurso está nulo.");
		Objects.requireNonNull(recursoModel.getNome(), "Nome do Recurso está nulo.");
		return recursoRepository.save(new Recurso(codigo, recursoModel.getNome().getNome(), recursoModel.getDescricao()));
	}

	public List<Recurso> findAllEntity() throws EntityNotFoundException, IOException {
		return recursoRepository.findAll();
	}

	public ResponseGlobalModel deleteEntity(@NonNull Long perfil) throws IOException {
		try {
			recursoRepository.deleteById(perfil);
			return Utils.responseMessageSucess("Apagado com Sucesso.");
		} catch (Exception e) {
			throw new IOException("Erro ao apagar o item. Mensagem" + e.getMessage());
		}
	}

	public Page<Recurso> findAllEntity(String nome, Pageable pageable) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(pageable, "Pageable do Recurso está nulo.");
		Page<Recurso> result;
		if (nome == null)
			result = recursoRepository.findAll(pageable);
		else
			result = recursoRepository.findByRecnomLike(nome, pageable);
		return result;
	}

	@Override
	public Page<RecursoDTO> findAll(String nome, Pageable pageable) throws EntityNotFoundException, IOException {
		return findAllEntity(nome, pageable).map(map -> new RecursoDTO(map));
	}

	@Override
	public RecursoDTO save(RecursoModel recursoModel) {
		return new RecursoDTO(saveEntity(recursoModel));
	}

	@Override
	public RecursoDTO update(Long codigo, RecursoModel recursoModel) {
		return new RecursoDTO(updateEntity(codigo, recursoModel));
	}

	@Override
	public List<RecursoDTO> findAll() throws EntityNotFoundException, IOException {
		return findAllEntity().stream().map(map -> new RecursoDTO(map)).collect(Collectors.toList());
	}

	@Override
	public RecursoDTO findById(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		return findByIdApi(codigo);
	}

	@Override
	public RecursoDTO findByString(@NonNull String nome) throws EntityNotFoundException, IOException {
		return findByIdApi(nome);
	}

	@Override
	public ResponseGlobalModel delete(@NonNull Long codigo) throws IOException {
		return deleteEntity(codigo);
	}
}
