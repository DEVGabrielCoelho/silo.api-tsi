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

@Service
public class AbrangenciaServiceImpl implements AbrangenciaServInterface {

	private static final Logger log = LoggerFactory.getLogger(Abrangencia.class);

	@Autowired
	private AbrangenciaRepository abrangenciaRepository;
	@Autowired
	private AbrangenciaDetalhesRepository abrangenciaDetalhesRepository;
	@Autowired
	private RecursoServiceImpl recursoService;
	@Autowired
	private EmpresaServiceImpl empresaService;

	public AbrangenciaDetalhes findByAbrangenciaAndRecursoContaining(Abrangencia codigo, Recurso nome) {
		return abrangenciaDetalhesRepository
				.findByAbrangencia_abrcodAndRecurso_recnomContaining(codigo.getAbrcod(), nome.getRecnom())
				.orElseThrow(() -> new EntityNotFoundException("Detalhes de Abrangencia não encontrada no banco de dados."));
	}

	public AbrangenciaDetalhes findByAbrangenciaAndRecursoContainingAbrangencia(Abrangencia codigo, Recurso nome) {
		return abrangenciaDetalhesRepository
				.findByAbrangencia_abrcodAndRecurso_recnomContaining(codigo.getAbrcod(), nome.getRecnom())
				.orElse(null);
	}

	public Abrangencia findByIdEntity(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		return abrangenciaRepository.findById(codigo)
				.orElseThrow(() -> new EntityNotFoundException("Abrangência não encontrada com o código: " + codigo));
	}

	public Abrangencia findByIdEntity(String nome) {
		return abrangenciaRepository.findByAbrnomLike(nome)
				.orElse(null);
	}

	public AbrangenciaDTO findByIdSimples(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		Abrangencia abrangencia = findByIdEntity(codigo);
		return new AbrangenciaDTO(abrangencia);
	}

	public Abrangencia createUpdateAbrangencia(@NonNull Abrangencia abrangencia) {
		return abrangenciaRepository.save(abrangencia);
	}

	public AbrangenciaDetalhes createDetalhesAbrangencia(@NonNull AbrangenciaDetalhes detalhes) {
		return abrangenciaDetalhesRepository.save(detalhes);
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
	public ResponseEntity<Page<AbrangenciaListaDetalhesDTO>> findAll(String nome, Pageable pageable)
			throws EntityNotFoundException, IOException {
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
		long codigo = 0;
		Objects.requireNonNull(abrangenciaModel.getNome(), "Nome da Abrangência está nulo.");
		try {
			Abrangencia abrangencia = createUpdateAbrangencia(
					new Abrangencia(null, abrangenciaModel.getNome(), abrangenciaModel.getDescricao()));
			codigo = abrangencia.getAbrcod();

			List<AbrangenciaDetalhesDTO> abrangenciaDetalhesDTOList = abrangenciaModel.getRecursos() == null ? null
					: abrangenciaModel.getRecursos().stream().map(recurso -> createAbrangenciaDetalhes(abrangencia, recurso))
							.collect(Collectors.toList());

			return MessageResponse.create(new AbrangenciaListaDetalhesDTO(abrangencia, abrangenciaDetalhesDTOList));
		} catch (Exception e) {
			log.error("Erro ao criar a Abrangência e seus detalhes: ", e);
			abrangenciaRepository.deleteById(codigo);
			throw new IOException(e);
		}
	}

	@Override
	public ResponseEntity<AbrangenciaListaDetalhesDTO> update(Long codigo, AbrangenciaModel abrangenciaModel) {
		Objects.requireNonNull(codigo, "Código da Abrangência está nulo.");
		Objects.requireNonNull(abrangenciaModel.getNome(), "Nome da Abrangência está nulo.");

		Abrangencia abrangencia = createUpdateAbrangencia(
				new Abrangencia(codigo, abrangenciaModel.getNome(), abrangenciaModel.getDescricao()));

		List<AbrangenciaDetalhesDTO> abrangenciaDetalhesDTOList = abrangenciaModel.getRecursos() == null ? null
				: abrangenciaModel.getRecursos().stream().map(recurso -> updateAbrangenciaDetalhes(abrangencia, recurso))
						.collect(Collectors.toList());

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
	public ResponseEntity<AbrangenciaListaDetalhesDTO> findById(@NonNull Long codigo)
			throws EntityNotFoundException, IOException {
		Abrangencia abrangencia = findByIdEntity(codigo);

		List<AbrangenciaDetalhesDTO> detailsDTOList = abrangenciaDetalhesRepository
				.findByAbrangencia_Abrcod(abrangencia.getAbrcod()).stream().map(AbrangenciaDetalhesDTO::new)
				.collect(Collectors.toList());

		return MessageResponse
				.success(new AbrangenciaListaDetalhesDTO(abrangencia, detailsDTOList.isEmpty() ? null : detailsDTOList));
	}

	@Override
	public ResponseEntity<AbrangenciaListaDetalhesDTO> delete(@NonNull Long codigo) throws IOException {
		try {
			Abrangencia entityAbrangencia = findByIdEntity(codigo);

			var listAbrangenciaDetalhes = abrangenciaDetalhesRepository
					.findByAbrangencia_Abrcod(entityAbrangencia.getAbrcod());

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
		} catch (Exception e) {
			log.error("Erro ao apagar a Abrangência. Mensagem: " + e.getMessage(), e);
			throw new IOException("Erro ao apagar a Abrangência. Mensagem: " + e.getMessage(), e);
		}
	}

	private AbrangenciaDetalhesDTO createAbrangenciaDetalhes(Abrangencia abrangencia, AbrangenciaDetalhesModel recurso) {
		String recursoNome = Objects.requireNonNull(recurso.getRecurso().getNome(), "Nome do recurso está nulo.");
		Objects.requireNonNull(recurso.getHierarquia(), "Valor da Hierarquia está nulo.");

		Recurso recursoEntity = recursoService.findByIdEntity(recursoNome);
		JsonNodeConverter jsonNode = new JsonNodeConverter();
		AbrangenciaDetalhes detalhes = new AbrangenciaDetalhes(null, abrangencia, recursoEntity, recurso.getHierarquia(),
				jsonNode.convertToDatabaseColumn(recurso.getDados()));
		return new AbrangenciaDetalhesDTO(abrangenciaDetalhesRepository.save(detalhes));
	}

	private AbrangenciaDetalhesDTO updateAbrangenciaDetalhes(Abrangencia abrangencia, AbrangenciaDetalhesModel recurso) {
		String recursoNome = Objects.requireNonNull(recurso.getRecurso().getNome(), "Nome do recurso está nulo.");
		Objects.requireNonNull(recurso.getHierarquia(), "Valor da Hierarquia está nulo.");

		Recurso recursoEntity = recursoService.findByIdEntity(recursoNome);
		var detalhesOpt = abrangenciaDetalhesRepository
				.findByAbrangencia_abrcodAndRecurso_recnomContaining(abrangencia.getAbrcod(), recursoEntity.getRecnom());

		JsonNodeConverter jsonNode = new JsonNodeConverter();
		AbrangenciaDetalhes detalhes = new AbrangenciaDetalhes(detalhesOpt.map(AbrangenciaDetalhes::getAbdcod).orElse(null),
				abrangencia, recursoEntity, recurso.getHierarquia(), jsonNode.convertToDatabaseColumn(recurso.getDados()));
		return new AbrangenciaDetalhesDTO(abrangenciaDetalhesRepository.save(detalhes));
	}

}
