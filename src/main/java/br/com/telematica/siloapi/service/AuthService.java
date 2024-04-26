package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.AuthModel;
import br.com.telematica.siloapi.model.entity.UsuarioEntity;
import br.com.telematica.siloapi.repository.AuthRepository;
import br.com.telematica.siloapi.repository.UsuarioRepository;
import br.com.telematica.siloapi.utils.JWTUtil;

@Service
public class AuthService implements AuthRepository {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UsuarioRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null)
			throw new UsernameNotFoundException("O nome do Usuário está vazio.");
		Optional<UsuarioEntity> usuario = userRepository.findByUsulog(username);
		if (usuario.isEmpty())
			throw new UsernameNotFoundException("Usuário não encontrado: " + username);
		return (UserDetails) new UsuarioEntity(usuario.get());

	}

	@Override
	public String generateToken(AuthModel authToken) throws IOException {
		String loginToken = authToken.getLogin();
		if (loginToken == null)
			throw new UsernameNotFoundException("O Token de autenticação está vazio.");
		UsuarioEntity user = userRepository.findByUsulog(loginToken).get();
		return jwtUtil.generateToken(user);
	}

	@Override
	public String validateToken(String token) {
		try {
			return jwtUtil.validateToken(token);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unimplemented method 'validToken'");
		}
	}

}
