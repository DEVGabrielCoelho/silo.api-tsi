package br.com.telematica.siloapi.utils;

import java.time.Instant;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.telematica.siloapi.model.entity.UsuarioEntity;

@Component
public class JWTUtil {

	@Value("${api.security.token.secret}")
	private String secret;

	@Value("${api.security.expiration.time.hours}")
	private long expirationTime;

	public String generateToken(UsuarioEntity usuario) throws IllegalArgumentException {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create().withIssuer("auth-api").withSubject(usuario.getUsulog()).withExpiresAt(genExpirationDate()).sign(algorithm);
			return token;
		} catch (JWTCreationException | IllegalArgumentException exception) {
			throw new RuntimeException("Error while generating token", exception);
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("auth-api").build().verify(token).getSubject();
		} catch (JWTVerificationException exception) {
			System.out.println(exception);
			return null;
		}
	}

	private Instant genExpirationDate() {
		ZonedDateTime agoraLocal = ZonedDateTime.now();
		ZonedDateTime dataExpiracao = agoraLocal.plusHours(expirationTime);
		return dataExpiracao.toInstant();
	}

}
