package br.com.telematica.siloapi.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.dto.AuthenticationDTO;
import br.com.telematica.siloapi.model.dto.RegistryDTO;
import br.com.telematica.siloapi.model.dto.ResponseAuthenticationDTO;
import br.com.telematica.siloapi.model.entity.UsuarioEntity;
import br.com.telematica.siloapi.repository.AuthenticationRepository;
import br.com.telematica.siloapi.service.UsuarioServices;
import br.com.telematica.siloapi.utils.error.GenericResponseModel;
import br.com.telematica.siloapi.utils.error.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/management")
@Tag(name = "Management", description = "Management Controller")
@ApiResponse(responseCode = "200", description = "Ok", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) })
@ApiResponse(responseCode = "400", description = "Dados inválidos", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) })
@ApiResponse(responseCode = "403", description = "Não Autorizado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) })
@ApiResponse(responseCode = "404", description = "Não Encontrado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) })
@ApiResponse(responseCode = "500", description = "Erro no servidor", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) })
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AuthenticationRepository authService;
	@Autowired
	private UsuarioServices userServices;

	@PostMapping("/authenticate")
	@Operation(description = "User Authentication")
	public ResponseAuthenticationDTO postMethodName(@RequestBody AuthenticationDTO entity) {

		var userAutheticationToken = new UsernamePasswordAuthenticationToken(entity.getLogin(), entity.getPassword());

		String role = userAutheticationToken.getAuthorities().toString();

		authenticationManager.authenticate(userAutheticationToken);

		return new ResponseAuthenticationDTO(authService.getToken(entity), role, new Date());
	}

	@PostMapping("/register")
	@Operation(description = "Register a User"/* , security = { @SecurityRequirement(name = "bearerAuth") } */)
	public ResponseEntity<GenericResponseModel> postRegister(@Valid @RequestBody RegistryDTO entity) {
		try {
			UsuarioEntity user = new UsuarioEntity(entity.getUser(), entity.getPassword(), entity.getName(), entity.getEmail(), entity.getRole());

			var userService = userServices.saveUserEncodePassword(user);

			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, userService), HttpStatus.OK);
		
		} catch (RuntimeException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listUsers")
	@Operation(description = "List registered users", security = { @SecurityRequirement(name = "bearerAuth") })
	public ResponseEntity<GenericResponseModel> getListUsers() {
		try {
			var userList = userServices.findAllRegistryDTO();

			return new ResponseEntity<GenericResponseModel>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, userList), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error: " + e.getMessage());
			return new ResponseEntity<GenericResponseModel>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateUser")
	@Operation(description = "Register a User"/* , security = { @SecurityRequirement(name = "bearerAuth") } */)
	public ResponseEntity<GenericResponseModel> updateUser(@Valid @RequestBody RegistryDTO entity) {
		try {
			UsuarioEntity user = new UsuarioEntity(entity.getUser(), entity.getPassword(), entity.getName(), entity.getEmail(), entity.getRole());

			var userService = userServices.update(user);

			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro atualizado com Sucesso", null, userService), HttpStatus.OK);
		
		} catch (RuntimeException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/deleteUser/{code}")
	@Operation(description = "Register a User"/* , security = { @SecurityRequirement(name = "bearerAuth") } */)
	public ResponseEntity<GenericResponseModel> deleteUser(@Valid @PathVariable Integer code) {
		try {

			var userService = userServices.deleteByCode(code);

			if ( userService == true)
				return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, userService), HttpStatus.OK);
			else 
				return new ResponseEntity<>(MessageResponse.exceptionRequest400("Erro ao deletar esse Usuário", null, userService), HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
