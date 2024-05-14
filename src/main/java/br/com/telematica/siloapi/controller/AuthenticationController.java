package br.com.telematica.siloapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.AuthModel;
import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.services.UsuarioInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/autenticacao")
@Tag(name = "Autenticação", description = "Api para Controle de Autenticação")
public class AuthenticationController {

	@Autowired
	private UsuarioInterface user;

	@PostMapping("/v1/auth")
	public ResponseEntity<GenericResponseModel> postAuth(@Valid @RequestBody AuthModel auth) throws Exception {
		return user.authLogin(auth);
	}

}
