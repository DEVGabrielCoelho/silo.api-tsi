package br.com.telematica.siloapi.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.telematica.siloapi.config.ConfigProperties;
import br.com.telematica.siloapi.model.dto.AuthenticationDTO;
import br.com.telematica.siloapi.model.entity.UsuarioEntity;
import br.com.telematica.siloapi.repository.AuthenticationRepository;
import br.com.telematica.siloapi.repository.UsuarioRepository;

@Service
public class AuthService implements AuthenticationRepository {

	@Autowired
	private ConfigProperties confProp;
	@Autowired
	private UsuarioRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsulog(username);
	}

	@Override
	public String getToken(AuthenticationDTO authToken) {
		UsuarioEntity user = userRepository.findByUsulog(authToken.getLogin());
		return generateToken(user);
	}

	@Override
	public String validToken(String token) {
		try {
			return validateToken(token);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unimplemented method 'validToken'");
		}
	}

	public String generateToken(UsuarioEntity user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256("my-secret");
			String token = JWT.create().withIssuer("auth-api").withSubject(user.getUsulog()).withExpiresAt(genExpirationDate()).sign(algorithm);
			return token;
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while generating token", exception);
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256("my-secret");
			return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();
		} catch (JWTVerificationException exception) {
			return "";
		}
	}

	private Instant genExpirationDate() {
		return LocalDateTime.now().plusMinutes(confProp.AUTH_TIME_MIN()).toInstant(ZoneOffset.of("-03:00"));
	}

}
