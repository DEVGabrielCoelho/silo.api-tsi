package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.UsuarioModel;
import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.model.dto.UsuarioDTO;
import br.com.telematica.siloapi.model.dto.UsuarioPermissaoDTO;
import br.com.telematica.siloapi.model.entity.Abrangencia;
import br.com.telematica.siloapi.model.entity.Empresa;
import br.com.telematica.siloapi.model.entity.Perfil;
import br.com.telematica.siloapi.model.entity.Usuario;
import br.com.telematica.siloapi.repository.UsuarioRepository;
import br.com.telematica.siloapi.services.UsuarioServInterface;
import br.com.telematica.siloapi.utils.Utils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioServInterface {

	private static Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioRepository userRepository;
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PerfilPermissaoServiceImpl permissaoService;
	@Autowired
	private AbrangenciaServiceImpl abrangenciaService;
	@Autowired
	private EmpresaServiceImpl empresaService;

	@Value("${spring.jackson.time-zone}")
	private String configuredTimeZone;

	public UsuarioDTO findLogin(String login) throws EntityNotFoundException, IOException {
		var user = userRepository.findByUsulog(login);
		if (user.isEmpty()) {
			throw new RuntimeException("Usuário não existe!");
		}
		return new UsuarioDTO(user.get(), empresaService.findById(user.get().getEmpresa().getEmpcod()), user.get().getPerfil(), abrangenciaService.findByIdSimples(user.get().getAbrangencia().getAbrcod()));
	}

	public Usuario findLoginEntity(String login) {
		var user = userRepository.findByUsulog(login);
		if (user.isEmpty()) {
			throw new RuntimeException("Usuário não existe!");
		}
		return user.get();
	}

	public Page<Usuario> findAllEntity(String nome, @NonNull Pageable pageable) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(pageable, "Pageable do Usuário está nulo.");
		Specification<Usuario> spec;
		spec = Usuario.filterByFields(nome);

		Page<Usuario> result = userRepository.findAll(spec, pageable);
		return result;
	}

	public List<Usuario> findAllEntity() throws EntityNotFoundException, IOException {
		return userRepository.findAll();
	}

	public Usuario findByIdEntity(String login) {
		var userAdmin = userRepository.findByUsulog(login);
		if (userAdmin.isEmpty())
			return null;
		return userAdmin.get();
	}

	public Usuario findByIdEntity(Long cod) throws EntityNotFoundException, IOException {
		var userAll = userRepository.findById(cod);
		if (userAll.isEmpty())
			return null;
		return userAll.get();
	}

	public UsuarioDTO findByUsuario(Long codigo) throws EntityNotFoundException, IOException {
		var user = findByIdEntity(codigo);

		if (user == null)
			throw new RuntimeException("Sem abrangência permitida para esse Usuário.");

		return new UsuarioDTO(user, empresaService.findById(user.getEmpresa().getEmpcod()), user.getPerfil(), abrangenciaService.findByIdSimples(user.getAbrangencia().getAbrcod()));
	}

	public Usuario saveUpdateEntity(@NonNull Long codigo, @NonNull UsuarioModel userModel) throws EntityNotFoundException, IOException {
		Optional<Usuario> existingUser = userRepository.findById(codigo);
		String login = "admin";
		if (existingUser.get().getUsulog().toUpperCase().equals(login.toUpperCase())) {
			logger.info("Usuário " + login.toUpperCase() + " não pode ser alterado " + existingUser.get());
			throw new RuntimeException("Usuário " + login.toUpperCase() + " não pode ser alterado.");
		}
		Objects.requireNonNull(codigo, "Código do Usuário está nulo.");
		Objects.requireNonNull(userModel.getNome(), "Nome do Usuário está nulo.");
		Objects.requireNonNull(userModel.getCpf(), "CPF do Usuário está nulo.");
		Objects.requireNonNull(userModel.getLogin(), "Login do Usuário está nulo.");
		Objects.requireNonNull(userModel.getSenha(), "Senha do Usuário está nulo.");
		Objects.requireNonNull(userModel.getEmail(), "Email do Usuário está nulo.");
		Objects.requireNonNull(userModel.getEmpresa(), "Código da Empresa do Usuário está nulo.");
		Objects.requireNonNull(userModel.getPerfil(), "Código do Perfil do Usuário está nulo.");
		Objects.requireNonNull(userModel.getAbrangencia(), "Código da Abrangência do Usuário está nulo.");

		Perfil perfil = permissaoService.findByIdPerfilEntity(userModel.getPerfil());
		Empresa empresa = empresaService.findByIdEntity(userModel.getEmpresa());
		Abrangencia abrangencia = abrangenciaService.findByIdEntity(userModel.getAbrangencia());

		existingUser.get().setUsunom(userModel.getNome());
		existingUser.get().setUsucpf(userModel.getCpf());
		existingUser.get().setUsulog(userModel.getLogin());
		existingUser.get().setUsusen(passwordEncoder.encode(userModel.getSenha()));
		existingUser.get().setUsuema(userModel.getEmail().isEmpty() ? "" : userModel.getEmail());
		existingUser.get().setPerfil(perfil);
		existingUser.get().setAbrangencia(abrangencia);
		existingUser.get().setEmpresa(empresa);
		return userRepository.save(existingUser.get());
	}

	public Usuario saveUpdateEntity(@NonNull UsuarioModel userModel) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(userModel.getNome(), "Nome do Usuário está nulo.");
		Objects.requireNonNull(userModel.getCpf(), "CPF do Usuário está nulo.");
		Objects.requireNonNull(userModel.getLogin(), "Login do Usuário está nulo.");
		Objects.requireNonNull(userModel.getSenha(), "Senha do Usuário está nulo.");
		Objects.requireNonNull(userModel.getEmail(), "Email do Usuário está nulo.");
		Objects.requireNonNull(userModel.getEmpresa(), "Código da Empresa do Usuário está nulo.");
		Objects.requireNonNull(userModel.getPerfil(), "Código do Perfil do Usuário está nulo.");
		Objects.requireNonNull(userModel.getAbrangencia(), "Código da Abrangência do Usuário está nulo.");
		Usuario userSave = null;
		Optional<Usuario> existingUser = userRepository.findByUsulog(userModel.getLogin());
		String login = "admin";
		if (!existingUser.isEmpty() && existingUser.get().getUsulog().toUpperCase().equals(login.toUpperCase())) {
			userSave = existingUser.get();
			logger.info("Usuário com o login " + login.toUpperCase() + " já existe: " + userSave);
			throw new RuntimeException("Usuário já existe!");
		}
		Perfil perfil = permissaoService.findByIdPerfilEntity(userModel.getPerfil());
		Empresa empresa = empresaService.findByIdEntity(userModel.getEmpresa());
		Abrangencia abrangencia = abrangenciaService.findByIdEntity(userModel.getAbrangencia());
		existingUser = Optional.ofNullable(new Usuario());
		existingUser.get().setUsucod(null);
		existingUser.get().setUsunom(userModel.getNome());
		existingUser.get().setUsucpf(userModel.getCpf());
		existingUser.get().setUsulog(userModel.getLogin());
		existingUser.get().setUsusen(passwordEncoder.encode(userModel.getSenha()));
		existingUser.get().setUsuema(userModel.getEmail().isEmpty() ? "" : userModel.getEmail());
		existingUser.get().setPerfil(perfil);
		existingUser.get().setAbrangencia(abrangencia);
		existingUser.get().setEmpresa(empresa);
		userSave = userRepository.save(existingUser.get());

		return userSave;
	}

	public ResponseGlobalModel deleteEntity(@NonNull Long codigo) throws IOException {
		try {
			userRepository.deleteById(codigo);
			return Utils.responseMessageSucess("Apagado com Sucesso.");
		} catch (Exception e) {
			throw new IOException("Erro ao apagar o item. Mensagem" + e.getMessage());
		}
	}

	@Override
	public Page<UsuarioDTO> findAll(String nome, @NonNull Pageable pageable) throws EntityNotFoundException, IOException {
		return findAllEntity(nome, pageable).map(map -> {
			EmpresaDTO empresaDTO = null;
			try {
				empresaDTO = new EmpresaDTO(empresaService.findById(map.getEmpresa().getEmpcod()));
				return new UsuarioDTO(map, empresaDTO, map.getPerfil(), abrangenciaService.findByIdSimples(map.getAbrangencia().getAbrcod()));
			} catch (EntityNotFoundException | IOException e) {
				return new UsuarioDTO(map, empresaDTO, map.getPerfil(), null);
			}
		});
	}

	@Override
	public List<UsuarioDTO> findAll() throws EntityNotFoundException, IOException {
		return findAllEntity().stream().map(map -> {
			EmpresaDTO empresaDTO = null;
			try {
				empresaDTO = new EmpresaDTO(empresaService.findById(map.getEmpresa().getEmpcod()));
				return new UsuarioDTO(map, empresaDTO, map.getPerfil(), abrangenciaService.findByIdSimples(map.getAbrangencia().getAbrcod()));
			} catch (EntityNotFoundException | IOException e) {
				return new UsuarioDTO(map, empresaDTO, map.getPerfil(), null);
			}
		}).collect(Collectors.toList());
	}

	@Override
	public UsuarioDTO findById(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		return findByUsuario(codigo);
	}

	@Override
	public UsuarioPermissaoDTO findByIdPermission(@NonNull Long codigo) throws EntityNotFoundException, IOException {
		var user = findByIdEntity(codigo);
		return new UsuarioPermissaoDTO(user, new EmpresaDTO(empresaService.findById(user.getEmpresa().getEmpcod())), abrangenciaService.findByIdSimples(user.getAbrangencia().getAbrcod()), permissaoService.findByIdPerfil(user.getPerfil().getPercod()));
	}

	@Override
	public UsuarioDTO saveUpdateEncodePassword(@NonNull UsuarioModel userModel) throws EntityNotFoundException, IOException {
		return new UsuarioDTO(saveUpdateEntity(userModel), empresaService.findById(userModel.getEmpresa()), permissaoService.findByIdPerfilEntity(userModel.getPerfil()), abrangenciaService.findByIdSimples(userModel.getAbrangencia()));
	}

	@Override
	public UsuarioDTO saveUpdateEncodePassword(@NonNull Long codigo, @NonNull UsuarioModel userModel) throws EntityNotFoundException, IOException {
		return new UsuarioDTO(saveUpdateEntity(codigo, userModel), empresaService.findById(userModel.getEmpresa()), permissaoService.findByIdPerfilEntity(userModel.getPerfil()), abrangenciaService.findByIdSimples(userModel.getAbrangencia()));
	}

	@Override
	public ResponseGlobalModel delete(@NonNull Long perfil) throws IOException {
		return deleteEntity(perfil);
	}

	// public Usuario buscarUsuarioAtual() {
	// Authentication authentication =
	// SecurityContextHolder.getContext().getAuthentication();
	// String currentUserName = authentication.getName();
	// Usuario usuario = userRepository.findByUsulog(currentUserName).orElseThrow(()
	// -> new UsernameNotFoundException("Usuário não encontrado: " +
	// currentUserName));
	// return usuario;
	// }
}
