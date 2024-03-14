package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.RegistryDTO;
import br.com.telematica.siloapi.model.entity.UsuarioEntity;
import br.com.telematica.siloapi.model.enums.RoleColectionEnum;
import br.com.telematica.siloapi.repository.UsuarioRepository;
import ch.qos.logback.classic.Logger;

@Service
public class UsuarioServices {

	private static Logger logger = (Logger) LoggerFactory.getLogger(TipoSiloService.class);

	@Autowired
	private UsuarioRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public RegistryDTO saveUserEncodePassword(UsuarioEntity user) throws RuntimeException {

		var userEntity = userRepository.findByUsulog(user.getUsulog());
		if (userEntity != null) {
			logger.error("Usuário já existe!");
			throw new RuntimeException("Usuário já existe!");
		}

		user.setUsusen(this.passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		RoleColectionEnum role = user.getUsurol();

		return new RegistryDTO(Long.valueOf(user.getUsucod()) ,user.getUsulog(), user.getUsusen(), user.getUsunom(), user.getUsuema(), role);
	}

	public boolean deleteByCode(Integer id) throws IOException { 
		if (id == null) {
			logger.error("Id está nulo.");
			throw new IOException("Id está nulo.");
		}
		try {
			userRepository.deleteByUsucod(id);
			return true;
		} catch (Exception e) {
			logger.error("Error deleting user: ", e);
			return false; 
		}
	}
	

	public UsuarioEntity update(UsuarioEntity usuario) throws IOException {
		if (usuario == null) {
			logger.error("Usuário está nulo.");
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

	public UsuarioEntity findById(Integer id) throws IOException, EmptyResultDataAccessException {
		if (id == null) {
			logger.error("Id está nulo.");
			throw new IOException("Id está nulo.");
		}
		return userRepository.findById(id).orElse(null);
	}

	private RegistryDTO convertToRegistryDTO(UsuarioEntity userEntity) {
		return new RegistryDTO(userEntity);
	}
}
