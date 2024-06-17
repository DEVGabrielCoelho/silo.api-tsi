package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

import br.com.telematica.siloapi.model.AuthModel;
import br.com.telematica.siloapi.model.dto.PerfilDTO;
import br.com.telematica.siloapi.model.dto.ResponseAuthDTO;
import br.com.telematica.siloapi.model.dto.TokenValidationResponseDTO;
import br.com.telematica.siloapi.model.entity.Usuario;
import br.com.telematica.siloapi.records.GenerateTokenRecords;
import br.com.telematica.siloapi.services.AuthServiceInterface;
import br.com.telematica.siloapi.utils.JWTUtil;

@Service
public class AuthServiceImpl implements AuthServiceInterface {

	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UsuarioServiceImpl userService;

	@Autowired
	@Lazy
	protected AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = userService.findLoginEntity(username);
		if (usuario == null)
			log.info("Usuário não encontrado: " + username);
//			throw new UsernameNotFoundException("Usuário não encontrado: " + username);
		return (UserDetails) new Usuario(usuario);

	}

	@Override
	public GenerateTokenRecords getToken(AuthModel authToken) throws IOException {
		Objects.requireNonNull(authToken.getLogin(), "Login está nulo.");
		Objects.requireNonNull(authToken.getSenha(), "Senha está nulo.");
		Usuario user = userService.findLoginEntity(authToken.getLogin());
		return jwtUtil.generateToken(user);
	}

	@Override
	public String validToken(String token) {
		try {
			return jwtUtil.validateToken(token);
		} catch (TokenExpiredException e) {
			throw new TokenExpiredException(e.getMessage(), null);
		} catch (JWTVerificationException | JWTCreationException e) {
			throw new AccessDeniedException(e.getMessage());
		}
	}

	@Override
	public Instant validateTimeToken(String token) {
		try {
			return jwtUtil.getExpirationDateFromToken(token);
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(e.getMessage());
		}
	}

	@Override
	public ResponseAuthDTO refreshToken(String token) {
		try {
			var refresh = jwtUtil.validateOrRefreshToken(token);
			Usuario userCheck = userService.findLoginEntity(refresh.username());
			return new ResponseAuthDTO(refresh.token(), refresh.date(), refresh.expiryIn(), userCheck.getUsucod(), new PerfilDTO(userCheck.getPerfil()));
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException(e.getMessage());
		}
	}

	@Override
	public ResponseAuthDTO authLogin(@NonNull AuthModel authReq) throws Exception {
		Usuario userCheck = userService.findLoginEntity(authReq.getLogin());

		try {
			var userAutheticationToken = new UsernamePasswordAuthenticationToken(authReq.getLogin(), authReq.getSenha());
			authenticationManager.authenticate(userAutheticationToken);

			GenerateTokenRecords tokenGenerate = getToken(authReq);
			return new ResponseAuthDTO(tokenGenerate.token(), tokenGenerate.date(), tokenGenerate.expiryIn(), userCheck.getUsucod(), new PerfilDTO(userCheck.getPerfil()));
		} catch (AuthenticationException e) {
			throw new RuntimeException("Erro na autenticação: " + e.getMessage());
		}
	}

	@Override
	public TokenValidationResponseDTO validateAndParseToken(@NonNull String token) {
		Objects.requireNonNull(token, "Token está nulo.");
		String username = validToken(token);
		if (username == null) {
			throw new JWTVerificationException("Invalid token");
		}

		Instant expiration = validateTimeToken(token);
		long timeToExpiry = Duration.between(Instant.now(), expiration).toMillis();

		LocalDateTime expirationLocalDateTime = expiration.atZone(ZoneId.systemDefault()).toLocalDateTime();

		return new TokenValidationResponseDTO(true, timeToExpiry, expirationLocalDateTime.toString());

	}

}
