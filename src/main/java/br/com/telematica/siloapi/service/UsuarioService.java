package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.PermissaoModel;
import br.com.telematica.siloapi.model.UsuarioModel;
import br.com.telematica.siloapi.model.dto.PermissaoDTO;
import br.com.telematica.siloapi.model.dto.Permissao_UsuarioDTO;
import br.com.telematica.siloapi.model.dto.UsuarioDTO;
import br.com.telematica.siloapi.model.dto.UsuarioDetailsDTO;
import br.com.telematica.siloapi.model.entity.PerfilEntity;
import br.com.telematica.siloapi.model.entity.UsuarioEntity;
import br.com.telematica.siloapi.model.enums.MapaURLEnum;
import br.com.telematica.siloapi.repository.UsuarioRepository;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	private static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	protected PerfilService perfilService;
	@Autowired
	@Lazy
	protected PermissaoService permissaoService;

	public void createUserRolePermission() {
		List<PermissaoModel> listPermi = new ArrayList<PermissaoModel>();

		List<String> listString = Arrays.asList("SIRENE", "MODULO", "PENDENCIA", "FIRMWARE", "MEDICAO", "AUDIO", "LOGGER", "EMPRESA", "CANAL", "BARRAGEM", "USUARIO");

		for (int i = 0; i < listString.size(); i++) {
			listPermi.add(new PermissaoModel(MapaURLEnum.valueOf(listString.get(i)), 1, 1, 1, 1));
		}

		Optional<UsuarioModel> userModel = Optional.of(new UsuarioModel("admin", "admin", "admin@admin.com", "ADMIN", listPermi));
		saveUserEncodePassword(userModel);

	}

	public UsuarioEntity buscarUsuarioAtual() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		if (currentUserName == null)
			throw new RuntimeException("Usuário está vazio!");
		UsuarioEntity usuario = userRepository.findByUsulog(currentUserName).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + currentUserName));
		return usuario;
	}

	public List<UsuarioDetailsDTO> findUserPermiAll() {
		var userAll = userRepository.findAll();

		return userAll.stream().map(map -> {
			List<Permissao_UsuarioDTO> usuPerm = null;
			try {
				usuPerm = permissaoService.getAllPermissao_UsuarioDTO(map.getUsucod());
				return new UsuarioDetailsDTO(map, usuPerm);
			} catch (Exception e) {
				return new UsuarioDetailsDTO(map, usuPerm);
			}
		}).collect(Collectors.toList());
	}

	public UsuarioDetailsDTO findUserPermiById(Long cod) {
		if (cod == null)
			throw new RuntimeException("Código do Usuário está vazio!");
		var userAll = userRepository.findById(cod).get();
		if (userAll == null)
			throw new EntityNotFoundException("Usuário não encontrado ou deletado.");

		List<Permissao_UsuarioDTO> usuPerm = null;
		try {
			usuPerm = permissaoService.getAllPermissao_UsuarioDTO(userAll.getUsucod());
			return new UsuarioDetailsDTO(userAll, usuPerm);
		} catch (Exception e) {
			return new UsuarioDetailsDTO(userAll, usuPerm);
		}

	}

	public UsuarioDTO findLogin(String loginEntrada) throws IOException {
		if (loginEntrada == null)
			throw new RuntimeException("Login está vazio!");
		String login = loginEntrada;
		return new UsuarioDTO(userRepository.findByUsulog(login).get());
	}

	@Transactional
	public UsuarioDTO saveUserEncodePassword(Optional<UsuarioModel> userCadastro) {
		validateUserCadastro(userCadastro);
		if (!userCadastro.isPresent()) {
			throw new RuntimeException("Os dados do Usuário está vazio.");
		}
		String login = userCadastro.get().getLogin();
		if (login == null || login.isEmpty()) {
			throw new RuntimeException("Login está vazio.");
		}
		Optional<UsuarioEntity> existingUser = userRepository.findByUsulog(login);
		if (existingUser.isPresent()) {
			throw new RuntimeException("Usuário já existe!");
		}

		UsuarioEntity novoUsuario = createUsuario(userCadastro.get());
		PerfilEntity perfil = criarPerfil(userCadastro.get().getNivelAcesso());
		novoUsuario.setPerfil(perfil);

		UsuarioEntity savedUser = userRepository.save(novoUsuario);
		Long codigoUsuario = savedUser.getUsucod();
		if (codigoUsuario == null) {
			throw new EntityNotFoundException("Usuário está nulo no banco de dados.");
		}

		criarPermisao(codigoUsuario, perfil.getPercod(), userCadastro.get().getPermissao());

		return new UsuarioDTO(savedUser);
	}

	private void validateUserCadastro(Optional<UsuarioModel> userCadastro) {
		if (userCadastro.isEmpty() || !userCadastro.isPresent()) {
			throw new RuntimeException("Modelo de cadastro de Usuário está vazio!");
		}

		if (userCadastro.get().getLogin().isEmpty() || userCadastro.get().getSenha().isEmpty()) {
			throw new RuntimeException("Login e/ou senha não podem ser vazios!");
		}
	}

	private UsuarioEntity createUsuario(UsuarioModel userCadastro) {
		UsuarioEntity novoUsuario = new UsuarioEntity();
		novoUsuario.setUsulog(userCadastro.getLogin());
		novoUsuario.setUsusen(passwordEncoder.encode(userCadastro.getSenha()));
		novoUsuario.setUsuema(userCadastro.getEmail().isEmpty() ? "" : userCadastro.getEmail());
		return novoUsuario;
	}

	public PerfilEntity criarPerfil(String desc) {
		PerfilEntity perfil = perfilService.save(desc);
		if (perfil == null) {
			throw new EntityNotFoundException("Perfil não cadastrado.");
		}
		return perfil;
	}

	public String criarPermisao(Long codigoUsuario, Long codigoPerfil, List<PermissaoModel> permissao) {

		try {

			for (PermissaoModel perm : permissao) {
				permissaoService.save(codigoUsuario, codigoPerfil, perm);
			}
			return "Permissão Salva";
		} catch (Exception e) {
			logger.debug("Erro Execption: ", e);
			return null;
		}
	}

	public ResponseEntity<GenericResponseModel> deleteByCode(Long codigo) {
		Objects.requireNonNull(codigo, "Código da Usuario está nulo.");
		try {

			userRepository.findById(codigo).ifPresentOrElse(user -> {

				List<PermissaoDTO> permissao;
				try {
					Long codPerf = user.getPerfil().getPercod();
					Objects.requireNonNull(codPerf, "Código não encontrado");
					var perfil = perfilService.findById(codPerf);
					permissao = permissaoService.findByUsucod(user.getUsucod(), perfil.getCodigo());
					if (!permissaoService.delete(permissao)) {
						throw new RuntimeException("Falha ao excluir as permissões do usuário.");
					}

					userRepository.delete(user);

					if (!perfilService.perfil(codPerf)) {
						throw new RuntimeException("Falha ao excluir o perfil do usuário.");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}, () -> {
				throw new EntityNotFoundException("Usuário não encontrado");
			});
			return MessageResponse.sucess(null);
		} catch (Exception e) {
			return MessageResponse.badRequest(e.getMessage());
		}
	}

	public Page<UsuarioDTO> usuarioFindAllPaginado(String nome, Pageable pageable) {
		Objects.requireNonNull(pageable, "Pageable do Usuário está nulo.");
		Page<UsuarioEntity> result;
		if (nome == null)
			result = userRepository.findAll(pageable);
		else
			result = userRepository.findByUsulogLike(nome, pageable);

		return result.map(usuario -> new UsuarioDTO(usuario));

	}

	public List<UsuarioDTO> findAll() throws ParseException {
		var result = userRepository.findAll();
		return result.stream().map(barragem -> new UsuarioDTO(barragem)).collect(Collectors.toList());
	}

	public UsuarioDTO findById(Long codigo) throws ParseException {
		Objects.requireNonNull(codigo, "Código da usuário está nulo.");
		Optional<UsuarioEntity> emp = userRepository.findById(codigo);
		if (!emp.isPresent() || emp.get().getUsucod() == 0) {
			throw new EntityNotFoundException("Usuário não encontrado ou deletado.");
		}
		return new UsuarioDTO(emp.get());
	}

	@Transactional
	public UsuarioDTO update(Long codigo, Optional<UsuarioModel> userCadastro) throws ParseException {
		validateUserCadastro(userCadastro);

		Objects.requireNonNull(codigo, "Código do Usuário não pode ser nulo."); // Use Objects.requireNonNull instead of RuntimeException

		UsuarioEntity existingUser = userRepository.findById(codigo).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

		if (!existingUser.getUsulog().equals(userCadastro.get().getLogin())) {
			if (!userCadastro.isPresent()) {
				throw new RuntimeException("User data is missing.");
			}
			String login = userCadastro.get().getLogin();
			if (login == null || login.isEmpty()) {
				throw new RuntimeException("Login cannot be empty.");
			}
			Optional<UsuarioEntity> usuarioComLogin = userRepository.findByUsulog(login);
			if (usuarioComLogin.isPresent()) {
				throw new RuntimeException("Usuário com o mesmo login já existe!");
			}
		}

		updateUsuario(existingUser, userCadastro.get());

		UsuarioEntity updatedUser = userRepository.save(existingUser);
		Long codigoUsuario = updatedUser.getUsucod();
		if (codigoUsuario == null) {
			throw new EntityNotFoundException("Usuário está nulo no banco de dados.");
		}

		criarPermisao(codigoUsuario, existingUser.getPerfil().getPercod(), userCadastro.get().getPermissao());

		return new UsuarioDTO(updatedUser);
	}

	private void updateUsuario(UsuarioEntity existingUser, UsuarioModel userCadastro) {
		existingUser.setUsulog(userCadastro.getLogin());
		existingUser.setUsusen(passwordEncoder.encode(userCadastro.getSenha()));
		existingUser.setUsuema(userCadastro.getEmail().isEmpty() ? "" : userCadastro.getEmail());
		existingUser.setPerfil(criarPerfil(userCadastro.getNivelAcesso()));
	}
}
