package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.AuthModel;
import br.com.telematica.siloapi.model.UsuarioModel;
import br.com.telematica.siloapi.model.dto.ResponseAuthenticationDTO;
import br.com.telematica.siloapi.repository.AuthRepository;
import br.com.telematica.siloapi.service.UsuarioServiceImpl;
import br.com.telematica.siloapi.utils.error.GenericResponseModel;
import br.com.telematica.siloapi.utils.error.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/management")
@Tag(name = "Management", description = "Management Controller")
@ApiResponses(value = { @ApiResponse(responseCode = "400", description = "Dados inválidos", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) }),
		@ApiResponse(responseCode = "403", description = "Não Autorizado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) }),
		@ApiResponse(responseCode = "404", description = "Não Encontrado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) }),
		@ApiResponse(responseCode = "500", description = "Erro no servidor", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) }) })
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AuthRepository authService;
	@Autowired
	private UsuarioServiceImpl userServices;

	@PostMapping("/authenticate")
	@Operation(description = "User Authentication")
	public ResponseAuthenticationDTO postMethodName(@RequestBody AuthModel entity) throws IOException {

		var userAutheticationToken = new UsernamePasswordAuthenticationToken(entity.getLogin(), entity.getSenha());

		// Authenticate first!
		Authentication authentication = authenticationManager.authenticate(userAutheticationToken);

		// Now get user details and roles
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

		List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		String role = roles.get(0);

		return new ResponseAuthenticationDTO(authService.generateToken(entity), role, null);
	}

	@PostMapping("/register")
	@Operation(description = "Register a User"/* , security = { @SecurityRequirement(name = "bearerAuth") } */)
	public ResponseEntity<GenericResponseModel> postRegister(@Valid @RequestBody UsuarioModel entity) {
		try {
			var userService = userServices.saveUserEncodePassword(Optional.of(entity));

			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, userService), HttpStatus.OK);

		} catch (RuntimeException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listUsers")
//	@Operation(description = "List registered users", security = { @SecurityRequirement(name = "bearerAuth") })
	public ResponseEntity<GenericResponseModel> getListUsers() {
		try {
			var userList = userServices.findUserPermiAll();

			return new ResponseEntity<GenericResponseModel>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, userList), HttpStatus.OK);
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return new ResponseEntity<GenericResponseModel>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateUser")
//	@Operation(description = "Register a User", security = { @SecurityRequirement(name = "bearerAuth") })
	public ResponseEntity<GenericResponseModel> updateUser(@Valid @PathVariable Long codigo, @Valid @RequestBody UsuarioModel entity) {
		try {

			var userService = userServices.update(codigo, Optional.ofNullable(entity));

			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro atualizado com Sucesso", null, userService), HttpStatus.OK);

		} catch (RuntimeException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteUser/{code}")
//	@Operation(description = "Register a User", security = { @SecurityRequirement(name = "bearerAuth") })
	public ResponseEntity<GenericResponseModel> deleteUser(@Valid @PathVariable Long code) {
		try {

			return userServices.deleteByCode(code);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
