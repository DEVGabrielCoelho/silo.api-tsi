package br.com.telematica.siloapi.handler;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.telematica.siloapi.model.enums.RecursoMapEnum;
import br.com.telematica.siloapi.services.impl.PerfilPermissaoServiceImpl;
import br.com.telematica.siloapi.services.impl.RecursoServiceImpl;

@Configuration
public class PermissaoHandler {

	private static Logger logger = LoggerFactory.getLogger(PerfilPermissaoServiceImpl.class);

	@Autowired
	private RecursoServiceImpl recursoService;
	@Autowired
	private PerfilPermissaoServiceImpl perfilPermissaoService;

	public boolean checkPermission(String perfil, URLValidator urlValidator, String method) {
		String nomeRecurso = urlValidator.getRecursoMapEnum().getNome();
		Objects.requireNonNull(nomeRecurso, "Nome do recurso está nulo");
		Objects.requireNonNull(perfil, "Perfil está nulo");
		Objects.requireNonNull(urlValidator, "UrlValidator está nulo");
		
		var recursoEntity = recursoService.findByIdEntity(nomeRecurso);
		var perfilEntity = perfilPermissaoService.findByIdPerfilEntity(perfil);
		var entity = perfilPermissaoService.findByPerfilAndRecurso(perfilEntity, recursoEntity);

		switch (method.toUpperCase()) {
		case "GET":
			if (urlValidator.getMessage().equals("BUSCAR")) {
				if (entity.getPembus() == 1)
					return true;
			} else {
				if (entity.getPemlis() == 1)
					return true;
			}
			break;
		case "POST":
			if (entity.getPemcri() == 1)
				return true;
			break;
		case "PUT":
			if (entity.getPemedi() == 1)
				return true;
			break;
		case "DELETE":
			if (entity.getPemdel() == 1)
				return true;
			break;
		default:
			return false;
		}
		return false;
	}

	public RecursoMapEnum mapDescricaoToUrl(RecursoMapEnum descricao) {
		switch (descricao) {
		case SIRENE:
			return RecursoMapEnum.SIRENE;
		case MODULO:
			return RecursoMapEnum.MODULO;
		case PENDENCIA:
			return RecursoMapEnum.PENDENCIA;
		case MEDICAO:
			return RecursoMapEnum.MEDICAO;
		case AUDIO:
			return RecursoMapEnum.AUDIO;
		case LOGGER:
			return RecursoMapEnum.LOGGER;
		case EMPRESA:
			return RecursoMapEnum.EMPRESA;
		case CANAL:
			return RecursoMapEnum.CANAL;
		case BARRAGEM:
			return RecursoMapEnum.BARRAGEM;
		case USUARIO:
			return RecursoMapEnum.USUARIO;
		case FIRMWARE:
			return RecursoMapEnum.FIRMWARE;
		default:
			logger.error("Error, URL não encontrada.");
			return null;
		}
	}

//	public UrlPermissoesDTO getPermissionsForRole(String role) {
//		return permissionsByRole.get(role);
//	}

//	public List<PermissaoDTO> findByUsucod(Long cod, Long perfil) throws ParseException {
//		List<Permissao> result = permRepository.findByUsucodAndPercod(cod, perfil);
//		return result.stream().map(permissao -> new PermissaoDTO(permissao)).collect(Collectors.toList());
//	}
//	@Autowired
//	public void checkPermission() {
//		var findAllPerfil = this.perService.findAllEntity();
//
////		if (findAllPerfil.isEmpty()) {
////			userImpleService.createUserRolePermission();
////		}
//		for (Perfil perfil : findAllPerfil) {
//			UrlPermissoesDTO urlPermission = new UrlPermissoesDTO();
//			urlPermission.setPerfil(perfil.getPerdes().toUpperCase());
//			var findAllPermissao = permService.findByIdPerfilEntity(perfil.getPernom());
//			for (Permissao permissao : findAllPermissao) {
//				RecursoMapEnum enumURL = RecursoMapEnum.valueOf(permissao.getRecnom());
//				RecursoMapEnum url = mapDescricaoToUrl(enumURL);
//				if (permissao.getPembus() == 1)
//					urlPermission.adicionarPermissao("GET", url.getUrl());
//				if (permissao.getPemlis() == 1)
//					urlPermission.adicionarPermissao("GET", url.getUrl());
//				if (permissao.getPemdel() == 1)
//					urlPermission.adicionarPermissao("DELETE", url.getUrl());
//				if (permissao.getPemcri() == 1)
//					urlPermission.adicionarPermissao("POST", url.getUrl());
//				if (permissao.getPemedi() == 1)
//					urlPermission.adicionarPermissao("PUT", url.getUrl());
//			}
//			permissionsByRole.put(perfil.getPerdes().toUpperCase(), urlPermission);
//		}
//
////		System.out.println(permissionsByRole);
//	}
}
