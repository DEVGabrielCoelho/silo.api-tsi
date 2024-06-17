package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
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

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.PerfilModel;
import br.com.telematica.siloapi.model.dto.PerfilPermissaoDTO;
import br.com.telematica.siloapi.services.PerfilPermissaoServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/perfil")
@Tag(name = "Perfil", description = "Api de registros de Perfil com Permissões.")
public class PerfilController extends SecurityRestController {

	@Autowired
	private PerfilPermissaoServiceInterface perfilServImpl;

	@PostMapping("/v1")
	@Operation(description = "Cadastrar um perfil de usuário com suas permissões de acesso.")
	public ResponseEntity<PerfilPermissaoDTO> cadastrarPerfil(@Validated @RequestBody PerfilModel cadastro) throws IOException {
		var perfilService = perfilServImpl.save(cadastro);
		return ResponseEntity.ok(perfilService);
	}

	@GetMapping("/v1/{codigo}")
	@Operation(description = "Buscar um perfil pelo código.")
	public ResponseEntity<PerfilPermissaoDTO> buscarPerfilPorCodigo(@PathVariable @NonNull Long codigo) throws EntityNotFoundException, IOException {
		var perfilList = perfilServImpl.findById(codigo);
		return ResponseEntity.ok(perfilList);
	}

	@GetMapping("/v1")
	@Operation(description = "Buscar lista de perfils de acesso cadastrados.")
	public ResponseEntity<List<PerfilPermissaoDTO>> buscarListarPerfil() throws EntityNotFoundException, IOException {
		var perfilList = perfilServImpl.findAll();
		return ResponseEntity.ok(perfilList);
	}

	@GetMapping("/v1/paginado")
	@Operation(description = "Buscar pagiado dos perfils de acesso. Pagina define a pagina que desseja acessar, Tamanho define a quantidade de itens por pagina, Filtro busca pelos campos codigo, nome e descrição, OrdenarPor requer os seguintes dados: codigo, login, senha, email.")
	public ResponseEntity<Page<PerfilPermissaoDTO>> buscarPerfilPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho, @RequestParam(value = "filtro", required = false) String filtro)
			throws EntityNotFoundException, IOException {
		return ResponseEntity.ok(perfilServImpl.findAll(filtro, PageRequest.of(pagina, tamanho)));
	}

	@PutMapping("/v1/{codigo}")
	@Operation(description = "Atualizar um perfil de acesso pelo código.")
	public ResponseEntity<PerfilPermissaoDTO> atualizarPerfil(@Valid @PathVariable @NonNull Long codigo, @Valid @RequestBody PerfilModel entity) throws EntityNotFoundException, IOException {
		var perfilService = perfilServImpl.update(codigo, entity);
		return ResponseEntity.ok(perfilService);
	}

	@DeleteMapping("/v1/{codigo}")
	@Operation(description = "Deletar um perfil de acesso.")
	public ResponseEntity<ResponseGlobalModel> deletarPerfil(@Valid @PathVariable @NonNull Long codigo) throws IOException {
		var perfilService = perfilServImpl.delete(codigo);
		return ResponseEntity.ok(perfilService);
	}

}
