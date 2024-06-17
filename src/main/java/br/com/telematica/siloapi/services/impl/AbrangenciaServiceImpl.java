package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
import br.com.telematica.siloapi.model.AbrangenciaModel;
import br.com.telematica.siloapi.model.dto.AbrangenciaDTO;
import br.com.telematica.siloapi.model.dto.AbrangenciaDetalhesDTO;
import br.com.telematica.siloapi.model.dto.AbrangenciaListaDetalhesDTO;
import br.com.telematica.siloapi.model.entity.Abrangencia;
import br.com.telematica.siloapi.model.entity.AbrangenciaDetalhes;
import br.com.telematica.siloapi.model.entity.Recurso;
import br.com.telematica.siloapi.records.ItensAbrangentes;
import br.com.telematica.siloapi.repository.AbrangenciaDetalhesRepository;
import br.com.telematica.siloapi.repository.AbrangenciaRepository;
import br.com.telematica.siloapi.services.AbrangenciaServiceInterface;
import br.com.telematica.siloapi.utils.JsonNodeConverter;
import br.com.telematica.siloapi.utils.Utils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AbrangenciaServiceImpl implements AbrangenciaServiceInterface {

	private static final Logger logs = LoggerFactory.getLogger(Abrangencia.class);

	@Autowired
	private AbrangenciaRepository abrangenciaRepository;
	@Autowired
	private AbrangenciaDetalhesRepository abrangenciaDetalhesRepository;
	@Autowired
	private RecursoServiceImpl recursoService;
	@Autowired
	private EmpresaServiceImpl empresaService;

	public AbrangenciaDetalhes findByAbrangenciaAndRecursoContaining(Abrangencia codigo, Recurso nome) {
		var entity = abrangenciaDetalhesRepository.findByAbrangencia_abrcodAndRecurso_recnomContaining(codigo.getAbrcod(), nome.getRecnom());
		if (entity.isEmpty())
			throw new EntityNotFoundException("Detalhes de Abrangencia não encontrada no banco de dados.");
		return entity.get();
	}

	public AbrangenciaDetalhes findByAbrangenciaAndRecursoContainingAbrangencia(Abrangencia codigo, Recurso nome) {
		var entity = abrangenciaDetalhesRepository.findByAbrangencia_abrcodAndRecurso_recnomContaining(codigo.getAbrcod(), nome.getRecnom());
		if (entity.isEmpty())
			return null;
		return entity.get();
	}

	public Abrangencia findByIdEntity(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		var entity = abrangenciaRepository.findById(codigo);
		if (entity.isEmpty())
			return null;
		return entity.get();
	}

	public Abrangencia findByIdEntity(String nome) {
		var entity = abrangenciaRepository.findByAbrnomLike(nome);
		if (entity.isEmpty())
			return null;
		return entity.get();
	}

	public AbrangenciaDTO findByIdSimples(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		Abrangencia abrangencia = findByIdEntity(codigo);
		if (abrangencia == null)
			return null;
		return new AbrangenciaDTO(abrangencia);
	}

	public AbrangenciaListaDetalhesDTO findIdApi(@NonNull Long codigo) throws EntityNotFoundException, IOException {

		Abrangencia abrangencia = findByIdEntity(codigo);
		if (abrangencia == null)
			return null;

		List<AbrangenciaDetalhesDTO> detailsDTOList = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(abrangencia.getAbrcod()).stream().map(map -> new AbrangenciaDetalhesDTO(map)).collect(Collectors.toList());
		return new AbrangenciaListaDetalhesDTO(abrangencia, detailsDTOList.isEmpty() ? null : detailsDTOList);
	}

	public List<AbrangenciaListaDetalhesDTO> findAllApi() throws EntityNotFoundException, IOException {
		List<Abrangencia> abrangenciaList = abrangenciaRepository.findAll();

		return abrangenciaList.stream().map(abrangencia -> {
			var details = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(abrangencia.getAbrcod());
			var detailsDTOList = details.stream().map(map -> new AbrangenciaDetalhesDTO(map)).collect(Collectors.toList());
			return new AbrangenciaListaDetalhesDTO(abrangencia, detailsDTOList);
		}).collect(Collectors.toList());
	}

	public Page<AbrangenciaListaDetalhesDTO> findAllPageApi(String nome, Pageable pageable) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(pageable, "Pageable do Abrangencia está nulo.");
		Specification<Abrangencia> spec;
		spec = Abrangencia.filterByFields(nome);

		Page<Abrangencia> result = abrangenciaRepository.findAll(spec, pageable);

		return result.map(abrangencia -> {
			var details = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(abrangencia.getAbrcod());
			var detailsDTOList = details.stream().map(map -> new AbrangenciaDetalhesDTO(map)).collect(Collectors.toList()); // Collect the stream into a list
			return new AbrangenciaListaDetalhesDTO(abrangencia, detailsDTOList); // Pass the collected list to the constructor
		});
	}

	public Abrangencia createUpdateAbrangencia(@NonNull Abrangencia abrangencia) {
		return abrangenciaRepository.save(abrangencia);
	}

	public AbrangenciaDetalhes createDetalhesAbrangencia(@NonNull AbrangenciaDetalhes detalhes) {
		return abrangenciaDetalhesRepository.save(detalhes);
	}

	public AbrangenciaListaDetalhesDTO saveDetalhesApi(AbrangenciaModel abrangenciaModel) throws IOException {
		long codigo = 0;
		Objects.requireNonNull(abrangenciaModel.getNome(), "Nome da Abrangência está nulo.");
		try {
			Objects.requireNonNull(abrangenciaModel.getNome(), "Nome da Abrangência está nulo.");
			var abrangencia = createUpdateAbrangencia(new Abrangencia(null, abrangenciaModel.getNome().toString(), abrangenciaModel.getDescricao()));
			codigo = abrangencia.getAbrcod();

			var abrangenciaDetalhesDTOList = abrangenciaModel.getRecursos() == null ? null : abrangenciaModel.getRecursos().stream().map(recurso -> {
				Objects.requireNonNull(recurso.getRecurso(), "Nome do recursoestá nulo.");
				Objects.requireNonNull(recurso.getHierarquia(), "Valor da Hierarquia está nulo.");
				var recursoEntity = recursoService.findByIdEntity(recurso.getRecurso().getNome());
				JsonNodeConverter jsonNode = new JsonNodeConverter();
				var entity = new AbrangenciaDetalhes(null, abrangencia, recursoEntity, recurso.getHierarquia(), jsonNode.convertToDatabaseColumn(recurso.getDados()));
				var abrangenciaDetalhesSave = abrangenciaDetalhesRepository.save(entity);

				return new AbrangenciaDetalhesDTO(abrangenciaDetalhesSave);
			}).collect(Collectors.toList());

			return new AbrangenciaListaDetalhesDTO(abrangencia, abrangenciaDetalhesDTOList);

		} catch (Exception e) {
			logs.error("Erro ao criar a Abrangencia e Abrangencia_Detalhes: ", e);
			abrangenciaRepository.deleteById(codigo);
			throw new IOException(e);
		}
	}

	public AbrangenciaListaDetalhesDTO updateDetalhesApi(Long codigo, AbrangenciaModel abrangenciaModel) {
		Objects.requireNonNull(codigo, "Código do Abrangência está nulo.");
		Objects.requireNonNull(abrangenciaModel.getNome(), "Nome da Abrangência está nulo.");
		var abrangencia = createUpdateAbrangencia(new Abrangencia(codigo, abrangenciaModel.getNome().toString(), abrangenciaModel.getDescricao()));

		var abrangenciaDetalhesDTOList = abrangenciaModel.getRecursos() == null ? null : abrangenciaModel.getRecursos().stream().map(recurso -> {
			Objects.requireNonNull(recurso.getRecurso(), "Nome do recursoestá nulo.");
			Objects.requireNonNull(recurso.getHierarquia(), "Valor da Hierarquia está nulo.");
			var recursoEntity = recursoService.findByIdEntity(recurso.getRecurso().getNome());
			var abDetalhes = abrangenciaDetalhesRepository.findByAbrangencia_abrcodAndRecurso_recnomContaining(abrangencia.getAbrcod(), recursoEntity.getRecnom());
			JsonNodeConverter jsonNode = new JsonNodeConverter();
			var entity = new AbrangenciaDetalhes(abDetalhes.isEmpty() ? codigo : abDetalhes.get().getAbdcod(), abrangencia, recursoEntity, recurso.getHierarquia(), jsonNode.convertToDatabaseColumn(recurso.getDados()));
			var abrangenciaDetalhesSave = abrangenciaDetalhesRepository.save(entity);
			return new AbrangenciaDetalhesDTO(abrangenciaDetalhesSave);
		}).collect(Collectors.toList());

		if (abrangenciaDetalhesDTOList == null || abrangenciaDetalhesDTOList.isEmpty())
			abrangenciaDetalhesRepository.deleteById(codigo);

		return new AbrangenciaListaDetalhesDTO(abrangencia, abrangenciaDetalhesDTOList);
	}

	public ResponseGlobalModel deleteDetalhesApi(@NonNull Long codigo) throws IOException {
		try {
			var entityAbrangencia = findByIdEntity(codigo);
			var listAbrangenciaDetalhes = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(entityAbrangencia.getAbrcod());
			if (!listAbrangenciaDetalhes.isEmpty()) {
				listAbrangenciaDetalhes.forEach(map -> abrangenciaDetalhesRepository.deleteById(map.getAbdcod()));
				abrangenciaRepository.deleteById(codigo);
				return Utils.responseMessageSucess("Apagado com Sucesso.");
			}

			abrangenciaRepository.deleteById(codigo);
			return Utils.responseMessageSucess("Apagado com Sucesso.");
		} catch (Exception e) {
			throw new IOException("Erro ao apagar o item. Mensagem" + e.getMessage());
		}
	}

	public ItensAbrangentes findByItemAbrangenceEntity() {

		var empresaList = empresaService.findByEmpresa();
		// add itens que seram abrangentes
		return new ItensAbrangentes(empresaList);
	}

	@Override
	public ItensAbrangentes findByItemAbrangence() {
		return findByItemAbrangenceEntity();
	}

	@Override
	public Page<AbrangenciaListaDetalhesDTO> findAll(String nome, Pageable pageable) throws EntityNotFoundException, IOException {
		return findAllPageApi(nome, pageable);
	}

	@Override
	public AbrangenciaListaDetalhesDTO save(AbrangenciaModel abrangenciaModel) throws IOException {
		return saveDetalhesApi(abrangenciaModel);
	}

	@Override
	public AbrangenciaListaDetalhesDTO update(Long codigo, AbrangenciaModel abrangenciaModel) {
		return updateDetalhesApi(codigo, abrangenciaModel);
	}

	@Override
	public List<AbrangenciaListaDetalhesDTO> findAll() throws EntityNotFoundException, IOException {
		return findAllApi();
	}

	@Override
	public AbrangenciaListaDetalhesDTO findById(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		return findIdApi(codigo);
	}

	@Override
	public ResponseGlobalModel delete(@NonNull Long perfil) throws IOException {
		return deleteDetalhesApi(perfil);
	}

}
