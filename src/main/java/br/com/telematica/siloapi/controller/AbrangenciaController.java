package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.AbrangenciaModel;
import br.com.telematica.siloapi.model.dto.AbrangenciaListaDetalhesDTO;
import br.com.telematica.siloapi.records.ItensAbrangentes;
import br.com.telematica.siloapi.services.AbrangenciaServInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/abrangencia")
@Tag(name = "Abrangencia", description = "Api para consulta de Abrangencia")
public class AbrangenciaController extends SecurityRestController {

	@Autowired
	private AbrangenciaServInterface abrangenciaServImpl;

	@PostMapping("/v1")
	@Operation(description = "Cadastrar uma Abrangencia.")
	public ResponseEntity<AbrangenciaListaDetalhesDTO> cadastrarAbrangencia(@RequestBody AbrangenciaModel cadastro) throws IOException {
		var abrangenciaService = abrangenciaServImpl.save(cadastro);
		return abrangenciaService;
	}

	@GetMapping("/v1/{codigo}")
	@Operation(description = "Buscar uma abrangencia pelo código cadastrado.")
	public ResponseEntity<AbrangenciaListaDetalhesDTO> buscarAbrangenciaPorCodigo(@PathVariable @NonNull Long codigo) throws EntityNotFoundException, IOException {
		var abrangenciaList = abrangenciaServImpl.findById(codigo);
		return abrangenciaList;
	}

	@GetMapping("/v1")
	@Operation(description = "Buscar lista de abrangencias cadastradas")
	public ResponseEntity<List<AbrangenciaListaDetalhesDTO>> buscarListarAbrangencia() throws EntityNotFoundException, IOException {
		var abrangenciaList = abrangenciaServImpl.findAll();
		return abrangenciaList;
	}

	@GetMapping("/v1/lista-items-abrangentes")
	@Operation(description = "Buscar lista com todos os itens abrangentes por recurso.")
	public ResponseEntity<ItensAbrangentes> buscarListarItemsAbrangentes() throws EntityNotFoundException, IOException {
		var abrangenciaList = abrangenciaServImpl.findByItemAbrangence();
		return abrangenciaList;
	}

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca pagina de abrangencias cadastradas. Pagina define qual pagina deseja abrir, Tamanho define a quantidade de itens por pagina, Filtro é um campo para realizar pesquisa pelo código, nome ou descrição.")
	public ResponseEntity<Page<AbrangenciaListaDetalhesDTO>> buscarAbrangenciaPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho, @RequestParam(value = "filtro", required = false) String filtro)
			throws EntityNotFoundException, IOException {
		return abrangenciaServImpl.findAll(filtro, PageRequest.of(pagina, tamanho));
	}

	@PutMapping("/v1/{codigo}")
	@Operation(description = "Atualizar uma abrangencia de acordo com o código já cadastrado e os dados que deseja alterar.")
	public ResponseEntity<AbrangenciaListaDetalhesDTO> atualizarAbrangencia(@Valid @PathVariable Long codigo, @Valid @RequestBody AbrangenciaModel entity) throws ParseException {
		var abrangenciaService = abrangenciaServImpl.update(codigo, entity);
		return abrangenciaService;
	}

	@DeleteMapping("/v1/{codigo}")
	@Operation(description = "Deletar uma abrangenca pelo código cadastrado.")
	public ResponseEntity<AbrangenciaListaDetalhesDTO> deletarAbrangencia(@Valid @PathVariable @NonNull Long codigo) throws IOException {
		var abrangenciaService = abrangenciaServImpl.delete(codigo);
		return abrangenciaService;
	}

}
