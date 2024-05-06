package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.UsuarioModel;
import br.com.telematica.siloapi.model.dto.ResponseAuthenticationDTO;
import br.com.telematica.siloapi.repository.AuthRepository;
import br.com.telematica.siloapi.service.UsuarioServiceImpl;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/management")
@Tag(name = "Management", description = "Management Controller")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AuthRepository authService;
	@Autowired
	private UsuarioServiceImpl userServices;

	@PostMapping("/authenticate")
	@Operation(description = "User Authentication")
	public ResponseEntity<GenericResponseModel> postMethodName(@RequestBody AuthModel entity) throws IOException {

		var userAutheticationToken = new UsernamePasswordAuthenticationToken(entity.getLogin(), entity.getSenha());

		// Authenticate first!
		Authentication authentication = authenticationManager.authenticate(userAutheticationToken);

		// Now get user details and roles
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

		List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

		String role = roles.get(0);

		return MessageResponse.sucess(new ResponseAuthenticationDTO(authService.generateToken(entity), role, null));
	}

	@PostMapping("/register")
	@Operation(description = "Register a User"/* , security = { @SecurityRequirement(name = "bearerAuth") } */)
	public ResponseEntity<GenericResponseModel> postRegister(@Valid @RequestBody UsuarioModel entity) {
		try {
			var userService = userServices.saveUserEncodePassword(Optional.of(entity));

			return MessageResponse.sucess(userService);

		} catch (RuntimeException e) {
			return MessageResponse.badRequest(e.getMessage());
		} catch (Exception e) {
			return MessageResponse.notAutorize("Exceção gerada ao executar o registro. " + e.getMessage());
		}
	}

	@GetMapping("/listUsers")
//	@Operation(description = "List registered users", security = { @SecurityRequirement(name = "bearerAuth") })
	public ResponseEntity<GenericResponseModel> getListUsers() {
		try {
			var userList = userServices.findUserPermiAll();

			return MessageResponse.sucess(userList);
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	@PutMapping("/updateUser")
//	@Operation(description = "Register a User", security = { @SecurityRequirement(name = "bearerAuth") })
	public ResponseEntity<GenericResponseModel> updateUser(@Valid @PathVariable Long codigo, @Valid @RequestBody UsuarioModel entity) {
		try {

			var userService = userServices.update(codigo, Optional.ofNullable(entity));

			return MessageResponse.sucess(userService);

		} catch (RuntimeException e) {
			return MessageResponse.badRequest(e.getMessage());
		} catch (Exception e) {
			return MessageResponse.notAutorize("Exceção gerada ao executar o registro. " + e.getCause());
		}
	}

	@DeleteMapping("/deleteUser/{code}")
//	@Operation(description = "Register a User", security = { @SecurityRequirement(name = "bearerAuth") })
	public ResponseEntity<GenericResponseModel> deleteUser(@Valid @PathVariable Long code) {
		try {

			return userServices.deleteByCode(code);
		} catch (RuntimeException e) {
			return MessageResponse.badRequest(e.getMessage());
		} catch (Exception e) {
			return MessageResponse.notAutorize("Exceção gerada ao executar o registro. " + e.getCause());
		}
	}
}
