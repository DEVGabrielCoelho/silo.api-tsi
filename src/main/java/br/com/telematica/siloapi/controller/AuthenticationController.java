package br.com.telematica.siloapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.telematica.siloapi.model.AuthModel;
import br.com.telematica.siloapi.model.dto.ResponseAuthDTO;
import br.com.telematica.siloapi.model.dto.TokenValidationResponseDTO;
import br.com.telematica.siloapi.services.AuthServInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/autenticacao")
@Tag(name = "Autenticação", description = "Api para Controle de Autenticação")
public class AuthenticationController {

	@Autowired
	private AuthServInterface userServImpl;

	@PostMapping("/v1/auth")
	@Operation(description = "Realizar autenticação para obter token de acesso.")
	public ResponseEntity<ResponseAuthDTO> postAuth(@Valid @RequestBody @NonNull AuthModel auth) throws Exception {
		var login = userServImpl.authLogin(auth);
		return ResponseEntity.ok(login);
	}

	@GetMapping("/v1/validate")
	@Operation(description = "Verificar validade do token.")
	public ResponseEntity<TokenValidationResponseDTO> validateToken(@RequestParam("token") @NonNull String token) {
		try {
			return ResponseEntity.ok(userServImpl.validateAndParseToken(token));
		} catch (JWTVerificationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenValidationResponseDTO(false, 0L, "Unauthorized token."));
		}
	}

	@GetMapping("/v1/refresh")
	@Operation(description = "Verificar validade do token, se extiver expirado gera um novo token.")
	public ResponseEntity<ResponseAuthDTO> refreshToken(@RequestParam("token") String token) {
		return ResponseEntity.ok(userServImpl.refreshToken(token));

	}

}
