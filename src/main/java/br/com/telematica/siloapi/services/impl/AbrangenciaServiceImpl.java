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

	public Abrangencia createUpdateAbrangencia(@NonNull Abrangencia abrangencia) {
		return abrangenciaRepository.save(abrangencia);
	}

	public AbrangenciaDetalhes createDetalhesAbrangencia(@NonNull AbrangenciaDetalhes detalhes) {
		return abrangenciaDetalhesRepository.save(detalhes);
	}

	public ItensAbrangentes findByItemAbrangenceEntity() {

		var empresaList = empresaService.findByEmpresa();
		// add itens que seram abrangentes
		return new ItensAbrangentes(empresaList);
	}

	@Override
	public ResponseEntity<ItensAbrangentes> findByItemAbrangence() {
		return MessageResponse.success(findByItemAbrangenceEntity());
	}

	@Override
	public ResponseEntity<Page<AbrangenciaListaDetalhesDTO>> findAll(String nome, Pageable pageable) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(pageable, "Pageable do Abrangencia está nulo.");
		Specification<Abrangencia> spec = Specification.where(null);
		spec = spec.and(Abrangencia.filterByFields(nome));

		Page<Abrangencia> result = abrangenciaRepository.findAll(spec, pageable);

		return MessageResponse.success(result.map(abrangencia -> {
			var details = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(abrangencia.getAbrcod());
			var detailsDTOList = details.stream().map(map -> new AbrangenciaDetalhesDTO(map)).collect(Collectors.toList()); // Collect the stream into a list
			return new AbrangenciaListaDetalhesDTO(abrangencia, detailsDTOList); // Pass the collected list to the constructor
		}));
	}

	@Override
	public ResponseEntity<AbrangenciaListaDetalhesDTO> save(AbrangenciaModel abrangenciaModel) throws IOException {
		long codigo = 0;
		Objects.requireNonNull(abrangenciaModel.getNome(), "Nome da Abrangência está nulo.");
		try {
			Objects.requireNonNull(abrangenciaModel.getNome(), "Nome da Abrangência está nulo.");
			var abrangencia = createUpdateAbrangencia(new Abrangencia(null, abrangenciaModel.getNome().toString(), abrangenciaModel.getDescricao()));
			codigo = abrangencia.getAbrcod();

			var abrangenciaDetalhesDTOList = abrangenciaModel.getRecursos() == null ? null : abrangenciaModel.getRecursos().stream().map(recurso -> {
				String recursoString = recurso.getRecurso().getNome();
				Objects.requireNonNull(recursoString, "Nome do recurso está nulo.");
				Objects.requireNonNull(recurso.getHierarquia(), "Valor da Hierarquia está nulo.");

				var recursoEntity = recursoService.findByIdEntity(recursoString);
				JsonNodeConverter jsonNode = new JsonNodeConverter();
				var entity = new AbrangenciaDetalhes(null, abrangencia, recursoEntity, recurso.getHierarquia(), jsonNode.convertToDatabaseColumn(recurso.getDados()));
				var abrangenciaDetalhesSave = abrangenciaDetalhesRepository.save(entity);

				return new AbrangenciaDetalhesDTO(abrangenciaDetalhesSave);
			}).collect(Collectors.toList());

			return MessageResponse.create(new AbrangenciaListaDetalhesDTO(abrangencia, abrangenciaDetalhesDTOList));

		} catch (Exception e) {
			log.error("Erro ao criar a Abrangencia e Abrangencia_Detalhes: ", e);
			abrangenciaRepository.deleteById(codigo);
			throw new IOException(e);
		}
	}

	@Override
	public ResponseEntity<AbrangenciaListaDetalhesDTO> update(Long codigo, AbrangenciaModel abrangenciaModel) {
		Objects.requireNonNull(codigo, "Código do Abrangência está nulo.");
		Objects.requireNonNull(abrangenciaModel.getNome(), "Nome da Abrangência está nulo.");
		var abrangencia = createUpdateAbrangencia(new Abrangencia(codigo, abrangenciaModel.getNome().toString(), abrangenciaModel.getDescricao()));

		var abrangenciaDetalhesDTOList = abrangenciaModel.getRecursos() == null ? null : abrangenciaModel.getRecursos().stream().map(recurso -> {
			String recursoString = recurso.getRecurso().getNome();
			Objects.requireNonNull(recursoString, "Nome do recurso está nulo.");
			Objects.requireNonNull(recurso.getHierarquia(), "Valor da Hierarquia está nulo.");

			var recursoEntity = recursoService.findByIdEntity(recursoString);
			var abDetalhes = abrangenciaDetalhesRepository.findByAbrangencia_abrcodAndRecurso_recnomContaining(abrangencia.getAbrcod(), recursoEntity.getRecnom());
			JsonNodeConverter jsonNode = new JsonNodeConverter();
			var entity = new AbrangenciaDetalhes(abDetalhes.isEmpty() ? codigo : abDetalhes.get().getAbdcod(), abrangencia, recursoEntity, recurso.getHierarquia(), jsonNode.convertToDatabaseColumn(recurso.getDados()));
			var abrangenciaDetalhesSave = abrangenciaDetalhesRepository.save(entity);
			return new AbrangenciaDetalhesDTO(abrangenciaDetalhesSave);
		}).collect(Collectors.toList());

		if (abrangenciaDetalhesDTOList == null || abrangenciaDetalhesDTOList.isEmpty())
			abrangenciaDetalhesRepository.deleteById(codigo);

		return MessageResponse.create(new AbrangenciaListaDetalhesDTO(abrangencia, abrangenciaDetalhesDTOList));
	}

	@Override
	public ResponseEntity<List<AbrangenciaListaDetalhesDTO>> findAll() throws EntityNotFoundException, IOException {
		List<Abrangencia> abrangenciaList = abrangenciaRepository.findAll();

		return MessageResponse.success(abrangenciaList.stream().map(abrangencia -> {
			var details = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(abrangencia.getAbrcod());
			var detailsDTOList = details.stream().map(map -> new AbrangenciaDetalhesDTO(map)).collect(Collectors.toList());
			return new AbrangenciaListaDetalhesDTO(abrangencia, detailsDTOList);
		}).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<AbrangenciaListaDetalhesDTO> findById(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		Abrangencia abrangencia = findByIdEntity(codigo);
		if (abrangencia == null)
			return null;

		List<AbrangenciaDetalhesDTO> detailsDTOList = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(abrangencia.getAbrcod()).stream().map(map -> new AbrangenciaDetalhesDTO(map)).collect(Collectors.toList());
		return MessageResponse.success(new AbrangenciaListaDetalhesDTO(abrangencia, detailsDTOList.isEmpty() ? null : detailsDTOList));
	}

	@Override
	public ResponseEntity<AbrangenciaListaDetalhesDTO> delete(@NonNull Long codigo) throws IOException {
		try {
			var entityAbrangencia = findByIdEntity(codigo);

			var listAbrangenciaDetalhes = abrangenciaDetalhesRepository.findByAbrangencia_Abrcod(entityAbrangencia.getAbrcod());

			if (!listAbrangenciaDetalhes.isEmpty()) {
				listAbrangenciaDetalhes.forEach(map -> {
					Long abdcod = map.getAbdcod();
					if (abdcod != null) {
						abrangenciaDetalhesRepository.deleteById(abdcod);
					} else {
						log.warn("Detalhe da abrangência com ID nulo encontrado. Ignorando deleção.");
					}
				});

				abrangenciaRepository.deleteById(codigo);
			} else
				abrangenciaRepository.deleteById(codigo);

			return MessageResponse.success(null);
		} catch (Exception e) {
			throw new IOException("Erro ao apagar o item. Mensagem: " + e.getMessage(), e);
		}
	}

}
