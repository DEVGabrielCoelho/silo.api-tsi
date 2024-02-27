package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.RegistryDTO;
import br.com.telematica.siloapi.model.enttity.UsuarioEntity;
import br.com.telematica.siloapi.model.enums.RoleColectionEnum;
import br.com.telematica.siloapi.repository.UsuarioRepository;

@Service
public class UsuarioServices {

	@Autowired
	private UsuarioRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public RegistryDTO saveUserEncodePassword(UsuarioEntity user) {

		var userEntity = userRepository.findByUsulog(user.getUsulog());
		if (userEntity != null) {
			throw new RuntimeException("Usuário já existe!");
		}

		user.setUsusen(this.passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		RoleColectionEnum role = user.getUsurol();

		return new RegistryDTO(user.getUsulog(), user.getUsusen(), user.getUsunom(), user.getUsuema(), role);
	}

	public void deleteById(BigInteger id) throws IOException {
		if (id == null) {
			throw new IOException("Id está nulo.");
		}
		userRepository.deleteById(id);
	}

	public UsuarioEntity update(UsuarioEntity usuario) throws IOException {
		if (usuario == null) {
			throw new RuntimeException("Usuário está nulo.");
		}
		return userRepository.save(usuario);
	}

	public List<UsuarioEntity> findAll() throws IOException {
		return userRepository.findAll();
	}

	public List<RegistryDTO> findAllRegistryDTO() throws IOException {
		return userRepository.findAll().stream().map(this::convertToRegistryDTO).collect(Collectors.toList());
	}

	public UsuarioEntity findLogin(String login) throws IOException {
		return userRepository.findByUsulog(login);
	}

	public UsuarioEntity findById(BigInteger id) throws IOException, EmptyResultDataAccessException {
		if (id == null) {
			throw new IOException("Id está nulo.");
		}
		return userRepository.findById(id).orElse(null);
	}

	private RegistryDTO convertToRegistryDTO(UsuarioEntity userEntity) {
		return new RegistryDTO(userEntity);
	}
}
