package br.com.telematica.siloapi.handler;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.telematica.siloapi.services.impl.PerfilPermissaoServiceImpl;
import br.com.telematica.siloapi.services.impl.RecursoServiceImpl;

@Configuration
public class PermissaoHandler {

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

}
