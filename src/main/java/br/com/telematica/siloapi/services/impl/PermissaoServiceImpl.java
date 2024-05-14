package br.com.telematica.siloapi.services.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.PermissaoModel;
import br.com.telematica.siloapi.model.dto.PermissaoDTO;
import br.com.telematica.siloapi.model.dto.Permissao_UsuarioDTO;
import br.com.telematica.siloapi.model.dto.UrlPermissoesDTO;
import br.com.telematica.siloapi.model.entity.PerfilEntity;
import br.com.telematica.siloapi.model.entity.PermissaoEntity;
import br.com.telematica.siloapi.model.enums.MapaURLEnum;
import br.com.telematica.siloapi.repository.PerfilRepository;
import br.com.telematica.siloapi.repository.PermissaoRepository;
import br.com.telematica.siloapi.services.PermissaoInterface;

@Service
public class PermissaoServiceImpl implements PermissaoInterface {

	private static Logger logger = LoggerFactory.getLogger(PermissaoServiceImpl.class);

	private Map<String, UrlPermissoesDTO> permissionsByRole = new HashMap<>();

	@Autowired
	private PermissaoRepository permRepository;
	@Autowired
	@Lazy
	private UsuarioServiceImpl userImpleService;
	@Autowired
	private PerfilRepository perRepository;

	@Override
	public List<PermissaoDTO> findByUsucod(Long cod, Long perfil) throws ParseException {
		List<PermissaoEntity> result = permRepository.findByUsucodAndPercod(cod, perfil);
		return result.stream().map(permissao -> new PermissaoDTO(permissao)).collect(Collectors.toList());
	}

	@Override
	public PermissaoDTO save(Long codiUser, Long codiPerfil, PermissaoModel dto) {
		return new PermissaoDTO(permRepository.save(new PermissaoEntity(codiUser, codiPerfil, null, dto.getDescricao().toString(), dto.getGet(), dto.getPost(), dto.getPut(), dto.getDelete(), 1)));
	}

	@Override
	public List<PermissaoDTO> getAll() {

		var permiAll = permRepository.findAll();
		return permiAll.stream().map(map -> new PermissaoDTO(map)).collect(Collectors.toList());
	}

	@Override
	public List<Permissao_UsuarioDTO> getAllPermissao_UsuarioDTO(Long cod) {

		var permiAll = permRepository.findByUsucod(cod);
		return permiAll.stream().map(map -> new Permissao_UsuarioDTO(map)).collect(Collectors.toList());
	}

	@Override
	public boolean delete(List<PermissaoDTO> perm) {
		try {
			perm.forEach(permissao -> {
				Long cod = permissao.getCodigo();
				Objects.requireNonNull(cod, "Código da Permissão não encontrado.");
				permRepository.deleteById(cod);
			});

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public UrlPermissoesDTO getPermissionsForRole(String role) {
		return permissionsByRole.get(role);
	}

	@Autowired
	public void checkPermission() {
		var findAllPerfil = this.perRepository.findAll();

		if (findAllPerfil.isEmpty()) {
			userImpleService.createUserRolePermission();
		}
		for (PerfilEntity perfil : findAllPerfil) {
			UrlPermissoesDTO urlPermission = new UrlPermissoesDTO();
			urlPermission.setPerfil(perfil.getPerdes().toUpperCase());
			var findAllPermissao = this.permRepository.findByPercod(perfil.getPercod());
			for (PermissaoEntity permissao : findAllPermissao) {
				MapaURLEnum enumURL = MapaURLEnum.valueOf(permissao.getPemdes());
				String url = mapDescricaoToUrl(enumURL);
				if (permissao.getPemget() == 1)
					urlPermission.adicionarPermissao("GET", url);
				if (permissao.getPemdlt() == 1)
					urlPermission.adicionarPermissao("DELETE", url);
				if (permissao.getPempos() == 1)
					urlPermission.adicionarPermissao("POST", url);
				if (permissao.getPemput() == 1)
					urlPermission.adicionarPermissao("PUT", url);
			}
			permissionsByRole.put(perfil.getPerdes().toUpperCase(), urlPermission);
		}

//		System.out.println(permissionsByRole);
	}

	public String mapDescricaoToUrl(MapaURLEnum descricao) {
		switch (descricao) {
		case SIRENE:
			return MapaURLEnum.SIRENE.getUrl();
		case MODULO:
			return MapaURLEnum.MODULO.getUrl();
		case PENDENCIA:
			return MapaURLEnum.PENDENCIA.getUrl();
		case MEDICAO:
			return MapaURLEnum.MEDICAO.getUrl();
		case AUDIO:
			return MapaURLEnum.AUDIO.getUrl();
		case LOGGER:
			return MapaURLEnum.LOGGER.getUrl();
		case EMPRESA:
			return MapaURLEnum.EMPRESA.getUrl();
		case CANAL:
			return MapaURLEnum.CANAL.getUrl();
		case BARRAGEM:
			return MapaURLEnum.BARRAGEM.getUrl();
		case USUARIO:
			return MapaURLEnum.USUARIO.getUrl();
		case FIRMWARE:
			return MapaURLEnum.FIRMWARE.getUrl();
		default:
			logger.error("Error, URL não encontrada.");
			return null;
		}
	}
}
