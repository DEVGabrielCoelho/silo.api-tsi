package br.com.telematica.siloapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.AuthModel;
import br.com.telematica.siloapi.model.dto.ResponseAuthDTO;
import br.com.telematica.siloapi.service.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/autenticacao/v1")
@Tag(name = "Autenticação", description = "Api para Controle de Autenticação")
public class AuthenticationController {

	@Autowired
	private UsuarioServiceImpl userServImpl;

	@PostMapping("/auth")
	public ResponseEntity<ResponseAuthDTO> postAuth(@Valid @RequestBody AuthModel auth) throws Exception {
		var login = userServImpl.authLogin(auth);
		return ResponseEntity.ok(login);
	}

}
