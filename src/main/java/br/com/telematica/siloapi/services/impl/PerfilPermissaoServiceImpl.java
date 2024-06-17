package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.PerfilModel;
import br.com.telematica.siloapi.model.PermissaoModel;
import br.com.telematica.siloapi.model.dto.PerfilPermissaoDTO;
import br.com.telematica.siloapi.model.dto.PermissaoDTO;
import br.com.telematica.siloapi.model.dto.RecursoDTO;
import br.com.telematica.siloapi.model.entity.Perfil;
import br.com.telematica.siloapi.model.entity.Permissao;
import br.com.telematica.siloapi.model.entity.Recurso;
import br.com.telematica.siloapi.repository.PerfilRepository;
import br.com.telematica.siloapi.repository.PermissaoRepository;
import br.com.telematica.siloapi.services.PerfilPermissaoServiceInterface;
import br.com.telematica.siloapi.utils.Utils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PerfilPermissaoServiceImpl implements PerfilPermissaoServiceInterface {

	@Autowired
	private PermissaoRepository permissaoRepository;

	@Autowired
	private RecursoServiceImpl recursoService;

	@Autowired
	private PerfilRepository perfilRepository;

	public Perfil findByIdPerfilEntity(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		var entity = perfilRepository.findById(codigo);
		if (entity.isEmpty())
			return null;
		return entity.get();
	}

	public Perfil findByIdPerfilEntity(@NonNull String codigo) {
		var entity = perfilRepository.findByPernom(codigo);
		if (entity.isEmpty())
			return null;
		return entity.get();
	}

	public Perfil findByIdApiPerfil(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		var entity = findByIdPerfilEntity(codigo);
		if (entity == null)
			return null;

		return entity;
	}

	public Perfil findByIdApiPerfil(@NonNull String nome) throws EntityNotFoundException, IOException {
		var entity = findByIdPerfilEntity(nome);
		if (entity == null)
			return null;
		return entity;
	}

	public PerfilPermissaoDTO findByIdPerfil(Long codigo) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(codigo, "Código do Perfil está nulo.");
		var perfil = perfilRepository.findById(codigo).get();
		if (perfil == null)
			return null;
		List<PermissaoDTO> permissoes = permissaoRepository.findByPerfil_percod(perfil.getPercod()).get().stream().map(map -> new PermissaoDTO(map)).collect(Collectors.toList());
		return new PerfilPermissaoDTO(perfil, permissoes);
	}

	public List<PerfilPermissaoDTO> findAllPerfil() throws EntityNotFoundException, IOException {
		List<PerfilPermissaoDTO> listPerfilAndPermission = perfilRepository.findAll().stream().map(perfil -> {
			List<Permissao> permissoes = permissaoRepository.findByPerfil_percod(perfil.getPercod()).get();
			List<PermissaoDTO> listDTO = permissoes.stream().map(map -> new PermissaoDTO(map)).collect(Collectors.toList());
			return new PerfilPermissaoDTO(perfil, listDTO);
		}).collect(Collectors.toList());
		return listPerfilAndPermission;
	}

	public Page<PerfilPermissaoDTO> findAllPagePerfil(String nome, Pageable pageable) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(pageable, "Pageable do Perfil está nulo.");

		Specification<Perfil> spec;
		spec = Perfil.filterByFields(nome);

		Page<Perfil> result = perfilRepository.findAll(spec, pageable);
		return result.map(perfil -> {
			List<PermissaoDTO> permissoes = permissaoRepository.findByPerfil_percod(perfil.getPercod()).get().stream().map(map -> new PermissaoDTO(map)).collect(Collectors.toList());
			return new PerfilPermissaoDTO(perfil, permissoes);
		});
	}

	public Permissao findByPerfilAndRecurso(Perfil codigo, Recurso recurso) {
		var entity = permissaoRepository.findByPerfil_percodAndRecurso_recnom(codigo.getPercod(), recurso.getRecnom());
		if (entity.isEmpty())
			throw new EntityNotFoundException("Permissao não encontrada no banco de dados para perfil " + codigo + " e recurso " + recurso + ".");
		return entity.get();
	}

	public Permissao findByPercodAndRecnomCreate(Perfil codigo, Recurso recurso) {
		var entity = permissaoRepository.findByPerfil_percodAndRecurso_recnom(codigo.getPercod(), recurso.getRecnom());
		if (entity.isEmpty())
			return null;
		return entity.get();
	}

	public Permissao findByEntity(@NonNull Long codigo) {
		var entity = permissaoRepository.findById(codigo);
		if (entity.isEmpty())
			return null;
		return entity.get();
	}

	public PermissaoDTO findByIdApi(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		var entity = findByEntity(codigo);
		if (entity == null)
			return null;
		return new PermissaoDTO(entity);
	}

	public Perfil createUpdatePerfil(@NonNull Perfil perfil) {
		return perfilRepository.save(perfil);
	}

	public PerfilPermissaoDTO savePerfilApi(PerfilModel perModel) throws IOException {
		try {
			Objects.requireNonNull(perModel.getNome(), "Nome do Perfil está nulo.");
			Perfil perfil = createUpdatePerfil(new Perfil(null, perModel.getNome().toUpperCase(), perModel.getDescricao()));

			List<PermissaoDTO> permissaoList = new ArrayList<PermissaoDTO>();
			for (PermissaoModel permissao : perModel.getPermissoes()) {
				permissaoList.add(saveEntityPermissao(perfil, permissao));
			}

			return new PerfilPermissaoDTO(perfil, permissaoList);
		} catch (Exception e) {
			throw new IOException("Erro ao criar um Perfil." + e.getMessage());
		}
	}

	public PerfilPermissaoDTO updatePerfilApi(Long codigo, PerfilModel perModel) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(codigo, "Código do Perfil está nulo.");
		Objects.requireNonNull(perModel.getNome(), "Nome do Perfil está nulo.");
		Perfil perfil = findByIdPerfilEntity(codigo);
		if (perfil == null)
			throw new IOException("Perfil não encontrado.");
		perfil = perfilRepository.save(new Perfil(codigo, perModel.getNome().toUpperCase(), perModel.getDescricao()));

		List<PermissaoDTO> permissaoList = new ArrayList<PermissaoDTO>();
		for (PermissaoModel permissao : perModel.getPermissoes()) {
			permissaoList.add(updateEntityPermissao(perfil, permissao));
		}
		return new PerfilPermissaoDTO(perfil, permissaoList);
	}

	public PermissaoDTO saveEntityPermissao(Perfil perfil, @NonNull PermissaoModel permissao) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(perfil, "Perfil da Permissão está nulo.");
		Objects.requireNonNull(permissao.getRecurso(), "Recuro da Permissão está nulo.");
		Objects.requireNonNull(permissao.getCriar(), "Criar da Permissão está nulo.");
		Objects.requireNonNull(permissao.getEditar(), "Editar da Permissão está nulo.");
		Objects.requireNonNull(permissao.getDeletar(), "Deletar da Permissão está nulo.");
		Objects.requireNonNull(permissao.getBuscar(), "Buscar da Permissão está nulo.");
		Objects.requireNonNull(permissao.getListar(), "Listar da Permissão está nulo.");
		var recurso = recursoService.findByIdEntity(permissao.getRecurso().getNome());
		Permissao perm = new Permissao(null, perfil, recurso, permissao.getListar(), permissao.getBuscar(), permissao.getCriar(), permissao.getEditar(), permissao.getDeletar());
		return new PermissaoDTO(permissaoRepository.save(perm));
	}

	public PermissaoDTO updateEntityPermissao(@NonNull Perfil perfil, @NonNull PermissaoModel permissao) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(perfil, "Perfil da Permissão está nulo.");
		Objects.requireNonNull(permissao.getRecurso(), "Recuro da Permissão está nulo.");
		Objects.requireNonNull(permissao.getCriar(), "Criar da Permissão está nulo.");
		Objects.requireNonNull(permissao.getEditar(), "Editar da Permissão está nulo.");
		Objects.requireNonNull(permissao.getDeletar(), "Deletar da Permissão está nulo.");
		Objects.requireNonNull(permissao.getBuscar(), "Buscar da Permissão está nulo.");
		Objects.requireNonNull(permissao.getListar(), "Listar da Permissão está nulo.");
		var recurso = recursoService.findByIdEntity(permissao.getRecurso().getNome());
		var permissaoEntity = findByPerfilAndRecurso(perfil, recurso);
		Permissao perm = new Permissao(permissaoEntity.getPemcod(), perfil, recurso, permissao.getListar(), permissao.getBuscar(), permissao.getCriar(), permissao.getEditar(), permissao.getDeletar());
		return new PermissaoDTO(permissaoRepository.save(perm));
	}

	public ResponseGlobalModel deleteEntityPerfil(@NonNull Long perfil) throws IOException {
		try {
			deleteEntityPermissao(perfil);
			perfilRepository.deleteById(perfil);
			return Utils.responseMessageSucess("Apagado com Sucesso.");
		} catch (Exception e) {
			throw new IOException("Erro ao apagar o perfil. Mensagem" + e.getMessage());
		}
	}

	public ResponseGlobalModel deleteEntityPermissao(@NonNull Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código da Permissão está nulo.");
		try {
			permissaoRepository.deleteByPerfil_Percod(codigo);
			return Utils.responseMessageSucess("Apagado com Sucesso.");
		} catch (Exception e) {
			throw new IOException("Erro ao apagar as permissões. Mensagem" + e.getMessage());
		}
	}

	public RecursoDTO getNomeRecursoDTO(@NonNull String name) throws EntityNotFoundException, IOException {
		return new RecursoDTO(recursoService.findByIdEntity(name));
	}

	@Override
	public Page<PerfilPermissaoDTO> findAll(String nome, @NonNull Pageable pageable) throws EntityNotFoundException, IOException {
		return findAllPagePerfil(nome, pageable);
	}

	@Override
	public PerfilPermissaoDTO save(PerfilModel perModel) throws IOException {
		return savePerfilApi(perModel);
	}

	@Override
	public PerfilPermissaoDTO update(@NonNull Long codigo, PerfilModel perModel) throws EntityNotFoundException, IOException {
		return updatePerfilApi(codigo, perModel);
	}

	@Override
	public List<PerfilPermissaoDTO> findAll() throws EntityNotFoundException, IOException {
		return findAllPerfil();
	}

	@Override
	public PerfilPermissaoDTO findById(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		return findByIdPerfil(codigo);
	}

	@Override
	public ResponseGlobalModel delete(@NonNull Long perfil) throws IOException {
		return deleteEntityPerfil(perfil);
	}

}
