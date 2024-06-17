package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.UsuarioModel;
import br.com.telematica.siloapi.model.dto.UsuarioDTO;
import br.com.telematica.siloapi.model.dto.UsuarioPermissaoDTO;
import br.com.telematica.siloapi.services.UsuarioServiceInterface;
import br.com.telematica.siloapi.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuário", description = "Api para Controle de Usuários")
public class UsuarioController extends SecurityRestController {

	@Autowired
	private UsuarioServiceInterface userServImpl;

	@PostMapping("/v1")
	@Operation(description = "Cadastrar um usuário")
	public ResponseEntity<UsuarioDTO> criar(@RequestBody @NonNull UsuarioModel cadastro) throws EntityNotFoundException, IOException {
		var userService = userServImpl.saveUpdateEncodePassword(cadastro);
		return ResponseEntity.ok(userService);
	}

	@GetMapping("/v1/codigo/{codigo}")
	@Operation(description = "Buscar um usuario pelo código")
	public ResponseEntity<UsuarioDTO> buscarPorCodigo(@PathVariable @NonNull Long codigo) throws EntityNotFoundException, IOException {
		var userList = userServImpl.findById(codigo);
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/v1/permissao/{codigo}")
	@Operation(description = "Buscar um usuário e suas permissões apartir do código do usuário.")
	public ResponseEntity<UsuarioPermissaoDTO> buscarPorCodigoPermissaoUsuario(@PathVariable @NonNull Long codigo) throws EntityNotFoundException, IOException {
		var userList = userServImpl.findByIdPermission(codigo);
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/v1")
	@Operation(description = "Buscar lista de usuário")
	public ResponseEntity<List<UsuarioDTO>> listar() throws EntityNotFoundException, IOException {
		var userList = userServImpl.findAll();
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca de usuário paginado. Obs: O campo 'ordenarPor' requer os seguintes dados: codigo, nome, cpf, login, senha, email. Consulta por filtro (sem restrição de consulta).")
	public ResponseEntity<Page<UsuarioDTO>> buscarPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho, @RequestParam(value = "filtro", required = false) String filtro,
			@RequestParam(value = "ordenarPor", defaultValue = "codigo") String ordenarPor, @RequestParam(value = "direcao", defaultValue = "ASC", required = false) String direcao) throws EntityNotFoundException, IOException {
		String ordenarEntity = UsuarioDTO.consultaPagable(ordenarPor);
		if (ordenarEntity == null) {
			return ResponseEntity.badRequest().body(Page.empty());
		}
		return ResponseEntity.ok(userServImpl.findAll(filtro, Utils.consultaPage(ordenarEntity, direcao, pagina, tamanho)));
	}

	@PutMapping("/v1/{codigo}")
	@Operation(description = "Atualizar um usuário")
	public ResponseEntity<UsuarioDTO> editar(@Valid @PathVariable @NonNull Long codigo, @Valid @RequestBody @NonNull UsuarioModel entity) throws EntityNotFoundException, IOException {
		var userService = userServImpl.saveUpdateEncodePassword(codigo, entity);
		return ResponseEntity.ok(userService);
	}

	@DeleteMapping("/v1/{codigo}")
	@Operation(description = "Deletar um usuário")
	public ResponseEntity<ResponseGlobalModel> deletar(@Valid @PathVariable @NonNull Long codigo) throws IOException {
		var userService = userServImpl.delete(codigo);
		return ResponseEntity.ok(userService);
	}

}
