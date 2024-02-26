package br.com.telematica.ciloapi.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.telematica.ciloapi.model.dto.RegistryDTO;
import br.com.telematica.ciloapi.model.enttity.UserEntity;
import br.com.telematica.ciloapi.model.enums.RoleEnum;
import br.com.telematica.ciloapi.repository.UserRepository;

@Service
public class UserServices {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public RegistryDTO saveUserEncodePassword(UserEntity user) {

		var userEntity = userRepository.findByUsulog(user.getUsulog());
		if (userEntity != null) {
			throw new RuntimeException("Usuário já existe!");
		}

		user.setUsusen(this.passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		RoleEnum role = RoleEnum.valueOf(user.getUsurol());

		return new RegistryDTO(user.getUsulog(), user.getUsusen(), user.getUsunom(), user.getUsuema(), role);
	}

	public void deleteById(BigInteger id) throws IOException {
		if (id == null) {
			throw new IOException("Id está nulo.");
		}
		userRepository.deleteById(id);
	}

	public UserEntity update(UserEntity usuario) throws IOException {
		if (usuario == null) {
			throw new RuntimeException("Usuário está nulo.");
		}
		return userRepository.save(usuario);
	}

	public List<UserEntity> findAll() throws IOException {
		return userRepository.findAll();
	}

	public List<RegistryDTO> findAllRegistryDTO() throws IOException {
		return userRepository.findAll().stream().map(this::convertToRegistryDTO).collect(Collectors.toList());
	}

	public UserEntity findLogin(String login) throws IOException {
		return userRepository.findByUsulog(login);
	}

	public UserEntity findById(BigInteger id) throws IOException, EmptyResultDataAccessException {
		if (id == null) {
			throw new IOException("Id está nulo.");
		}
		return userRepository.findById(id).orElse(null);
	}

	private RegistryDTO convertToRegistryDTO(UserEntity userEntity) {
		return new RegistryDTO(userEntity);
	}
}
