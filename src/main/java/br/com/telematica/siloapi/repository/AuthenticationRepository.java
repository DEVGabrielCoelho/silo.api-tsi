package br.com.telematica.siloapi.repository;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.telematica.siloapi.model.dto.AuthenticationDTO;

public interface AuthenticationRepository extends UserDetailsService {
	public String getToken(AuthenticationDTO authToken);

	public String validToken(String token);
}
