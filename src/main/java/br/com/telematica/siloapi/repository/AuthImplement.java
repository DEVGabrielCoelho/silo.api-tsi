package br.com.telematica.siloapi.repository;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.telematica.siloapi.model.AuthModel;

public interface AuthImplement extends UserDetailsService {
	public String generateToken(AuthModel authToken) throws IOException;

	public String validateToken(String token);

}
