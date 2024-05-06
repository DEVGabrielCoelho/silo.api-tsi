package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

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

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.UsuarioModel;
import br.com.telematica.siloapi.model.dto.UsuarioDTO;
import br.com.telematica.siloapi.model.dto.UsuarioDetailsDTO;
import br.com.telematica.siloapi.service.UsuarioServiceImpl;
import br.com.telematica.siloapi.utils.Utils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/usuario/v1")
@Tag(name = "Usuário", description = "Api para Controle de Usuários")
public class UsuarioController extends SecurityRestController {

	@Autowired
	private UsuarioServiceImpl userServImpl;

	@PostMapping
	public ResponseEntity<GenericResponseModel> cadastrarUsuario(@RequestBody Optional<UsuarioModel> cadastro) {
		return userServImpl.saveUserEncodePassword(cadastro);
	}

	@GetMapping("{codigo}")
	public ResponseEntity<UsuarioDTO> buscarUsuarioPorCodigo(@PathVariable Long codigo) throws ParseException {
		var userList = userServImpl.findById(codigo);
		return ResponseEntity.ok(userList);
	}

	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> buscarListarUsuario() throws ParseException {
		var userList = userServImpl.findAll();
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/permissao")
	public ResponseEntity<List<UsuarioDetailsDTO>> buscarListarPermissao() throws ParseException {
		var userList = userServImpl.findUserPermiAll();
		return ResponseEntity.ok(userList);
	}

	@GetMapping("/permissao/{codigo}")
	public ResponseEntity<UsuarioDetailsDTO> buscarIdUsuarioPermissao(@PathVariable Long codigo) throws ParseException {
		var userList = userServImpl.findUserPermiById(codigo);
		return ResponseEntity.ok(userList);
	}

	@GetMapping("paginado")
	public ResponseEntity<Page<UsuarioDTO>> buscarUsuarioPaginado(@RequestParam(value = "pagina", defaultValue = "0") @NonNull Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") @NonNull Integer tamanho, @RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "ordenarPor", defaultValue = "codigo") String ordenarPor, @RequestParam(value = "direcao", defaultValue = "ASC", required = false) @NonNull String direcao) {
		String ordenarEntity = UsuarioDTO.consultaPagable(ordenarPor);
		if (ordenarEntity == null) {
			return ResponseEntity.badRequest().body(Page.empty());
		}
		return ResponseEntity.ok(userServImpl.usuarioFindAllPaginado(nome, Utils.consultaPage(ordenarEntity, direcao, pagina, tamanho)));
	}

	@PutMapping("/{codigo}")
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@Valid @PathVariable Long codigo, @Valid @RequestBody UsuarioModel entity) throws ParseException {
		var userService = userServImpl.update(codigo, Optional.ofNullable(entity));
		return ResponseEntity.ok(userService);
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<GenericResponseModel> deletarUsuario(@Valid @PathVariable Long codigo) throws IOException {
		return userServImpl.deleteByCode(codigo);
	}

}
