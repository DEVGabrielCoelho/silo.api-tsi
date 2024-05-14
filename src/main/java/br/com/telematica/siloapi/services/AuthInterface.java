package br.com.telematica.siloapi.services;

import java.io.IOException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.telematica.siloapi.model.AuthModel;

public interface AuthInterface extends UserDetailsService {
	public String generateToken(AuthModel authToken) throws IOException;

	public String validateToken(String token);

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
