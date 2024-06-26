package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.AbrangenciaDetalhesModel;
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
import br.com.telematica.siloapi.services.AbrangenciaServInterface;
import br.com.telematica.siloapi.utils.JsonNodeConverter;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;

@Service
public class AbrangenciaServiceImpl implements AbrangenciaServInterface {

	private static final Logger log = LoggerFactory.getLogger(AbrangenciaServiceImpl.class);

	@Autowired
	private AbrangenciaRepository abrangenciaRepository;
	@Autowired
	private AbrangenciaDetalhesRepository abrangenciaDetalhesRepository;
	@Autowired
	private RecursoServiceImpl recursoService;
	@Autowired
	private EmpresaServiceImpl empresaService;

	public AbrangenciaDetalhes findByAbrangenciaAndRecursoContaining(Abrangencia codigo, Recurso nome) {
		List<AbrangenciaDetalhes> results = abrangenciaDetalhesRepository.findByAbrangencia_abrcodAndRecurso_recnomContaining(codigo.getAbrcod(), nome.getRecnom());

		if (results.size() > 1) {
			throw new NonUniqueResultException("Query did not return a unique result: " + results.size() + " results were returned");
		}

		return results.isEmpty() ? null : results.get(0);
	}

	public AbrangenciaDetalhes findByAbrangenciaAndRecursoContainingAbrangencia(Abrangencia codigo, Recurso nome) {
		List<AbrangenciaDetalhes> results = abrangenciaDetalhesRepository.findByAbrangencia_abrcodAndRecurso_recnomContaining(codigo.getAbrcod(), nome.getRecnom());
		return results.get(0);
	}

	public Abrangencia findByIdEntity(@NonNull Long codigo) throws EntityNotFoundException {
		return abrangenciaRepository.findById(codigo).orElseThrow(() -> new EntityNotFoundException("Abrangência não encontrada com o código: " + codigo));
	}

	public Abrangencia findByIdEntity(String nome) {
		return abrangenciaRepository.findByAbrnomLike(nome).orElse(null);
	}

	public AbrangenciaDTO findByIdSimples(@NonNull Long codigo) throws EntityNotFoundException {
		Abrangencia abrangencia = findByIdEntity(codigo);
		return new AbrangenciaDTO(abrangencia);
	}

	public ItensAbrangentes findByItemAbrangenceEntity() {
		var empresaList = empresaService.findByEmpresa();
		return new ItensAbrangentes(empresaList);
	}

	@Override
	public ResponseEntity<ItensAbrangentes> findByItemAbrangence() {
		return MessageResponse.success(findByItemAbrangenceEntity());
	}

	@Override
	public ResponseEntity<Page<AbrangenciaListaDetalhesDTO>> findAll(String nome, Pageable pageable) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(pageable, "Pageable da Abrangência está nulo.");
		Specification<Abrangencia> spec = Specification.where(null);
		spec = spec.and(Abrangencia.filterByFields(nome));

		Page<Abrangencia> result = abrangenciaRepository.findAll(spec, pageable);

		return MessageResponse.success(result.map(abrangencia -> {
			var details = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(abrangencia.getAbrcod());
			var detailsDTOList = details.stream().map(AbrangenciaDetalhesDTO::new).collect(Collectors.toList());
			return new AbrangenciaListaDetalhesDTO(abrangencia, detailsDTOList);
		}));
	}

	@Override
	public ResponseEntity<AbrangenciaListaDetalhesDTO> save(AbrangenciaModel abrangenciaModel) throws IOException {
		Abrangencia abrangencia = null;
		try {
			Objects.requireNonNull(abrangenciaModel.getNome(), "Nome da Abrangência está nulo.");
			// Verifica se a descrição também não é nula, caso seja obrigatória.
			Objects.requireNonNull(abrangenciaModel.getDescricao(), "Descrição da Abrangência está nula.");

			abrangencia = abrangenciaRepository.save(new Abrangencia(null, abrangenciaModel.getNome(), abrangenciaModel.getDescricao()));

			var detalhes = createAbrangenciaDetalhesList(abrangencia, abrangenciaModel.getRecursos());

			return MessageResponse.create(new AbrangenciaListaDetalhesDTO(abrangencia, detalhes));
		} catch (DataIntegrityViolationException e) {
			log.error("Erro de integridade ao criar a Abrangência: ", e);
			if (abrangencia != null && abrangencia.getAbrcod() != null)
				abrangenciaRepository.deleteById(abrangencia.getAbrcod());

			throw new IOException("Erro de integridade ao criar a Abrangência.", e);
		} catch (Exception e) {
			log.error("Erro ao criar a Abrangência e seus detalhes: ", e);
			if (abrangencia != null && abrangencia.getAbrcod() != null)
				abrangenciaRepository.deleteById(abrangencia.getAbrcod());

			throw new IOException("Erro inesperado ao criar a Abrangência.", e);
		}
	}

	public List<AbrangenciaDetalhesDTO> createAbrangenciaDetalhesList(Abrangencia abrangencia, List<AbrangenciaDetalhesModel> recursos) {
		return recursos == null ? List.of() // Retorna uma lista vazia em vez de null.
				: recursos.stream().map(recurso -> new AbrangenciaDetalhesDTO(saveOrUpdateAbrangenciaDetalhes(abrangencia, recurso))).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<AbrangenciaListaDetalhesDTO> update(Long codigo, AbrangenciaModel abrangenciaModel) {
		Objects.requireNonNull(codigo, "Código da Abrangência está nulo.");
		Objects.requireNonNull(abrangenciaModel.getNome(), "Nome da Abrangência está nulo.");

		Abrangencia abrangencia = abrangenciaRepository.save(new Abrangencia(codigo, abrangenciaModel.getNome(), abrangenciaModel.getDescricao()));

		List<AbrangenciaDetalhesDTO> abrangenciaDetalhesDTOList = abrangenciaModel.getRecursos() == null ? null : abrangenciaModel.getRecursos().stream().map(recurso -> new AbrangenciaDetalhesDTO(saveOrUpdateAbrangenciaDetalhes(abrangencia, recurso))).collect(Collectors.toList());

		if (abrangenciaDetalhesDTOList == null || abrangenciaDetalhesDTOList.isEmpty()) {
			abrangenciaDetalhesRepository.deleteById(codigo);
		}

		return MessageResponse.create(new AbrangenciaListaDetalhesDTO(abrangencia, abrangenciaDetalhesDTOList));
	}

	@Override
	public ResponseEntity<List<AbrangenciaListaDetalhesDTO>> findAll() throws EntityNotFoundException, IOException {
		List<Abrangencia> abrangenciaList = abrangenciaRepository.findAll();

		List<AbrangenciaListaDetalhesDTO> abrangenciaDTOList = abrangenciaList.stream().map(abrangencia -> {
			var details = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(abrangencia.getAbrcod());
			var detailsDTOList = details.stream().map(AbrangenciaDetalhesDTO::new).collect(Collectors.toList());
			return new AbrangenciaListaDetalhesDTO(abrangencia, detailsDTOList);
		}).collect(Collectors.toList());

		return MessageResponse.success(abrangenciaDTOList);
	}

	@Override
	public ResponseEntity<AbrangenciaListaDetalhesDTO> findById(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		Abrangencia abrangencia = findByIdEntity(codigo);

		List<AbrangenciaDetalhesDTO> detailsDTOList = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(abrangencia.getAbrcod()).stream().map(AbrangenciaDetalhesDTO::new).collect(Collectors.toList());

		return MessageResponse.success(new AbrangenciaListaDetalhesDTO(abrangencia, detailsDTOList.isEmpty() ? null : detailsDTOList));
	}

	@Override
	public ResponseEntity<AbrangenciaListaDetalhesDTO> delete(@NonNull Long codigo) throws IOException {

		Abrangencia entityAbrangencia = findByIdEntity(codigo);

		var listAbrangenciaDetalhes = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(entityAbrangencia.getAbrcod());

		listAbrangenciaDetalhes.forEach(map -> {
			Long abdcod = map.getAbdcod();
			if (abdcod != null) {
				abrangenciaDetalhesRepository.deleteById(abdcod);
			} else {
				log.warn("Detalhe da abrangência com ID nulo encontrado. Ignorando deleção.");
			}
		});

		abrangenciaRepository.deleteById(codigo);
		return MessageResponse.success(null);

	}

	public AbrangenciaDetalhes saveOrUpdateAbrangenciaDetalhes(Abrangencia abrangencia, AbrangenciaDetalhesModel recurso) {
		String recursoNome = Objects.requireNonNull(recurso.getRecurso().getNome(), "Nome do recurso está nulo.");
		Objects.requireNonNull(recurso.getHierarquia(), "Valor da Hierarquia está nulo.");

		Recurso recursoEntity = recursoService.findByIdEntity(recursoNome);

		AbrangenciaDetalhes detalhesOpt = findByAbrangenciaAndRecursoContaining(abrangencia, recursoEntity);

		JsonNodeConverter jsonNode = new JsonNodeConverter();
		AbrangenciaDetalhes detalhes = new AbrangenciaDetalhes(detalhesOpt == null ? null : detalhesOpt.getAbdcod(), abrangencia, recursoEntity, recurso.getHierarquia(), jsonNode.convertToDatabaseColumn(recurso.getDados()));

		return abrangenciaDetalhesRepository.save(detalhes);
	}

	public AbrangenciaDetalhes saveOrUpdateAbrangenciaDetalhes(Abrangencia abrangencia, AbrangenciaDetalhes detalhes) {
		Objects.requireNonNull(abrangencia, "Abrangência está nula.");
		Objects.requireNonNull(detalhes, "Detalhes estão nulos.");
		Objects.requireNonNull(detalhes.getRecurso(), "Recurso está nulo.");
		Objects.requireNonNull(detalhes.getAbdhie(), "Hierarquia está nula.");
		Objects.requireNonNull(detalhes.getAbddat(), "Dados estão nulos.");

		Recurso recurso = detalhes.getRecurso();

		AbrangenciaDetalhes detalhesOpt = findByAbrangenciaAndRecursoContaining(abrangencia, recurso);

		AbrangenciaDetalhes abrangenciaDetalhes = new AbrangenciaDetalhes(detalhesOpt == null ? null : detalhesOpt.getAbdcod(), abrangencia, recurso, detalhes.getAbdhie(), detalhes.getAbddat());

		return abrangenciaDetalhesRepository.save(abrangenciaDetalhes);
	}

	public Abrangencia createUpdateAbrangencia(Abrangencia abrangencia) {
		return abrangenciaRepository.save(abrangencia);
	}
}
