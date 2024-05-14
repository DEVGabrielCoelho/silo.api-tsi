package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.text.ParseException;
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
import br.com.telematica.siloapi.services.UsuarioInterface;
import br.com.telematica.siloapi.utils.Utils;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuário", description = "Api para Controle de Usuários")
public class UsuarioController extends SecurityRestController {

	@Autowired
	private UsuarioInterface user;

	@PostMapping
	public ResponseEntity<GenericResponseModel> cadastrarUsuario(@RequestBody Optional<UsuarioModel> cadastro) {
		return user.saveUserEncodePassword(cadastro);
	}

	@GetMapping("/v1{codigo}")
	public ResponseEntity<GenericResponseModel> buscarUsuarioPorCodigo(@PathVariable Long codigo) throws ParseException {
		return user.findById(codigo);
	}

	@GetMapping("/v1")
	public ResponseEntity<GenericResponseModel> buscarListarUsuario() throws ParseException {
		return user.findAll();
	}

	@GetMapping("/v1/permissao")
	public ResponseEntity<GenericResponseModel> buscarListarPermissao() throws ParseException {
		return user.findUserPermiAll();
	}

	@GetMapping("/v1/permissao/{codigo}")
	public ResponseEntity<GenericResponseModel> buscarIdUsuarioPermissao(@PathVariable Long codigo) throws ParseException {
		return user.findUserPermiById(codigo);
	}

	@GetMapping("/v1/paginado")
	public ResponseEntity<Page<UsuarioDTO>> buscarUsuarioPaginado(@RequestParam(value = "pagina", defaultValue = "0") @NonNull Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") @NonNull Integer tamanho, @RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "ordenarPor", defaultValue = "codigo") String ordenarPor, @RequestParam(value = "direcao", defaultValue = "ASC", required = false) @NonNull String direcao) {
		String ordenarEntity = UsuarioDTO.consultaPagable(ordenarPor);
		if (ordenarEntity == null) {
			return ResponseEntity.badRequest().body(Page.empty());
		}
		return MessageResponse.page(user.usuarioFindAllPaginado(nome, Utils.consultaPage(ordenarEntity, direcao, pagina, tamanho)));

	}

	@PutMapping("/v1/{codigo}")
	public ResponseEntity<GenericResponseModel> atualizarUsuario(@Valid @PathVariable Long codigo, @Valid @RequestBody UsuarioModel entity) throws ParseException {
		return user.update(codigo, Optional.ofNullable(entity));
	}

	@DeleteMapping("/v1/{codigo}")
	public ResponseEntity<GenericResponseModel> deletarUsuario(@Valid @PathVariable Long codigo) throws IOException {
		return user.deleteByCode(codigo);
	}

}
